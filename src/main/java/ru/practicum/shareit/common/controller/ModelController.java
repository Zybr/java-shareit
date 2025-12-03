package ru.practicum.shareit.common.controller;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.common.dto.ModelDto;
import ru.practicum.shareit.common.mapper.ModelMapper;
import ru.practicum.shareit.common.model.Model;

import java.util.List;

@RequiredArgsConstructor
public class ModelController<M extends Model, D extends ModelDto> {
    protected final ModelMapper<M, D> mapper;

    protected M toModel(D dto) {
        return this.mapper.toModel(dto);
    }

    protected D toDto(M model) {
        return this.mapper.toDto(model);
    }

    protected List<D> toDto(List<M> models) {
        return models
                .stream()
                .map(this::toDto)
                .toList();
    }
}
