package ru.practicum.shareit.features.request.dto;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import ru.practicum.shareit.common.annotations.SerializableDateTime;
import ru.practicum.shareit.common.dto.ModelDto;
import ru.practicum.shareit.features.item.dto.item.ItemDto;

import java.time.LocalDate;
import java.util.List;

@Value
@RequiredArgsConstructor
public class ItemRequestOutDto implements ModelDto {
    public Long id;
    String description;
    Long requester;

    @SerializableDateTime
    LocalDate created;

    List<ItemDto> items;
}
