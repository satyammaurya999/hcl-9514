
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

public class LibraryService {

    public static final int MAX_BORROWED_ITEMS = 3;

    private final Map<Integer, LibraryItem> items =
            new HashMap<>();

    private final Map<Integer, Member> members =
            new HashMap<>();

    private final Repository<LibraryItem, Integer> itemRepository;
    private final Repository<Member, Integer> memberRepository;

    private final Set<String> bookIsbns = new HashSet<>();

    private final Map<Integer, Queue<Member>> waitlists =
            new HashMap<>();

    private final SearchCache searchCache = new SearchCache();

    private static final Comparator<LibraryItem>
            YEAR_DESCENDING_THEN_TITLE =
            Comparator.comparing(
                    LibraryItem::getYear,
                    Comparator.reverseOrder()
            ).thenComparing(
                    LibraryItem::getTitle,
                    String.CASE_INSENSITIVE_ORDER
            );

    public LibraryService() {
        itemRepository = new InMemoryRepository<>(
                LibraryItem::getId, items
        );

        memberRepository = new InMemoryRepository<>(
                Member::getId, members
        );
    }

    // Add item and prevent duplicate IDs and ISBNs.
    public void addItem(LibraryItem item) {
        Objects.requireNonNull(item, "Item cannot be null.");

        if (items.containsKey(item.getId())) {
            throw new LibraryException(
                    "An item with ID " + item.getId()
                            + " already exists."
            );
        }

        if (item instanceof Book) {
            Book book = (Book) item;
            String isbn = normalizeIsbn(book.getIsbn());

            if (bookIsbns.contains(isbn)) {
                throw new LibraryException(
                        "A book with ISBN " + book.getIsbn()
                                + " already exists."
                );
            }

            bookIsbns.add(isbn);
        }

        itemRepository.save(item);
        searchCache.clear();
    }

    // Register member with generated ID.
    public Member registerMember(String name, String email) {
        Member member = new Member(name, email);
        memberRepository.save(member);
        return member;
    }

    // Borrow an item.
    public void borrowItem(int memberId, int itemId) {
        LibraryItem item = findItemOrThrow(itemId);
        Member member = findMemberOrThrow(memberId);

        if (!item.isAvailable()) {
            throw new ItemNotAvailableException(
                    "Item " + itemId + " is not available."
            );
        }

        if (member.getBorrowedItemCount()
                >= MAX_BORROWED_ITEMS) {

            throw new BorrowLimitExceededException(
                    "Member " + memberId
                            + " has reached the limit of "
                            + MAX_BORROWED_ITEMS + " items."
            );
        }

        item.borrow();
        member.addBorrowedItem(item);

        System.out.println("Issued: " + item);
    }

    // Return an item and calculate the late fee.
    public double returnItem(
            int memberId, int itemId, int daysLate) {

        if (daysLate < 0) {
            throw new IllegalArgumentException(
                    "Days late cannot be negative."
            );
        }

        LibraryItem item = findItemOrThrow(itemId);
        Member member = findMemberOrThrow(memberId);

        if (item.isAvailable()
                || !member.getBorrowedItems().contains(item)) {

            throw new ItemNotAvailableException(
                    "Member " + memberId
                            + " has not borrowed item "
                            + itemId + "."
            );
        }

        double fee = item.lateFee(daysLate);

        member.removeBorrowedItem(item);
        item.giveBack();

        // Notify the first member waiting for this item.
        Queue<Member> queue = waitlists.get(itemId);

        if (queue != null) {
            Member nextMember = queue.poll();

            if (nextMember != null) {
                System.out.println(
                        "Notification: Member "
                                + nextMember.getId()
                                + " (" + nextMember.getName()
                                + ") is next in the waitlist for "
                                + itemId + "."
                );
            }

            if (queue.isEmpty()) {
                waitlists.remove(itemId);
            }
        }

        return fee;
    }

    // Add member to the queue for an unavailable item.
    public void joinWaitlist(int memberId, int itemId) {
        LibraryItem item = findItemOrThrow(itemId);
        Member member = findMemberOrThrow(memberId);

        if (item.isAvailable()) {
            throw new LibraryException(
                    "Item " + itemId
                            + " is available; borrow it instead "
                            + "of joining the waitlist."
            );
        }

        Queue<Member> queue = waitlists.computeIfAbsent(
                itemId, ignored -> new ArrayDeque<>()
        );

        if (queue.contains(member)) {
            throw new LibraryException(
                    "Member " + memberId
                            + " is already on the waitlist for item "
                            + itemId + "."
            );
        }

        queue.offer(member);

        System.out.println(
                "Member " + memberId
                        + " added to the waitlist for item "
                        + itemId + "."
        );
    }

