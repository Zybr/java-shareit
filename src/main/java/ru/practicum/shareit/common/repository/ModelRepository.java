package ru.practicum.shareit.common.repository;


import ru.practicum.shareit.common.model.Model;

import java.util.List;
import java.util.Optional;

public interface ModelRepository<M extends Model> {
    Optional<M> findOne(Long id);

    boolean isExisted(Long id);

    List<M> findList();

    M createOne(M creation);

    M updateOne(M update);

    M deleteOne(Long id);
}
