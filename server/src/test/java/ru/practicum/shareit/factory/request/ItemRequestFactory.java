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
        return ItemRequest.builder()
                .id(getValueOrDefault(attributes != null ? attributes.getId() : null, makeId()))
                .description(getValueOrDefault(attributes != null ? attributes.getDescription() : null, makeUniqueWord()))
                .requester(getValueOrDefault(attributes != null ? attributes.getRequester() : null, factories.user().make()))
                .created(getValueOrDefault(attributes != null ? attributes.getCreated() : null, LocalDate.now()))
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