    public List<LibraryItem> getAllItems() {
        return itemRepository.findAll();
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    // Sort by natural order: title.
    public List<LibraryItem> listItemsByTitle() {
        List<LibraryItem> sorted =
                new ArrayList<>(itemRepository.findAll());

        sorted.sort(null);

        return List.copyOf(sorted);
    }

    // Sort by publication year descending, then title.
    public List<LibraryItem> listItemsByYearDescending() {
        List<LibraryItem> sorted =
                new ArrayList<>(itemRepository.findAll());

        sorted.sort(YEAR_DESCENDING_THEN_TITLE);

        return List.copyOf(sorted);
    }

    // Search titles; use the bounded LRU cache.
    public List<LibraryItem> searchItems(String titleQuery) {
        if (titleQuery == null || titleQuery.isBlank()) {
            throw new IllegalArgumentException(
                    "Search title cannot be blank."
            );
        }

        String query = titleQuery.trim().toLowerCase(Locale.ROOT);

        return searchCache.getCached(query).orElseGet(() -> {
            List<LibraryItem> matches = new ArrayList<>();

            for (LibraryItem item : itemRepository.findAll()) {
                if (item.getTitle()
                        .toLowerCase(Locale.ROOT)
                        .contains(query)) {
                    matches.add(item);
                }
            }

            List<LibraryItem> result = List.copyOf(matches);
            searchCache.put(query, result);

            return result;
        });
    }

    // Remove available items published before a given year.
    public int removeOldItems(int beforeYear) {
        int removed = 0;

        Iterator<Map.Entry<Integer, LibraryItem>> iterator =
                items.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<Integer, LibraryItem> entry =
                    iterator.next();

            LibraryItem item = entry.getValue();

            if (item.getYear() < beforeYear
                    && item.isAvailable()) {

                if (item instanceof Book) {
                    Book book = (Book) item;

                    bookIsbns.remove(
                            normalizeIsbn(book.getIsbn())
                    );
                }

                iterator.remove();
                removed++;
            }
        }

        if (removed > 0) {
            searchCache.clear();
        }

        return removed;
    }

    // Count the number of items of each type.
    public Map<String, Integer> getItemTypeStatistics() {
        Map<String, Integer> counts = new LinkedHashMap<>();

        counts.put("Book", 0);
        counts.put("Magazine", 0);
        counts.put("DVD", 0);

        for (LibraryItem item : itemRepository.findAll()) {
            counts.merge(
                    item.getClass().getSimpleName(),
                    1,
                    Integer::sum
            );
        }

        Map<String, Integer> result = new LinkedHashMap<>();

        result.put("Book", counts.getOrDefault("Book", 0));
        result.put("Magazine", counts.getOrDefault("Magazine", 0));
        result.put("DVD", counts.getOrDefault("DVD", 0));

        return Collections.unmodifiableMap(
                new LinkedHashMap<>(result)
        );
    }

    // Build a report with StringBuilder.
    public String buildReport() {
        StringBuilder report = new StringBuilder();

        report.append("LIBRARY MANAGEMENT REPORT\n");
        report.append("=========================\n");

        report.append("Total items: ")
                .append(items.size())
                .append('\n');

        report.append("Total members: ")
                .append(members.size())
                .append("\n\n");

        report.append("ITEMS\n-----\n");

        for (LibraryItem item : listItemsByTitle()) {
            report.append(item)
                    .append(" | late fee per day: ")
                    .append(item.lateFee(1))
                    .append(" | status: ")
                    .append(
                            item.isAvailable()
                                    ? "Available"
                                    : "Issued"
                    )
                    .append('\n');
        }

        report.append("\nITEM COUNTS BY TYPE\n");
        report.append("-------------------\n");

        for (Map.Entry<String, Integer> entry
                : getItemTypeStatistics().entrySet()) {

            report.append(entry.getKey())
                    .append(": ")
                    .append(entry.getValue())
                    .append('\n');
        }

        report.append("\nMEMBERS\n-------\n");

        for (Member member : getAllMembers()) {
            report.append(member).append('\n');
        }

        return report.toString();
    }

    // Export report using try-with-resources.
    public void exportReport(String path) {
        if (path == null || path.isBlank()) {
            throw new IllegalArgumentException(
                    "Report path cannot be blank."
            );
        }

        Path outputPath = Path.of(path.trim());

        try {
            Path parent = outputPath.toAbsolutePath().getParent();

            if (parent != null) {
                Files.createDirectories(parent);
            }

            try (BufferedWriter writer = Files.newBufferedWriter(
                    outputPath, StandardCharsets.UTF_8)) {

                writer.write(buildReport());
            }

        } catch (IOException exception) {
            throw new LibraryException(
                    "Could not export library report to '"
                            + path + "'.",
                    exception
            );
        }
    }

    private LibraryItem findItemOrThrow(int itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(
                        () -> new ItemNotFoundException(
                                "Item " + itemId + " was not found."
                        )
                );
    }

    private Member findMemberOrThrow(int memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new LibraryException(
                                "Member " + memberId + " was not found."
                        )
                );
    }

    private String normalizeIsbn(String isbn) {
        return isbn.replaceAll("[-\\s]", "")
                .toUpperCase(Locale.ROOT);
    }
}