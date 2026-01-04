package ru.practicum.shareit.factory.request;

import ru.practicum.shareit.database.providers.FactoryProvider;
import ru.practicum.shareit.factory.ModelFactory;
import ru.practicum.shareit.features.request.model.ItemRequest;

import java.time.LocalDate;

public class ItemRequestFactory extends ModelFactory<ItemRequest> {
    private final FactoryProvider factories;

    public ItemRequestFactory(FactoryProvider factories) {
        super(factories.repositories().itemRequest());
        this.factories = factories;
    }

    @Override
    public ItemRequest make(ItemRequest attributes) {
        attributes = attributes != null ? attributes : new ItemRequest();

        return ItemRequest.builder()
                .id(getValueOrDefault(attributes.getId(), makeId()))
                .description(getValueOrDefault(attributes.getDescription(), makeUniqueWord()))
                .requester(getValueOrDefault(attributes.getRequester(), factories.user().make()))
                .created(getValueOrDefault(attributes.getCreated(), LocalDate.now()))
                .build();
    }

    @Override
    public ItemRequest create(ItemRequest attributes) {
        attributes = attributes != null ? attributes : new ItemRequest();

        ItemRequest model = make(attributes);
        model.setRequester(getValueOrDefault(attributes.getRequester(), factories.user().create()));

        return repository.saveAndFlush(model);
    }
}
