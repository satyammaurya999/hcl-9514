
public class BorrowLimitExceededException extends LibraryException {

    private static final long serialVersionUID = 1L;

    public BorrowLimitExceededException(String message) {
        super(message);
    }

    public BorrowLimitExceededException(
            String message, Throwable cause) {
        super(message, cause);
    }
}