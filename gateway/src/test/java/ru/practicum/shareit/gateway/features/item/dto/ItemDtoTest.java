package ru.practicum.shareit.gateway.features.item.dto;
import ru.practicum.shareit.features.item.dto.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class ItemDtoTest {

    @Autowired
    private JacksonTester<CreateItemDto> createItemJson;

    @Autowired
    private JacksonTester<UpdateItemDto> updateItemJson;

    @Autowired
    private JacksonTester<CreateCommentDto> createCommentJson;

    @Test
    void testCreateItemDto() throws Exception {
        CreateItemDto dto = new CreateItemDto("Name", "Description", true, 1L);

        JsonContent<CreateItemDto> result = createItemJson.write(dto);

        assertThat(result).hasJsonPathStringValue("$.name", "Name");
        assertThat(result).hasJsonPathStringValue("$.description", "Description");
        assertThat(result).hasJsonPathBooleanValue("$.available", true);
        assertThat(result).hasJsonPathNumberValue("$.requestId", 1);
    }

    @Test
    void testUpdateItemDto() throws Exception {
        UpdateItemDto dto = new UpdateItemDto("New Name", "New Description", false);

        JsonContent<UpdateItemDto> result = updateItemJson.write(dto);

        assertThat(result).hasJsonPathStringValue("$.name", "New Name");
        assertThat(result).hasJsonPathStringValue("$.description", "New Description");
        assertThat(result).hasJsonPathBooleanValue("$.available", false);
    }

    @Test
    void testCreateCommentDto() throws Exception {
        CreateCommentDto dto = new CreateCommentDto("Comment");

        JsonContent<CreateCommentDto> result = createCommentJson.write(dto);

        assertThat(result).hasJsonPathStringValue("$.text", "Comment");
    }
}
