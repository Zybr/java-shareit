package ru.practicum.shareit.factory.item;


import ru.practicum.shareit.database.providers.FactoryProvider;
import ru.practicum.shareit.factory.ModelFactory;
import ru.practicum.shareit.features.item.model.Item;


public class ItemFactory extends ModelFactory<Item> {
    private final FactoryProvider factories;

    public ItemFactory(FactoryProvider factories) {
        super(factories.repositories().item());
        this.factories = factories;
    }

    @Override
    public Item make(Item attributes) {
        return Item.builder()
                .id(getValueOrDefault(attributes != null ? attributes.getId() : null, makeId()))
                .name(getValueOrDefault(attributes != null ? attributes.getName() : null, makeUniqueWord()))
                .description(getValueOrDefault(attributes != null ? attributes.getDescription() : null, makeUniqueWord()))
                .available(getValueOrDefault(attributes != null ? attributes.getAvailable() : null, true))
                .owner(getValueOrDefault(attributes != null ? attributes.getOwner() : null, factories.user().make()))
                .build();
    }

    @Override
    public Item create(Item attributes) {
        attributes = attributes != null ? attributes : new Item();

        Item model = make(attributes);
        model.setOwner(getValueOrDefault(attributes.getOwner(), factories.user().create()));

        return repository.saveAndFlush(model);
    }
}
