package ru.practicum.shareit.features.item.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.common.constants.CustomHeaders;
import ru.practicum.shareit.common.controller.ModelController;
import ru.practicum.shareit.common.validation.action.OnCreate;
import ru.practicum.shareit.common.validation.action.OnPartialUpdate;
import ru.practicum.shareit.features.item.dto.comment.CommentInpDto;
import ru.practicum.shareit.features.item.dto.comment.CommentOutDto;
import ru.practicum.shareit.features.item.dto.item.ItemDto;
import ru.practicum.shareit.features.item.mapper.comment.CommentInpMapper;
import ru.practicum.shareit.features.item.mapper.comment.CommentOutMapper;
import ru.practicum.shareit.features.item.mapper.item.ItemDetailedOutMapper;
import ru.practicum.shareit.features.item.mapper.item.ItemMapper;
import ru.practicum.shareit.features.item.model.Item;
import ru.practicum.shareit.features.item.service.CommentService;
import ru.practicum.shareit.features.item.service.ItemService;

import java.util.List;

@RestController
@RequestMapping(path = "/items")
@Validated
public class ItemController extends ModelController<Item, ItemDto, ItemDto> {
    private final ItemService itemService;
    private final ItemDetailedOutMapper outDetailedMapper;

    private final CommentService commentService;
    private final CommentInpMapper commentInpMapper;
    private final CommentOutMapper commentOutMapper;

    public ItemController(
            ItemService service,
            ItemMapper mapper,
            ItemDetailedOutMapper itemOutDetailedMapper,

            CommentService commentService,
            CommentInpMapper commentInpMapper,
            CommentOutMapper commentOutMapper
    ) {
        super(mapper, mapper);
        this.itemService = service;
        this.outDetailedMapper = itemOutDetailedMapper;

        this.commentService = commentService;
        this.commentInpMapper = commentInpMapper;
        this.commentOutMapper = commentOutMapper;
    }

    @GetMapping
    public List<ItemDto> getItems(
            @RequestHeader(CustomHeaders.USER_ID) @Positive Long userId
    ) {
        return toOutDto(
                itemService.findListByOwner(userId)
        );
    }

    @GetMapping("/search")
    public List<ItemDto> searchItems(
            @RequestHeader(CustomHeaders.USER_ID) @Positive Long userId,
            @RequestParam("text") String searchText
    ) {
        return toOutDto(
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
        return outDetailedMapper.toDto(
                itemService.getOne(id)
        );
    }

    @PostMapping
    public ItemDto createItem(
            @RequestBody @Validated(OnCreate.class) ItemDto creation,
            @RequestHeader(CustomHeaders.USER_ID) @Positive Long userId
    ) {
        creation.setOwnerId(userId);

        return toOutDto(
                itemService.createOne(
                        toInpModel(creation)
                )
        );
    }

    @PatchMapping("{id}")
    public ItemDto updateItem(
            @PathVariable Long id,
            @RequestBody @Validated(OnPartialUpdate.class) ItemDto update,
            @RequestHeader(CustomHeaders.USER_ID) @Positive Long userId
    ) {
        update.setId(id);
        update.setOwnerId(userId);

        return toOutDto(
                itemService.updateOne(
                        toInpModel(update)
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
            @RequestBody @Valid CommentInpDto creation,
            @RequestHeader(CustomHeaders.USER_ID) @Positive Long userId
    ) {
        creation.setItemId(itemId);
        creation.setAuthorId(userId);

        return commentOutMapper.toDto(
                commentService.createOne(
                        commentInpMapper.toModel(
                                creation
                        )
                )
        );
    }
}
