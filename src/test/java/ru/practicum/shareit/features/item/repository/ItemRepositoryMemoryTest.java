package ru.practicum.shareit.features.item.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.common.repository.ModelRepositoryTest;
import ru.practicum.shareit.factory.ItemFactory;
import ru.practicum.shareit.factory.UserFactory;
import ru.practicum.shareit.features.item.model.Item;
import ru.practicum.shareit.features.user.model.User;
import ru.practicum.shareit.features.user.repository.UserRepository;

import java.util.List;

@SpringBootTest()
public class ItemRepositoryMemoryTest extends ModelRepositoryTest<ItemRepositoryMemory, Item> {
    @Override
    public ItemFactory getFactory() {
        return (ItemFactory) super.getFactory();
    }


    public ItemRepositoryMemoryTest(
            @Autowired ItemRepositoryMemory repository,
            @Autowired UserRepository userRepository
    ) {
        super(
                repository,
                new ItemFactory(
                        repository,
                        new UserFactory(userRepository)
                )
        );
    }

    /**
     * @see ItemRepository#findListByOwner(Long)
     */
    @Test
    public void shouldFinByOwner() {
        List<Item> items = getFactory().createList(5);
        User owner = getFactory().getUserFactory().create();
        Item itemTemplate = Item.builder().owner(owner).build();
        List<Item> ownerItems = List.of(
                getFactory().create(itemTemplate),
                getFactory().create(itemTemplate),
                getFactory().create(itemTemplate)
        );
        items.addAll(ownerItems);

        Assertions.assertEquals(
                getRepository()
                        .findListByOwner(owner.getId())
                        .stream()
                        .map(Item::getId)
                        .toList(),
                ownerItems
                        .stream()
                        .map(Item::getId)
                        .toList()
        );
    }

    /**
     * @see ItemRepository#findListByOwner(Long, String)
     */
    @Test
    public void shouldFinByOwnerAndSearchText() {
        List<Item> items = getFactory().createList(5);
        User owner = getFactory().getUserFactory().create();
        String searchText = "target";
        Item itemTemplate = Item.builder().owner(owner).build();
        Item targetItemTemplate = getFactory()
                .create(
                        Item.builder()
                                .owner(owner)
                                .name(searchText.toUpperCase())
                                .build()
                );
        List<Item> ownerItems = List.of(
                getFactory().create(itemTemplate),
                getFactory().create(itemTemplate),
                getFactory().create(itemTemplate)
        );
        items.addAll(ownerItems);

        Assertions.assertEquals(
                getRepository()
                        .findListByOwner(
                                owner.getId(),
                                searchText
                        )
                        .stream()
                        .map(Item::getId)
                        .toList(),
                List.of(targetItemTemplate.getId())
        );
    }
}
