package ru.practicum.shareit.features.request.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@RequiredArgsConstructor
public class ItemRequestCreationDto {
    @NotEmpty()
    private String description;

    private Long requesterId;
}
