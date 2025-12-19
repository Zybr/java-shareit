package ru.practicum.shareit.features.user.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.common.mapper.ModelMapper;
import ru.practicum.shareit.features.user.dto.UserDto;
import ru.practicum.shareit.features.user.model.User;

@Service
@RequiredArgsConstructor
public class UserMapper extends ModelMapper<User, UserDto> {
    private final ObjectMapper objectMapper;

    @Override
    public UserDto toDto(User model) {
        return objectMapper.convertValue(model, UserDto.class);
    }

    @Override
    public User toModel(UserDto dto) {
        return objectMapper.convertValue(dto, User.class);
    }
}
