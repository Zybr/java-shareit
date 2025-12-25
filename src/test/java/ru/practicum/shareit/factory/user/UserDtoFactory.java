package ru.practicum.shareit.factory.user;

import ru.practicum.shareit.factory.DataFactory;
import ru.practicum.shareit.features.user.dto.UserDto;

public class UserDtoFactory extends DataFactory<UserDto> {
    @Override
    public UserDto make(UserDto attributes) {
        UserDto dto = attributes == null
                ? new UserDto(makeUniqueWord(), this.faker.internet().emailAddress())
                : new UserDto(attributes.getName(), attributes.getEmail());
        dto.setId(
                attributes == null
                        ? makeId()
                        : attributes.getId()
        );

        return dto;
    }
}
