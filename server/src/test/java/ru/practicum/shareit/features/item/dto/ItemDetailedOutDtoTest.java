package ru.practicum.shareit.features.item.dto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.common.dto.DtoTestCase;
import ru.practicum.shareit.database.providers.FactoryProvider;
import ru.practicum.shareit.database.providers.RepositoryProvider;
import ru.practicum.shareit.features.item.dto.comment.CommentOutDto;
import ru.practicum.shareit.features.item.dto.item.ItemDetailedOutDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class ItemDetailedOutDtoTest extends DtoTestCase<ItemDetailedOutDto> {
    private final FactoryProvider factoryProvider = new FactoryProvider(new RepositoryProvider(null, null, null, null, null));

    @Test
    void shouldSerializeItemDetailedOutDto() throws Exception {
        LocalDateTime now = LocalDateTime.of(2024, 1, 1, 12, 0, 0);
        LocalDateTime last = now.minusDays(1);
        LocalDateTime next = now.plusDays(1);

        CommentOutDto comment = factoryProvider.commentOutDto().make(new CommentOutDto(1L, "Comment text", "Author Name", now));
        ItemDetailedOutDto attributes = new ItemDetailedOutDto(
                "Item name",
                "Item description",
                true,
                2L,
                last,
                next
        );
        attributes.setId(1L);
        attributes.setComments(List.of(comment));

        ItemDetailedOutDto dto = factoryProvider.itemDetailedOut().make(attributes);

        JsonContent<ItemDetailedOutDto> result = json.write(dto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("Item name");
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo("Item description");
        assertThat(result).extractingJsonPathBooleanValue("$.available").isTrue();
        assertThat(result).extractingJsonPathNumberValue("$.requestId").isEqualTo(2);

        assertThat(result).extractingJsonPathStringValue("$.lastBooking").isEqualTo(formatTime(last));
        assertThat(result).extractingJsonPathStringValue("$.nextBooking").isEqualTo(formatTime(next));

        assertThat(result).extractingJsonPathArrayValue("$.comments").hasSize(1);
        assertThat(result).extractingJsonPathStringValue("$.comments[0].text").isEqualTo("Comment text");
        assertThat(result).extractingJsonPathStringValue("$.comments[0].authorName").isEqualTo("Author Name");
        assertThat(result).extractingJsonPathStringValue("$.comments[0].created").isEqualTo(formatTime(now));
    }

    @Test
    void shouldDeserializeItemDetailedOutDto() throws Exception {
        Map<String, Object> data = Map.of(
                "id", 1,
                "name", "Item name",
                "description", "Item description",
                "available", true,
                "requestId", 2,
                "lastBooking", "2023-12-31T12:00:00",
                "nextBooking", "2024-01-02T12:00:00"
        );

        String content = objectMapper.writeValueAsString(data);
        ItemDetailedOutDto dto = objectMapper.readValue(content, ItemDetailedOutDto.class);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("Item name");
        assertThat(dto.getDescription()).isEqualTo("Item description");
        assertThat(dto.getAvailable()).isTrue();
        assertThat(dto.getRequestId()).isEqualTo(2L);
        assertThat(dto.getLastBooking()).isEqualTo(parseTime((String) data.get("lastBooking")));
        assertThat(dto.getNextBooking()).isEqualTo(parseTime((String) data.get("nextBooking")));
    }
}
