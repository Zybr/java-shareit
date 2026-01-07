package ru.practicum.shareit.factory.item;

import ru.practicum.shareit.factory.DataFactory;
import ru.practicum.shareit.features.item.dto.item.ItemDetailedOutDto;

import java.util.ArrayList;

public class ItemDetailedOutDtoFactory extends DataFactory<ItemDetailedOutDto> {
    @Override
    public ItemDetailedOutDto make(ItemDetailedOutDto attributes) {
        attributes = attributes != null ? attributes : new ItemDetailedOutDto(null, null, null, null, null, null);

        ItemDetailedOutDto dto = new ItemDetailedOutDto(
                getValueOrDefault(attributes.getName(), makeUniqueWord()),
                getValueOrDefault(attributes.getDescription(), makeUniqueWord()),
                getValueOrDefault(attributes.getAvailable(), true),
                attributes.getRequestId(),
                attributes.getLastBooking(),
                attributes.getNextBooking()
        );
        dto.setId(getValueOrDefault(attributes.getId(), makeId()));
        dto.setOwnerId(getValueOrDefault(attributes.getOwnerId(), makeId()));
        dto.setComments(getValueOrDefault(attributes.getComments(), new ArrayList<>()));

        return dto;
    }
}
