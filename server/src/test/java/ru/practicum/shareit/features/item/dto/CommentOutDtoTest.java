package ru.practicum.shareit.features.item.dto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.common.dto.DtoTestCase;
import ru.practicum.shareit.database.providers.FactoryProvider;
import ru.practicum.shareit.database.providers.RepositoryProvider;
import ru.practicum.shareit.features.item.dto.comment.CommentOutDto;

import java.time.LocalDateTime;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class CommentOutDtoTest extends DtoTestCase<CommentOutDto> {
    private final FactoryProvider factoryProvider = new FactoryProvider(new RepositoryProvider(null, null, null, null, null));

    @Test
    void shouldSerializeCommentOutDto() throws Exception {
        LocalDateTime created = LocalDateTime.of(2024, 1, 1, 12, 0, 0);
        CommentOutDto dto = factoryProvider.commentOutDto().make(new CommentOutDto(1L, "Comment text", "Author Name", created));

        JsonContent<CommentOutDto> result = json.write(dto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.text").isEqualTo("Comment text");
        assertThat(result).extractingJsonPathStringValue("$.authorName").isEqualTo("Author Name");
        assertThat(result).extractingJsonPathStringValue("$.created").isEqualTo(formatTime(created));
    }

    @Test
    void shouldDeserializeCommentOutDto() throws Exception {
        Map<String, Object> data = Map.of(
                "id", 1,
                "text", "Comment text",
                "authorName", "Author Name",
                "created", "2024-01-01T12:00:00"
        );

        String content = objectMapper.writeValueAsString(data);
        CommentOutDto dto = objectMapper.readValue(content, CommentOutDto.class);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getText()).isEqualTo("Comment text");
        assertThat(dto.getAuthorName()).isEqualTo("Author Name");
        assertThat(dto.getCreated()).isEqualTo(parseTime((String) data.get("created")));
    }
}
