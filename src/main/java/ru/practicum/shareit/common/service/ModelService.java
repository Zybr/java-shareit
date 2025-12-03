package ru.practicum.shareit.common.service;

import ru.practicum.shareit.common.model.Model;

import java.util.List;

public interface ModelService<M extends Model> {
    M getOne(Long id);

    boolean isExisted(Long id);

    List<M> findList();

    M createOne(M creation);

    M updateOne(M update);

    M deleteOne(Long id);
}
