package ru.practicum.shareit.features.item.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.database.DbTestCase;
import ru.practicum.shareit.features.item.model.Item;
import ru.practicum.shareit.features.item.repository.ItemRepository;
import ru.practicum.shareit.features.user.model.User;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest extends DbTestCase {
    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;
    private Item item;

    @BeforeEach
    void setUp() {
        User owner = factories().user().make(
                User.builder()
                        .id(1L)
                        .build()
        );
        item = factories().item().make(
                Item.builder()
                        .id(1L)
                        .owner(owner)
                        .build()
        );
    }

    @Test
    void shouldFindItemsByOwnerId() {
        when(itemRepository.findAllByOwnerId(1L)).thenReturn(List.of(item));

        List<Item> items = itemService.findListByOwner(1L);

        assertEquals(1, items.size());
    }

    @Test
    void shouldFindItemsByOwnerIdAndSearchText() {
        when(itemRepository.findAllByOwnerIdAndSearchText(1L, "text")).thenReturn(List.of(item));

        List<Item> items = itemService.findListByOwner(1L, "text");

        assertEquals(1, items.size());
    }

    @Test
    void shouldCreateItem() {
        when(itemRepository.saveAndFlush(any())).thenReturn(item);

        Item created = itemService.createOne(item);

        assertNotNull(created);
    }

    @Test
    void shouldUpdateItem() {
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(itemRepository.saveAndFlush(any())).thenReturn(item);

        Item update = Item.builder().id(1L).name("Updated").build();
        Item updated = itemService.updateOne(update);

        assertEquals("Updated", updated.getName());
    }
}
