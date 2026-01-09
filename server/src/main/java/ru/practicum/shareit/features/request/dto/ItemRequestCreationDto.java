package ru.practicum.shareit.features.request.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@RequiredArgsConstructor
public class ItemRequestCreationDto {
    private String description;

    private Long requesterId;
}
