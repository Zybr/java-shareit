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
        attributes = attributes != null ? attributes : new Item();
        return Item.builder()
                .id(getValueOrDefault(attributes.getId(), makeId()))
                .name(getValueOrDefault(attributes.getName(), makeUniqueWord()))
                .description(getValueOrDefault(attributes.getDescription(), makeUniqueWord()))
                .available(getValueOrDefault(attributes.getAvailable(), true))
                .owner(getValueOrDefault(attributes.getOwner(), factories.user().make()))
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
