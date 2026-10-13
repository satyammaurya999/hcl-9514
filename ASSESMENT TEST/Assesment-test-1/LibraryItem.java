
import java.time.Year;
import java.util.Objects;

public abstract class LibraryItem
        implements Borrowable, Comparable<LibraryItem> {

    private static final int FIRST_ID = 1001;
    private static final int MIN_PUBLICATION_YEAR = 1900;

    private static int nextId = FIRST_ID;

    private final int id;
    private String title;
    private int year;
    private boolean available;

    protected LibraryItem(String title, int year) {
        setTitle(title);
        setYear(year);

        this.id = nextId++;
        this.available = true;
    }

    public final int getId() {
        return id;
    }

    public final String getTitle() {
        return title;
    }

    public final void setTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException(
                    "Title must not be blank."
            );
        }

        this.title = title.trim();
    }

    public final int getYear() {
        return year;
    }

    public final void setYear(int year) {
        int currentYear = Year.now().getValue();

        if (year < MIN_PUBLICATION_YEAR || year > currentYear) {
            throw new IllegalArgumentException(
                    "Year must be between "
                            + MIN_PUBLICATION_YEAR
                            + " and " + currentYear + "."
            );
        }

        this.year = year;
    }

    @Override
    public final boolean isAvailable() {
        return available;
    }

    @Override
    public final void borrow() {
        if (!available) {
            throw new IllegalStateException(
                    "Item is already issued."
            );
        }

        available = false;
    }

    @Override
    public final void giveBack() {
        if (available) {
            throw new IllegalStateException(
                    "Item has not been issued."
            );
        }

        available = true;
    }

    public abstract double lateFee(int daysLate);

    protected final void validateDaysLate(int daysLate) {
        if (daysLate < 0) {
            throw new IllegalArgumentException(
                    "Days late cannot be negative."
            );
        }
    }

    // Natural sorting: title in case-insensitive order.
    @Override
    public int compareTo(LibraryItem other) {
        Objects.requireNonNull(other, "Other item cannot be null.");

        int result = String.CASE_INSENSITIVE_ORDER.compare(
                this.title, other.title
        );

        if (result != 0) {
            return result;
        }

        return Integer.compare(this.id, other.id);
    }

    // Item identity is based on ID.
    @Override
    public final boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof LibraryItem)) {
            return false;
        }

        LibraryItem item = (LibraryItem) other;
        return id == item.id;
    }

    @Override
    public final int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
        return "id=" + id
                + ", title='" + title + '\''
                + ", year=" + year
                + ", status="
                + (available ? "Available" : "Issued");
    }
}