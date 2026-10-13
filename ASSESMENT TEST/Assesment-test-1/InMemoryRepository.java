
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public class InMemoryRepository<T, ID>
        implements Repository<T, ID> {

    private final Map<ID, T> storage;
    private final Function<T, ID> idExtractor;

    public InMemoryRepository(Function<T, ID> idExtractor) {
        this(idExtractor, new HashMap<>());
    }

    public InMemoryRepository(
            Function<T, ID> idExtractor,
            Map<ID, T> backingMap) {

        this.idExtractor = Objects.requireNonNull(
                idExtractor, "ID extractor cannot be null."
        );

        this.storage = Objects.requireNonNull(
                backingMap, "Backing map cannot be null."
        );
    }

    @Override
    public void save(T entity) {
        Objects.requireNonNull(entity, "Entity cannot be null.");

        ID id = Objects.requireNonNull(
                idExtractor.apply(entity),
                "Entity ID cannot be null."
        );

        storage.put(id, entity);
    }

    @Override
    public Optional<T> findById(ID id) {
        Objects.requireNonNull(id, "ID cannot be null.");

        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<T> findAll() {
        return List.copyOf(storage.values());
    }

    @Override
    public boolean deleteById(ID id) {
        Objects.requireNonNull(id, "ID cannot be null.");

        return storage.remove(id) != null;
    }
}