

import com.hcl.library.exception.BorrowLimitExceededException;
import com.hcl.library.exception.ItemNotAvailableException;
import com.hcl.library.exception.ItemNotFoundException;
import com.hcl.library.exception.LibraryException;
import com.hcl.library.model.Book;
import com.hcl.library.model.DVD;
import com.hcl.library.model.LibraryItem;
import com.hcl.library.model.Magazine;
import com.hcl.library.model.Member;
import com.hcl.library.service.LibraryService;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static final String REPORT_PATH = "library-report.txt";

    private Main() {
    }

    public static void main(String[] args) {
        LibraryService libraryService = new LibraryService();

        try (Scanner scanner = new Scanner(System.in)) {
            runMenu(scanner, libraryService);
        }
    }

    private static void runMenu(
            Scanner scanner, LibraryService libraryService) {

        boolean running = true;

        while (running && scanner.hasNextLine()) {
            printMenu();

            String choiceLine = readLine(scanner, "Choice: ");

            if (choiceLine == null) {
                break;
            }

            try {
                int choice = Integer.parseInt(choiceLine.trim());

                switch (choice) {
                    case 1:
                        addItem(scanner, libraryService);
                        break;

                    case 2:
                        registerMember(scanner, libraryService);
                        break;

                    case 3:
                        borrowItem(scanner, libraryService);
                        break;

                    case 4:
                        returnItem(scanner, libraryService);
                        break;

                    case 5:
                        joinWaitlist(scanner, libraryService);
                        break;

                    case 6:
                        listItems(libraryService);
                        break;

                    case 7:
                        searchItems(scanner, libraryService);
                        break;

                    case 8:
                        exportReport(scanner, libraryService);
                        break;

                    case 9:
                        printStatistics(libraryService);
                        break;

                    case 10:
                        removeOldItems(scanner, libraryService);
                        break;

                    case 0:
                        System.out.println("Goodbye!");
                        running = false;
                        break;

                    default:
                        System.out.println(
                                "Please select a menu option from 0 to 10."
                        );
                }

            } catch (ItemNotFoundException exception) {
                System.out.println("ERROR: " + exception.getMessage());

            } catch (ItemNotAvailableException exception) {
                System.out.println("ERROR: " + exception.getMessage());

            } catch (BorrowLimitExceededException exception) {
                System.out.println("ERROR: " + exception.getMessage());

            } catch (LibraryException exception) {
                System.out.println(
                        "LIBRARY ERROR: " + exception.getMessage()
                );

            } catch (IllegalArgumentException exception) {
                System.out.println(
                        "INPUT ERROR: " + exception.getMessage()
                );

            } catch (Exception exception) {
                System.out.println(
                        "UNEXPECTED ERROR: " + exception.getMessage()
                );

            } finally {
                // The menu is displayed again after the action.
                System.out.println();
            }
        }
    }

    private static void printMenu() {
        System.out.println("==== LIBRARY MENU ====");
        System.out.println("1. Add item          2. Register member");
        System.out.println("3. Borrow item       4. Return item");
        System.out.println("5. Join waitlist     6. List items (two sorts)");
        System.out.println("7. Search            8. Export report");
        System.out.println("9. Item statistics  10. Remove old items");
        System.out.println("0. Exit");
    }

    private static void addItem(
            Scanner scanner, LibraryService service) {

        System.out.println("Add item type: 1. Book  2. Magazine  3. DVD");

        int type = readInt(scanner, "Type: ");

        String title = readRequiredLine(scanner, "Title: ");
        int year = readInt(scanner, "Publication year: ");

        LibraryItem item;

        switch (type) {
            case 1:
                String author = readRequiredLine(scanner, "Author: ");
                String isbn = readRequiredLine(scanner, "ISBN: ");

                item = new Book(title, year, author, isbn);
                break;

            case 2:
                int issueNumber =
                        readInt(scanner, "Issue number: ");

                item = new Magazine(title, year, issueNumber);
                break;

            case 3:
                int duration =
                        readInt(scanner, "Duration in minutes: ");

                item = new DVD(title, year, duration);
                break;

            default:
                throw new IllegalArgumentException(
                        "Item type must be 1, 2, or 3."
                );
        }

        service.addItem(item);

        System.out.println(
                "Added item successfully. Generated item ID: "
                        + item.getId()
        );

        System.out.println(item);
    }

    private static void registerMember(
            Scanner scanner, LibraryService service) {

        String name = readRequiredLine(scanner, "Member name: ");
        String email = readRequiredLine(scanner, "Email: ");

        Member member = service.registerMember(name, email);

        System.out.println(
                "Member registered successfully. Generated member ID: "
                        + member.getId()
        );

        System.out.println(member);
    }

    private static void borrowItem(
            Scanner scanner, LibraryService service) {

        int memberId = readInt(scanner, "Member ID: ");
        int itemId = readInt(scanner, "Item ID: ");

        try {
            service.borrowItem(memberId, itemId);

        } catch (ItemNotAvailableException exception) {
            System.out.println("ERROR: " + exception.getMessage());

            String answer = readRequiredLine(
                    scanner, "Add to waitlist? (y/n): "
            );

            if (answer.trim().equalsIgnoreCase("y")) {
                service.joinWaitlist(memberId, itemId);
            } else {
                System.out.println("No waitlist entry created.");
            }
        }
    }

    private static void returnItem(
            Scanner scanner, LibraryService service) {

        int memberId = readInt(scanner, "Member ID: ");
        int itemId = readInt(scanner, "Item ID: ");
        int daysLate = readInt(scanner, "Late by (days): ");

        double fee = service.returnItem(
                memberId, itemId, daysLate
        );

        System.out.printf("Late fee: %.2f%n", fee);
        System.out.println("Item returned successfully.");
    }

    private static void joinWaitlist(
            Scanner scanner, LibraryService service) {

        int memberId = readInt(scanner, "Member ID: ");
        int itemId = readInt(scanner, "Item ID: ");

        service.joinWaitlist(memberId, itemId);
    }

    private static void listItems(LibraryService service) {
        System.out.println("\n--- Items sorted by title (A-Z) ---");

        printItems(service.listItemsByTitle());

        System.out.println(
                "\n--- Items sorted by year (newest first) ---"
        );

        printItems(service.listItemsByYearDescending());
    }

    private static void searchItems(
            Scanner scanner, LibraryService service) {

        String query = readRequiredLine(
                scanner, "Title contains: "
        );

        List<LibraryItem> matches = service.searchItems(query);

        if (matches.isEmpty()) {
            System.out.println("No matching items found.");
        } else {
            System.out.println("Search results:");
            printItems(matches);
        }
    }

    private static void exportReport(
            Scanner scanner, LibraryService service) {

        String path = readLine(
                scanner,
                "Report path (press Enter for " + REPORT_PATH + "): "
        );

        if (path == null || path.isBlank()) {
            path = REPORT_PATH;
        }

        service.exportReport(path);

        System.out.println("Report exported to: " + path);
    }

    private static void printStatistics(LibraryService service) {
        System.out.println("Items by type:");

        for (Map.Entry<String, Integer> entry
                : service.getItemTypeStatistics().entrySet()) {

            System.out.println(
                    entry.getKey() + ": " + entry.getValue()
            );
        }
    }

    private static void removeOldItems(
            Scanner scanner, LibraryService service) {

        int year = readInt(
                scanner,
                "Remove available items published before year: "
        );

        int removed = service.removeOldItems(year);

        System.out.println(
                "Removed " + removed + " old available item(s)."
        );
    }

    private static void printItems(List<LibraryItem> items) {
        if (items.isEmpty()) {
            System.out.println("No items in the library.");
            return;
        }

        for (LibraryItem item : items) {
            System.out.println(item);
        }
    }

    private static int readInt(
            Scanner scanner, String prompt) {

        String value = readRequiredLine(scanner, prompt);

        try {
            return Integer.parseInt(value.trim());

        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(
                    "Please enter a valid whole number: " + value,
                    exception
            );
        }
    }

    private static String readRequiredLine(
            Scanner scanner, String prompt) {

        String value = readLine(scanner, prompt);

        if (value == null) {
            throw new IllegalStateException(
                    "Input ended before this action was complete."
            );
        }

        return value;
    }

    private static String readLine(
            Scanner scanner, String prompt) {

        System.out.print(prompt);

        return scanner.hasNextLine()
                ? scanner.nextLine()
                : null;
    }
}