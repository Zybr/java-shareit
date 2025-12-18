package ru.practicum.shareit.common.controller;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.common.dto.ModelDto;
import ru.practicum.shareit.common.mapper.ModelMapper;
import ru.practicum.shareit.common.model.Model;

import java.util.List;

@RequiredArgsConstructor
public class ModelController<M extends Model, I extends ModelDto, O extends ModelDto> {
    protected final ModelMapper<M, I> inpMapper;
    protected final ModelMapper<M, O> outMapper;

    protected M toInpModel(I inpDto) {
        return this.inpMapper.toModel(inpDto);
    }

    protected O toOutDto(M model) {
        return this.outMapper.toDto(model);
    }

    protected List<O> toOutDto(List<M> modelList) {
        return modelList
                .stream()
                .map(this::toOutDto)
                .toList();
    }
}
