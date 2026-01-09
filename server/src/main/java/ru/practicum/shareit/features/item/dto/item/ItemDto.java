package ru.practicum.shareit.features.item.dto.item;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.common.dto.ModelDto;

@Data
@RequiredArgsConstructor
public class ItemDto implements ModelDto {
    private Long id;

    private final String name;

    private final String description;

    private final Boolean available;

    private Long ownerId;

    private final Long requestId;
}
