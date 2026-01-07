package ru.practicum.shareit.factory;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.common.model.Model;

import java.util.List;

public abstract class ModelFactory<M extends Model> extends DataFactory<M> {
    protected final JpaRepository<M, Long> repository;

    protected ModelFactory(JpaRepository<M, Long> repository) {
        this.repository = repository;
    }

    public M create(M attributes) {
        return repository
                .saveAndFlush(
                        make(attributes)
                );
    }

    public M create() {
        return create(null);
    }

    public List<M> createList() {
        return createList(10);
    }

    public List<M> createList(int size) {
        return collectList(size, this::create);
    }
}
