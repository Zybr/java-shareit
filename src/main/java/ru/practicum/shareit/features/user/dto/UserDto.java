package ru.practicum.shareit.features.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.common.dto.ModelDto;
import ru.practicum.shareit.common.validation.action.OnCreate;
import ru.practicum.shareit.common.validation.action.OnPartialUpdate;

@Data
@RequiredArgsConstructor
public class UserDto implements ModelDto {
    private Long id;

    @NotBlank(groups = OnCreate.class)
    private final String name;

    @NotBlank(groups = OnCreate.class)
    @Email(groups = {OnCreate.class, OnPartialUpdate.class})
    private final String email;
}
