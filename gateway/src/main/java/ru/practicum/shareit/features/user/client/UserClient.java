package ru.practicum.shareit.features.user.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.common.client.BaseClient;
import ru.practicum.shareit.features.user.dto.CreateUserDto;
import ru.practicum.shareit.features.user.dto.UpdateUserDto;

@Component
public class UserClient extends BaseClient {
    @Override
    protected String getPathPrefix() {
        return "users";
    }

    public ResponseEntity<Object> getUsers() {
        return get("");
    }

    public ResponseEntity<Object> getUser(
            long userId
    ) {
        return get("/" + userId);
    }

    public ResponseEntity<Object> createUser(
            CreateUserDto createDto
    ) {
        return post("", createDto);
    }

    public ResponseEntity<Object> updateUser(
            long userId,
            UpdateUserDto updateDto
    ) {
        return patch("/" + userId, updateDto);
    }

    public ResponseEntity<Object> deleteUser(
            long userId
    ) {
        return delete("/" + userId);
    }
}
