package ru.practicum.shareit.features.item.mapper.item;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.features.item.dto.item.ItemDto;
import ru.practicum.shareit.features.item.model.Item;

@Service
public class ItemMapper extends BaseItemMapper<ItemDto> {
    public ItemMapper(ItemRelationsProvider relationsProvider) {
        super(relationsProvider);
    }

    @Override
    public ItemDto toDto(Item item) {
        ItemDto dto = new ItemDto(
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                getEntityId(item.getRequest())
        );
        dto.setId(item.getId());
        dto.setOwnerId(item.getOwner().getId());

        return dto;
    }
}
