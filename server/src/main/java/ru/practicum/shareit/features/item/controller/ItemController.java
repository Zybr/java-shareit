package ru.practicum.shareit.features.item.controller;

import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.common.constants.CustomHeaders;
import ru.practicum.shareit.features.item.dto.comment.CommentInpDto;
import ru.practicum.shareit.features.item.dto.comment.CommentOutDto;
import ru.practicum.shareit.features.item.dto.item.ItemDto;
import ru.practicum.shareit.features.item.mapper.comment.CommentMapper;
import ru.practicum.shareit.features.item.mapper.item.ItemMapper;
import ru.practicum.shareit.features.item.service.CommentService;
import ru.practicum.shareit.features.item.service.ItemService;

import java.util.List;

@RestController
@RequestMapping(path = "/items")
public class ItemController {
    private final ItemService itemService;
    private final ItemMapper itemMapper;

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    public ItemController(
            ItemService service,
            ItemMapper itemMapper,

            CommentService commentService,
            CommentMapper commentMapper
    ) {
        this.itemService = service;
        this.itemMapper = itemMapper;

        this.commentService = commentService;
        this.commentMapper = commentMapper;
    }

    @GetMapping
    public List<ItemDto> getItems(
            @RequestHeader(CustomHeaders.USER_ID) Long userId
    ) {
        return itemMapper.toDto(
                itemService.findListByOwner(userId)
        );
    }

    @GetMapping("/search")
    public List<ItemDto> searchItems(
            @RequestHeader(CustomHeaders.USER_ID) Long userId,
            @RequestParam("text") String searchText
    ) {
        return itemMapper.toDto(
                itemService.findListByOwner(
                        userId,
                        searchText
                )
        );
    }

    @GetMapping("{id}")
    public ItemDto getItem(
            @PathVariable Long id
    ) {
        return itemMapper.toDetailedDto(
                itemService.getOne(id)
        );
    }

    @PostMapping
    public ItemDto createItem(
            @RequestBody ItemDto creation,
            @RequestHeader(CustomHeaders.USER_ID) Long userId
    ) {
        creation.setOwnerId(userId);

        return itemMapper.toDto(
                itemService.createOne(
                        itemMapper.toModel(creation)
                )
        );
    }

    @PatchMapping("{id}")
    public ItemDto updateItem(
            @PathVariable Long id,
            @RequestBody ItemDto update,
            @RequestHeader(CustomHeaders.USER_ID) Long userId
    ) {
        update.setId(id);
        update.setOwnerId(userId);

        return itemMapper.toDto(
                itemService.updateOne(
                        itemMapper.toModel(update)
                )
        );
    }

    @DeleteMapping("{id}")
    public void removeItem(
            @PathVariable Long id
    ) {
        itemService.deleteOne(id);
    }

    @PostMapping("{id}/comment")
    public CommentOutDto createComment(
            @PathVariable("id") Long itemId,
            @RequestBody CommentInpDto creation,
            @RequestHeader(CustomHeaders.USER_ID) Long userId
    ) {
        creation.setItemId(itemId);
        creation.setAuthorId(userId);

        return commentMapper.toDto(
                commentService.createOne(
                        commentMapper.toModel(
                                creation
                        )
                )
        );
    }
}
