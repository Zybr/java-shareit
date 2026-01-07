package ru.practicum.shareit.features.request.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.database.DbTestCase;
import ru.practicum.shareit.features.request.model.ItemRequest;
import ru.practicum.shareit.features.request.repository.ItemRequestRepository;
import ru.practicum.shareit.features.user.model.User;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemRequestServiceTest extends DbTestCase {
    @Mock
    private ItemRequestRepository itemRequestRepository;

    @InjectMocks
    private ItemRequestService itemRequestService;

    private ItemRequest itemRequest;
    private User requester;

    @BeforeEach
    void setUp() {
        requester = factories().user().make(
                User.builder()
                        .id(1L)
                        .build()
        );
        itemRequest = factories().itemRequest().make(
                ItemRequest.builder()
                        .id(1L)
                        .requester(requester)
                        .build()
        );
    }

    @Test
    void shouldFindAllRequestsByRequesterId() {
        when(itemRequestRepository.findAllByRequesterId(1L)).thenReturn(List.of(itemRequest));

        List<ItemRequest> requests = itemRequestService.findAllByRequesterId(1L);

        assertEquals(1, requests.size());
    }

    @Test
    void shouldCreateItemRequest() {
        when(itemRequestRepository.saveAndFlush(any())).thenReturn(itemRequest);

        ItemRequest created = itemRequestService.createOne(itemRequest);

        assertNotNull(created);
    }

    @Test
    void shouldUpdateItemRequest() {
        when(itemRequestRepository.findById(1L)).thenReturn(Optional.of(itemRequest));
        when(itemRequestRepository.saveAndFlush(any())).thenReturn(itemRequest);

        ItemRequest update = ItemRequest.builder().id(1L).description("Updated").build();
        ItemRequest updated = itemRequestService.updateOne(update);

        assertEquals("Updated", updated.getDescription());
    }
}
