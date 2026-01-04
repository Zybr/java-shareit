package ru.practicum.shareit.factory.request;

import ru.practicum.shareit.factory.DataFactory;
import ru.practicum.shareit.features.request.dto.ItemRequestCreationDto;

public class ItemRequestCreationDtoFactory extends DataFactory<ItemRequestCreationDto> {
    @Override
    public ItemRequestCreationDto make(ItemRequestCreationDto attributes) {
        attributes = attributes != null ? attributes : new ItemRequestCreationDto();

        return (new ItemRequestCreationDto())
                .setDescription(getValueOrDefault(attributes.getDescription(), makeUniqueWord()))
                .setRequesterId(getValueOrDefault(attributes.getRequesterId(), makeId()));
    }
}
