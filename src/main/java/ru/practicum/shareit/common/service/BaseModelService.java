package ru.practicum.shareit.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.common.exception.NotFoundException;
import ru.practicum.shareit.common.model.Model;

import java.util.List;

@RequiredArgsConstructor
public abstract class BaseModelService<M extends Model> implements ModelService<M> {
    protected final JpaRepository<M, Long> repository;

    @Override
    public M getOne(Long id) {
        return repository
                .findById(id)
                .orElseThrow(
                        () -> new NotFoundException(
                                String.format(
                                        "User \"%d\" not found",
                                        id
                                )
                        )
                );
    }

    @Override
    public boolean isExisted(Long id) {
        return repository.existsById(id);
    }

    @Override
    public List<M> findList() {
        return repository.findAll();
    }

    @Override
    public M createOne(M creation) {
        return repository.saveAndFlush(creation);
    }

    @Override
    public M updateOne(M update) {
        return repository.saveAndFlush(
                fill(
                        update,
                        this.getOne(
                                update.getId()
                        )
                )
        );
    }

    @Override
    public void deleteOne(Long id) {
        repository.deleteById(id);
    }

    protected abstract M fill(M source, M target);

    protected <T> T getValueOrDefault(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }
}
