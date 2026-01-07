package ru.practicum.shareit.factory.item;

import ru.practicum.shareit.factory.DataFactory;
import ru.practicum.shareit.features.item.dto.item.ItemDto;

public class ItemDtoFactory extends DataFactory<ItemDto> {
    @Override
    public ItemDto make(ItemDto attributes) {
        attributes = attributes != null ? attributes : new ItemDto(null, null, null, null);

        ItemDto dto = new ItemDto(
                getValueOrDefault(attributes.getName(), makeUniqueWord()),
                getValueOrDefault(attributes.getDescription(), makeUniqueWord()),
                getValueOrDefault(attributes.getAvailable(),true),
                attributes.getRequestId()
        );
        dto.setId(getValueOrDefault(attributes.getId(), makeId()));
        dto.setOwnerId(getValueOrDefault(attributes.getOwnerId(), makeId()));

        return dto;
    }
}
