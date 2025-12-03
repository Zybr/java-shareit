package ru.practicum.shareit.factory;


import lombok.Getter;
import ru.practicum.shareit.common.repository.ModelRepository;
import ru.practicum.shareit.features.item.model.Item;


public class ItemFactory extends Factory<Item> {
    protected final ModelRepository<Item> repository;
    @Getter
    private final UserFactory userFactory;

    public ItemFactory(
            ModelRepository<Item> repository,
            UserFactory userFactory
    ) {
        super(repository);
        this.repository = repository;
        this.userFactory = userFactory;
    }

    public Item make(Item attributes) {
        return Item
                .builder()
                .id(getValueOrDefault(
                        attributes != null ? attributes.getId() : null,
                        makeId()
                ))
                .name(getValueOrDefault(
                        attributes != null ? attributes.getName() : null,
                        makeUniqueWord()
                ))
                .description(getValueOrDefault(
                        attributes != null ? attributes.getDescription() : null,
                        makeUniqueWord()
                ))
                .available(getValueOrDefault(
                        attributes != null ? attributes.getAvailable() : null,
                        true
                ))
                .owner(getValueOrDefault(
                        attributes != null ? attributes.getOwner() : null,
                        userFactory.make()
                ))
                .build();
    }

    public Item create() {
        Item item = make();
        item.setOwner(userFactory.create());

        return repository.createOne(make());
    }
}
