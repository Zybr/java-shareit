package ru.practicum.shareit.features.item.controller;

import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.common.controller.ModelController;
import ru.practicum.shareit.common.validation.action.OnCreate;
import ru.practicum.shareit.common.validation.action.OnPartialUpdate;
import ru.practicum.shareit.features.item.dto.ItemDto;
import ru.practicum.shareit.features.item.mapper.ItemMapper;
import ru.practicum.shareit.features.item.model.Item;
import ru.practicum.shareit.features.item.service.ItemService;

import java.util.List;

@RestController
@RequestMapping(path = "/items")
@Validated
public class ItemController extends ModelController<Item, ItemDto> {
    private final ItemService service;

    public ItemController(
            ItemMapper mapper,
            ItemService service
    ) {
        super(mapper);
        this.service = service;
    }

    @GetMapping
    public List<ItemDto> getItems(
            @RequestHeader("X-Sharer-User-Id") @Positive Long userId
    ) {
        return toDto(
                service.findListByOwner(userId)
        );
    }

    @GetMapping("/search")
    public List<ItemDto> searchItems(
            @RequestHeader("X-Sharer-User-Id") @Positive Long userId,
            @RequestParam("text") String searchText
    ) {
        return toDto(
                service.findListByOwner(
                        userId,
                        searchText
                )
        );
    }

    @GetMapping("{id}")
    public ItemDto getItem(
            @PathVariable Long id
    ) {
        return toDto(
                service.getOne(id)
        );
    }

    @PostMapping
    public ItemDto createItem(
            @RequestBody @Validated(OnCreate.class) ItemDto creation,
            @RequestHeader("X-Sharer-User-Id") @Positive Long userId
    ) {
        creation.setOwner(userId);

        return toDto(
                service.createOne(
                        toModel(creation)
                )
        );
    }

    @PatchMapping("{id}")
    public ItemDto updateItem(
            @PathVariable Long id,
            @RequestBody @Validated(OnPartialUpdate.class) ItemDto update,
            @RequestHeader("X-Sharer-User-Id") @Positive Long userId
    ) {
        update.setId(id);
        update.setOwner(userId);

        return toDto(
                service.updateOne(
                        toModel(update)
                )
        );
    }

    @DeleteMapping("{id}")
    public void removeItem(
            @PathVariable Long id
    ) {
        service.deleteOne(id);
    }
}
