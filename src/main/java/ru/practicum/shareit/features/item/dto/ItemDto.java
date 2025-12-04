package ru.practicum.shareit.features.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.common.dto.ModelDto;
import ru.practicum.shareit.common.validation.action.OnCreate;

@Data
@RequiredArgsConstructor
public class ItemDto implements ModelDto {
    private Long id;

    @NotBlank(groups = OnCreate.class)
    private final String name;

    @NotBlank(groups = OnCreate.class)
    private final String description;

    @NotNull(groups = OnCreate.class)
    private final Boolean available;

    private Long owner;

    private final Long request;
}
