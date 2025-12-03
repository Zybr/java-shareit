package ru.practicum.shareit.common.repository;

import ru.practicum.shareit.common.exception.NotFoundException;
import ru.practicum.shareit.common.model.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Repository for models in memory
 */
public abstract class ModelRepositoryMemory<M extends Model> implements ModelRepository<M> {
    private long lastId = 0L;
    protected final Map<Long, M> models = new HashMap<>();

    @Override
    public Optional<M> findOne(Long id) {
        return Optional.ofNullable(models.getOrDefault(id, null));
    }

    @Override
    public boolean isExisted(Long id) {
        return models.containsKey(id);
    }

    @Override
    public List<M> findList() {
        return models
                .values()
                .stream()
                .toList();
    }

    @Override
    public M createOne(M creation) {
        creation.setId(makeNextId());

        models.put(
                creation.getId(),
                creation
        );

        return models.get(
                creation.getId()
        );
    }

    @Override
    public M updateOne(M update) {
        assertExists(update.getId());

        return fill(
                update,
                models.get(update.getId())
        );
    }

    @Override
    public M deleteOne(Long id) {
        assertExists(id);

        return models.remove(id);
    }

    protected abstract M fill(M source, M target);

    protected <T> T getValueOrDefault(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }

    private Long makeNextId() {
        lastId += 1;
        return lastId;
    }

    private void assertExists(Long id) {
        if (!models.containsKey(id)) {
            throw new NotFoundException(
                    String.format(
                            "There is no model with ID \"%d\"",
                            id
                    )
            );
        }
    }
}
