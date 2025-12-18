package ru.practicum.shareit.features.item.mapper.item;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.common.mapper.ModelMapper;
import ru.practicum.shareit.features.item.dto.item.ItemDto;
import ru.practicum.shareit.features.item.model.Item;

@RequiredArgsConstructor
public abstract class BaseItemMapper<D extends ItemDto> extends ModelMapper<Item, D> {
    protected final ItemRelationsProvider relationsProvider;

    @Override
    public abstract D toDto(Item item);

    @Override
    public Item toModel(ItemDto dto) {
        return Item.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .available(dto.getAvailable())
                .owner(relationsProvider.getUser(dto.getOwnerId()))
//                .request() // TODO: Implement in one of the following sprint
                .build();
    }
}
