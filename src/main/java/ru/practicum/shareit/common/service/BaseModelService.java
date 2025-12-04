package ru.practicum.shareit.common.service;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.common.exception.NotFoundException;
import ru.practicum.shareit.common.model.Model;
import ru.practicum.shareit.common.repository.ModelRepository;

import java.util.List;

@RequiredArgsConstructor
public abstract class BaseModelService<M extends Model> implements ModelService<M> {
    protected final ModelRepository<M> repository;

    @Override
    public M getOne(Long id) {
        return repository
                .findOne(id)
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
        return repository.isExisted(id);
    }

    @Override
    public List<M> findList() {
        return repository.findList();
    }

    @Override
    public M createOne(M creation) {
        return repository.createOne(creation);
    }

    @Override
    public M updateOne(M update) {
        return repository.updateOne(update);
    }

    @Override
    public M deleteOne(Long id) {
        return repository.deleteOne(id);
    }
}
