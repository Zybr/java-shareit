package ru.practicum.shareit.features.user.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.common.validation.action.OnCreate;
import ru.practicum.shareit.common.validation.action.OnPartialUpdate;
import ru.practicum.shareit.features.user.dto.UserDto;
import ru.practicum.shareit.features.user.mapper.UserMapper;
import ru.practicum.shareit.features.user.service.UserService;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class UserController {
    protected final UserMapper mapper;


    private final UserService service;

    public UserController(
            UserMapper mapper,
            UserService service
    ) {
        this.mapper = mapper;
        this.service = service;
    }

    @GetMapping
    public List<UserDto> getUsers() {
        return mapper.toDto(service.findList());
    }

    @GetMapping("{id}")
    public UserDto getUser(
            @PathVariable Long id
    ) {
        return mapper.toDto(
                service.getOne(id)
        );
    }

    @PostMapping
    public UserDto createUser(
            @RequestBody @Validated(OnCreate.class) UserDto creation
    ) {
        return mapper.toDto(
                service.createOne(
                        mapper.toModel(creation)
                )
        );
    }

    @PatchMapping("{id}")
    public UserDto updateUser(
            @PathVariable Long id,
            @RequestBody @Validated(OnPartialUpdate.class) UserDto update
    ) {
        update.setId(id);

        return mapper.toDto(
                service.updateOne(
                        mapper.toModel(update)
                )
        );
    }

    @DeleteMapping("{id}")
    public void removeUser(
            @PathVariable Long id
    ) {
        service.deleteOne(id);
    }
}
