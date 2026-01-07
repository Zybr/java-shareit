package ru.practicum.shareit.features.item.dto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.common.dto.DtoTestCase;
import ru.practicum.shareit.database.providers.FactoryProvider;
import ru.practicum.shareit.database.providers.RepositoryProvider;
import ru.practicum.shareit.features.item.dto.comment.CommentInpDto;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class CommentInpDtoTest extends DtoTestCase<CommentInpDto> {
    private final FactoryProvider factoryProvider = new FactoryProvider(new RepositoryProvider(null, null, null, null, null));

    @Test
    void shouldSerializeCommentInpDto() throws Exception {
        CommentInpDto dto = factoryProvider.commentInpDto().make(new CommentInpDto(1L, "Comment text"));
        dto.setItemId(2L);
        dto.setAuthorId(3L);

        JsonContent<CommentInpDto> result = json.write(dto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.text").isEqualTo("Comment text");
        assertThat(result).extractingJsonPathNumberValue("$.itemId").isEqualTo(2);
        assertThat(result).extractingJsonPathNumberValue("$.authorId").isEqualTo(3);
    }

    @Test
    void shouldDeserializeCommentInpDto() throws Exception {
        Map<String, Object> data = Map.of(
                "id", 1,
                "text", "Comment text",
                "itemId", 2,
                "authorId", 3
        );

        String content = objectMapper.writeValueAsString(data);
        CommentInpDto dto = objectMapper.readValue(content, CommentInpDto.class);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getText()).isEqualTo("Comment text");
        assertThat(dto.getItemId()).isEqualTo(2L);
        assertThat(dto.getAuthorId()).isEqualTo(3L);
    }
}
