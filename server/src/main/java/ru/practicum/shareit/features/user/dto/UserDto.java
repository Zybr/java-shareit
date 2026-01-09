package ru.practicum.shareit.features.user.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.common.dto.ModelDto;

@Data
@RequiredArgsConstructor
public class UserDto implements ModelDto {
    private Long id;

    private final String name;

    private final String email;
}
