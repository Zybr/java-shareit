package ru.practicum.shareit.gateway.features.user.dto;
import ru.practicum.shareit.features.user.dto.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class UserDtoTest {

    @Autowired
    private JacksonTester<CreateUserDto> createJson;

    @Autowired
    private JacksonTester<UpdateUserDto> updateJson;

    @Test
    void testCreateUserDto() throws Exception {
        CreateUserDto dto = new CreateUserDto("Name", "email@mail.com");

        JsonContent<CreateUserDto> result = createJson.write(dto);

        assertThat(result).hasJsonPathStringValue("$.name", "Name");
        assertThat(result).hasJsonPathStringValue("$.email", "email@mail.com");
    }

    @Test
    void testUpdateUserDto() throws Exception {
        UpdateUserDto dto = new UpdateUserDto("New Name", "new@mail.com");

        JsonContent<UpdateUserDto> result = updateJson.write(dto);

        assertThat(result).hasJsonPathStringValue("$.name", "New Name");
        assertThat(result).hasJsonPathStringValue("$.email", "new@mail.com");
    }
}
