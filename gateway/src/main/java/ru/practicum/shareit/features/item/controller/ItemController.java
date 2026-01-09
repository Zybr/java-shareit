package ru.practicum.shareit.features.item.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.common.constant.CustomHeaders;
import ru.practicum.shareit.features.item.client.ItemClient;
import ru.practicum.shareit.features.item.dto.CreateCommentDto;
import ru.practicum.shareit.features.item.dto.CreateItemDto;
import ru.practicum.shareit.features.item.dto.UpdateItemDto;

import java.util.Collections;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Validated
public class ItemController {
    private final ItemClient itemClient;

    @GetMapping
    public ResponseEntity<Object> getItems(
            @RequestHeader(CustomHeaders.USER_ID) @Positive Long userId
    ) {
        return itemClient.getItems(userId);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getItem(
            @PathVariable Long itemId
    ) {
        return itemClient.getItem(itemId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchItems(
            @RequestHeader(CustomHeaders.USER_ID) @Positive Long userId,
            @RequestParam("text") String searchText
    ) {
        if (searchText.trim().isEmpty()) {
            return ResponseEntity
                    .ok()
                    .body(Collections.emptyList());
        }

        return itemClient.searchItems(userId, searchText);
    }

    @PostMapping
    public ResponseEntity<Object> createItem(
            @RequestHeader(CustomHeaders.USER_ID) @Positive Long userId,
            @RequestBody @Valid CreateItemDto createDto
    ) {
        return itemClient.createItem(userId, createDto);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> updateItem(
            @RequestHeader(CustomHeaders.USER_ID) @Positive Long userId,
            @PathVariable Long itemId,
            @RequestBody UpdateItemDto updateDto
    ) {
        return itemClient.updateItem(userId, itemId, updateDto);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Object> deleteItem(
            @PathVariable Long itemId
    ) {
        return itemClient.deleteItem(itemId);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> createComment(
            @RequestHeader(CustomHeaders.USER_ID) @Positive Long userId,
            @PathVariable Long itemId,
            @RequestBody @Valid CreateCommentDto commentDto
    ) {
        return itemClient.createComment(userId, itemId, commentDto);
    }
}
