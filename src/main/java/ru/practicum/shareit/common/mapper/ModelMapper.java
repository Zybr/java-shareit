package ru.practicum.shareit.common.mapper;

import ru.practicum.shareit.common.dto.ModelDto;
import ru.practicum.shareit.common.model.Model;

import java.util.List;

public abstract class ModelMapper<M extends Model, D extends ModelDto> {
    public abstract D toDto(M model);

    public List<D> toDtoList(List<M> models) {
        return models
                .stream()
                .map(this::toDto)
                .toList();
    }

    public abstract M toModel(D dto);

    public List<M> toModelList(List<D> dtos) {
        return dtos
                .stream()
                .map(this::toModel)
                .toList();
    }

    public abstract M copyModel(M model);
}
