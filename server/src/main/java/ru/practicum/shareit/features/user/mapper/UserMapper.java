package ru.practicum.shareit.features.user.mapper;

import org.mapstruct.Mapper;
import ru.practicum.shareit.features.user.dto.UserDto;
import ru.practicum.shareit.features.user.model.User;

import java.util.List;


@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User model);

    List<UserDto> toDto(List<User> models);

    User toModel(UserDto dto);
}