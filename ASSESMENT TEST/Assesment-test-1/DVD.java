
public class DVD extends LibraryItem {

    private int durationMinutes;

    public DVD(String title, int year, int durationMinutes) {
        super(title, year);
        setDurationMinutes(durationMinutes);
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public final void setDurationMinutes(int durationMinutes) {
        if (durationMinutes <= 0) {
            throw new IllegalArgumentException(
                    "DVD duration must be positive."
            );
        }

        this.durationMinutes = durationMinutes;
    }

    @Override
    public double lateFee(int daysLate) {
        validateDaysLate(daysLate);
        return daysLate * 5.0;
    }

    @Override
    public String toString() {
        return "DVD[" + super.toString()
                + ", durationMinutes=" + durationMinutes + "]";
    }
}