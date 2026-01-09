package ru.practicum.shareit.features.request.dto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.common.dto.DtoTestCase;
import ru.practicum.shareit.database.providers.FactoryProvider;
import ru.practicum.shareit.database.providers.RepositoryProvider;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class ItemRequestCreationDtoTest extends DtoTestCase<ItemRequestCreationDto> {
    private final FactoryProvider factoryProvider = new FactoryProvider(new RepositoryProvider(null, null, null, null, null));

    @Test
    void shouldSerializeItemRequestCreationDto() throws Exception {
        ItemRequestCreationDto dto = factoryProvider.itemRequestCreationDto().make(new ItemRequestCreationDto().setDescription("Description"));
        dto.setRequesterId(1L);

        JsonContent<ItemRequestCreationDto> result = json.write(dto);

        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo("Description");
        assertThat(result).extractingJsonPathNumberValue("$.requesterId").isEqualTo(1);
    }

    @Test
    void shouldDeserializeItemRequestCreationDto() throws Exception {
        Map<String, Object> data = Map.of(
                "description", "Description",
                "requesterId", 1
        );

        String content = objectMapper.writeValueAsString(data);
        ItemRequestCreationDto dto = objectMapper.readValue(content, ItemRequestCreationDto.class);

        assertThat(dto.getDescription()).isEqualTo("Description");
        assertThat(dto.getRequesterId()).isEqualTo(1L);
    }
}
