
public class Magazine extends LibraryItem {

    private int issueNumber;

    public Magazine(String title, int year, int issueNumber) {
        super(title, year);
        setIssueNumber(issueNumber);
    }

    public int getIssueNumber() {
        return issueNumber;
    }

    public final void setIssueNumber(int issueNumber) {
        if (issueNumber <= 0) {
            throw new IllegalArgumentException(
                    "Issue number must be positive."
            );
        }

        this.issueNumber = issueNumber;
    }

    @Override
    public double lateFee(int daysLate) {
        validateDaysLate(daysLate);
        return daysLate * 1.0;
    }

    @Override
    public String toString() {
        return "Magazine[" + super.toString()
                + ", issueNumber=" + issueNumber + "]";
    }
}