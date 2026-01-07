package ru.practicum.shareit.features.item.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.common.client.BaseClient;
import ru.practicum.shareit.features.item.dto.CreateCommentDto;
import ru.practicum.shareit.features.item.dto.CreateItemDto;
import ru.practicum.shareit.features.item.dto.UpdateItemDto;

import java.util.Map;

@Component
public class ItemClient extends BaseClient {
    @Override
    protected String getPathPrefix() {
        return "items";
    }

    public ResponseEntity<Object> getItems(long userId) {
        return get(
                "",
                userId
        );
    }

    public ResponseEntity<Object> getItem(
            long itemId
    ) {
        return get(
                "/" + itemId
        );
    }

    public ResponseEntity<Object> searchItems(
            long userId,
            String searchText

    ) {
        return get(
                "/search?text={text}",
                userId,
                Map.of(
                        "text",
                        searchText)
        );
    }

    public ResponseEntity<Object> createItem(
            long userId,
            CreateItemDto createDto
    ) {
        return post(
                "",
                userId,
                createDto
        );
    }

    public ResponseEntity<Object> updateItem(
            long userId,
            long itemId,
            UpdateItemDto updateDto
    ) {
        return patch(
                "/" + itemId,
                userId,
                updateDto
        );
    }

    public ResponseEntity<Object> deleteItem(
            long itemId
    ) {
        return delete(
                "/" + itemId
        );
    }

    public ResponseEntity<Object> createComment(
            long userId,
            long itemId,
            CreateCommentDto commentDto
    ) {
        return post(
                "/" + itemId + "/comment",
                userId,
                commentDto
        );
    }
}
