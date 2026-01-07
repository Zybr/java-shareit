package ru.practicum.shareit.factory.request;

import ru.practicum.shareit.factory.DataFactory;
import ru.practicum.shareit.features.request.dto.ItemRequestOutDto;

import java.time.LocalDate;
import java.util.ArrayList;

public class ItemRequestOutDtoFactory extends DataFactory<ItemRequestOutDto> {
    @Override
    public ItemRequestOutDto make(ItemRequestOutDto attributes) {
        attributes = attributes != null ? attributes : new ItemRequestOutDto(null, null, null, null, null);

        return new ItemRequestOutDto(
                getValueOrDefault(attributes.getId(), makeId()),
                getValueOrDefault(attributes.getDescription(), makeUniqueWord()),
                getValueOrDefault(attributes.getRequester(), makeId()),
                getValueOrDefault(attributes.getCreated(), LocalDate.now()),
                getValueOrDefault(attributes.getItems(), new ArrayList<>())
        );
    }
}
