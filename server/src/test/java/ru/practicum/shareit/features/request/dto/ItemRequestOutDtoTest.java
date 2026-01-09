package ru.practicum.shareit.features.request.dto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.common.dto.DtoTestCase;
import ru.practicum.shareit.database.providers.FactoryProvider;
import ru.practicum.shareit.database.providers.RepositoryProvider;
import ru.practicum.shareit.features.item.dto.item.ItemDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class ItemRequestOutDtoTest extends DtoTestCase<ItemRequestOutDto> {
    private final FactoryProvider factoryProvider = new FactoryProvider(new RepositoryProvider(null, null, null, null, null));

    @Test
    void shouldSerializeItemRequestOutDto() throws Exception {
        ItemDto itemDto = factoryProvider.itemDto().make(new ItemDto("Item Name", "Item Description", true, 1L));
        itemDto.setId(10L);

        ItemRequestOutDto attributes = new ItemRequestOutDto(
                1L,
                "Request description",
                2L,
                LocalDate.of(2024, 1, 1),
                List.of(itemDto)
        );
        ItemRequestOutDto dto = factoryProvider.itemRequestOut().make(attributes);

        JsonContent<ItemRequestOutDto> result = json.write(dto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo("Request description");
        assertThat(result).extractingJsonPathNumberValue("$.requester").isEqualTo(2);

        assertThat(result).extractingJsonPathStringValue("$.created").contains(formatTime(dto.getCreated()));

        assertThat(result).extractingJsonPathArrayValue("$.items").hasSize(1);
        assertThat(result).extractingJsonPathNumberValue("$.items[0].id").isEqualTo(10);
        assertThat(result).extractingJsonPathStringValue("$.items[0].name").isEqualTo("Item Name");
    }

    @Test
    void shouldDeserializeItemRequestOutDto() throws Exception {
        Map<String, Object> data = Map.of(
                "id", 1,
                "description", "Request description",
                "requester", 2,
                "created", "2024-01-01",
                "items", List.of(Map.of(
                        "id", 10,
                        "name", "Item Name"
                ))
        );

        String content = objectMapper.writeValueAsString(data);
        ItemRequestOutDto dto = objectMapper.readValue(content, ItemRequestOutDto.class);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getDescription()).isEqualTo("Request description");
        assertThat(dto.getRequester()).isEqualTo(2L);
        assertThat(dto.getCreated()).isEqualTo(parseDate((String) data.get("created")));
        assertThat(dto.getItems()).hasSize(1);
        assertThat(dto.getItems().getFirst().getId()).isEqualTo(10L);
    }
}
