package ru.practicum.shareit.features.user.dto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.common.dto.DtoTestCase;
import ru.practicum.shareit.database.providers.FactoryProvider;
import ru.practicum.shareit.database.providers.RepositoryProvider;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class UserDtoTest extends DtoTestCase<UserDto> {
    private final FactoryProvider factoryProvider = new FactoryProvider(new RepositoryProvider(null, null, null, null, null));

    @Test
    void shouldSerializeUserDto() throws Exception {
        UserDto dto = factoryProvider.userDto().make(new UserDto("User Name", "user@email.com"));
        dto.setId(1L);

        JsonContent<UserDto> result = json.write(dto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("User Name");
        assertThat(result).extractingJsonPathStringValue("$.email").isEqualTo("user@email.com");
    }

    @Test
    void shouldDeserializeUserDto() throws Exception {
        Map<String, Object> data = Map.of(
                "id", 1,
                "name", "User Name",
                "email", "user@email.com"
        );

        String content = objectMapper.writeValueAsString(data);
        UserDto dto = objectMapper.readValue(content, UserDto.class);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("User Name");
        assertThat(dto.getEmail()).isEqualTo("user@email.com");
    }
}
