
public class Book extends LibraryItem {

    private String author;
    private final String isbn;

    public Book(String title, int year,
                String author, String isbn) {

        super(title, year);

        setAuthor(author);
        this.isbn = validateIsbn(isbn);
    }

    public String getAuthor() {
        return author;
    }

    public final void setAuthor(String author) {
        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException(
                    "Author must not be blank."
            );
        }

        this.author = author.trim();
    }

    public String getIsbn() {
        return isbn;
    }

    private String validateIsbn(String isbn) {
        if (isbn == null || isbn.isBlank()) {
            throw new IllegalArgumentException(
                    "ISBN must not be blank."
            );
        }

        return isbn.trim();
    }

    @Override
    public double lateFee(int daysLate) {
        validateDaysLate(daysLate);
        return daysLate * 2.0;
    }

    @Override
    public String toString() {
        return "Book[" + super.toString()
                + ", author='" + author + '\''
                + ", isbn='" + isbn + "']";
    }
}