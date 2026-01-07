package ru.practicum.shareit.features.item.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.practicum.shareit.common.repository.ModelRepositoryTestCase;
import ru.practicum.shareit.factory.item.ItemFactory;
import ru.practicum.shareit.features.item.model.Item;
import ru.practicum.shareit.features.user.model.User;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class ItemRepositoryTest extends ModelRepositoryTestCase<ItemRepository, Item> {
    @Override
    protected ItemRepository repository() {
        return factories().repositories().item();
    }

    @Override
    protected ItemFactory factory() {
        return factories().item();
    }

    /**
     * @see ItemRepository#findAllByOwnerId(Long)
     */
    @Test
    public void shouldFinByOwner() {
        List<Item> items = factory().createList(5);
        User owner = factories().user().create();
        Item itemTemplate = Item.builder().owner(owner).build();
        List<Item> ownerItems = List.of(
                factory().create(itemTemplate),
                factory().create(itemTemplate),
                factory().create(itemTemplate)
        );
        items.addAll(ownerItems);

        Assertions.assertEquals(
                repository()
                        .findAllByOwnerId(owner.getId())
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
     * @see ItemRepository#findAllByOwnerIdAndSearchText(Long, String)
     */
    @Test
    public void shouldFinByOwnerAndSearchText() {
        List<Item> items = factory().createList(5);
        User owner = factories().user().create();
        String searchText = "target";
        Item itemTemplate = Item.builder().owner(owner).build();
        Item targetItemTemplate = factory()
                .create(
                        Item.builder()
                                .owner(owner)
                                .name(searchText.toUpperCase())
                                .build()
                );
        List<Item> ownerItems = List.of(
                factory().create(itemTemplate),
                factory().create(itemTemplate),
                factory().create(itemTemplate)
        );
        items.addAll(ownerItems);

        Assertions.assertEquals(
                repository()
                        .findAllByOwnerIdAndSearchText(
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
