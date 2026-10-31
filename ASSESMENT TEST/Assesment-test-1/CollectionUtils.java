
import com.hcl.library.model.LibraryItem;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public final class CollectionUtils {

    private CollectionUtils() {
        // Prevent object creation.
    }

    public static <T extends Comparable<? super T>>
    T maxOf(List<T> list) {

        Objects.requireNonNull(list, "List cannot be null.");

        if (list.isEmpty()) {
            throw new NoSuchElementException(
                    "Cannot find a maximum in an empty list."
            );
        }

        T maximum = Objects.requireNonNull(
                list.get(0), "List cannot contain null elements."
        );

        for (int index = 1; index < list.size(); index++) {
            T current = Objects.requireNonNull(
                    list.get(index),
                    "List cannot contain null elements."
            );

            if (current.compareTo(maximum) > 0) {
                maximum = current;
            }
        }

        return maximum;
    }

    public static double totalFees(
            List<? extends LibraryItem> items,
            int daysLate) {

        Objects.requireNonNull(items, "Items list cannot be null.");

        if (daysLate < 0) {
            throw new IllegalArgumentException(
                    "Days late cannot be negative."
            );
        }

        double total = 0.0;

        for (LibraryItem item : items) {
            Objects.requireNonNull(
                    item, "Items list cannot contain null."
            );

            total += item.lateFee(daysLate);
        }

        return total;
    }
}