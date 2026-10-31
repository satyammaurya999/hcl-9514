
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public class SearchCache {

    private static final int MAX_ENTRIES = 5;

    private final Map<String, List<LibraryItem>> cache =
            new LinkedHashMap<String, List<LibraryItem>>(
                    MAX_ENTRIES, 0.75f, true) {

                @Override
                protected boolean removeEldestEntry(
                        Map.Entry<String, List<LibraryItem>> eldest) {

                    return size() > MAX_ENTRIES;
                }
            };

    public Optional<List<LibraryItem>> getCached(String title) {
        return Optional.ofNullable(cache.get(normalize(title)))
                .map(List::copyOf);
    }

    public void put(
            String title, List<LibraryItem> results) {

        cache.put(normalize(title), List.copyOf(results));
    }

    public int size() {
        return cache.size();
    }

    public void clear() {
        cache.clear();
    }

    private String normalize(String title) {
        if (title == null) {
            return "";
        }

        return title.trim().toLowerCase(Locale.ROOT);
    }
}