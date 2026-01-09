package ru.practicum.shareit.features.booking.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.common.exception.InvalidDataException;
import ru.practicum.shareit.common.exception.NotFoundException;
import ru.practicum.shareit.database.DbTestCase;
import ru.practicum.shareit.features.booking.model.Booking;
import ru.practicum.shareit.features.booking.model.BookingState;
import ru.practicum.shareit.features.booking.model.BookingStatus;
import ru.practicum.shareit.features.booking.repository.BookingRepository;
import ru.practicum.shareit.features.item.model.Item;
import ru.practicum.shareit.features.item.repository.ItemRepository;
import ru.practicum.shareit.features.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest extends DbTestCase {
    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private ItemRepository itemRepository;
    @InjectMocks
    private BookingService bookingService;

    private Item item;
    private Booking booking;

    @BeforeEach
    void setUp() {
        User booker = factories().user().make(
                User.builder()
                        .id(1L)
                        .build()
        );
        User owner = factories().user().make(
                User.builder()
                        .id(2L)
                        .build()
        );
        item = factories().item().make(
                Item.builder()
                        .id(1L)
                        .owner(owner)
                        .build()
        );
        booking = factories().booking().make(
                Booking.builder()
                        .id(1L)
                        .item(item)
                        .booker(booker)
                        .build()
        );
    }

    @Test
    void shouldGetOneBookingByUser() {
        when(bookingRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(booking));

        Booking found = bookingService.getOneByUser(1L, 1L);

        assertEquals(booking, found);
    }

    @Test
    void shouldThrowNotFoundWhenBookingDoesNotExistForUser() {
        when(bookingRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookingService.getOneByUser(1L, 1L));
    }

    @Test
    void shouldFindAllBookingsByBooker() {
        when(bookingRepository.findAllByBookerId(1L)).thenReturn(List.of(booking));

        List<Booking> list = bookingService.findAllByBooker(1L, BookingState.ALL);

        assertEquals(1, list.size());
    }

    @Test
    void shouldFindAllBookingsByBookerForVariousStates() {
        when(bookingRepository.findAllByBookerId(anyLong())).thenReturn(List.of(booking));
        when(bookingRepository.findAllByBookerIdAndEndBefore(anyLong(), any())).thenReturn(List.of(booking));
        when(bookingRepository.findAllByBookerIdAndStartBeforeAndEndAfter(anyLong(), any(), any())).thenReturn(List.of(booking));
        when(bookingRepository.findAllByBookerIdAndStartAfter(anyLong(), any())).thenReturn(List.of(booking));
        when(bookingRepository.findAllByBookerIdAndStatus(anyLong(), any())).thenReturn(List.of(booking));

        assertNotNull(bookingService.findAllByBooker(1L, BookingState.ALL));
        assertNotNull(bookingService.findAllByBooker(1L, BookingState.PAST));
        assertNotNull(bookingService.findAllByBooker(1L, BookingState.CURRENT));
        assertNotNull(bookingService.findAllByBooker(1L, BookingState.FUTURE));
        assertNotNull(bookingService.findAllByBooker(1L, BookingState.REJECTED));
        assertNotNull(bookingService.findAllByBooker(1L, null));
    }

    @Test
    void shouldFindAllBookingsByItemOwner() {
        when(bookingRepository.findAllByItemOwnerId(2L)).thenReturn(List.of(booking));

        List<Booking> list = bookingService.findAllByItemOwner(2L, BookingState.ALL);

        assertEquals(1, list.size());
    }

    @Test
    void shouldFindAllBookingsByItemOwnerForVariousStates() {
        when(bookingRepository.findAllByItemOwnerId(anyLong())).thenReturn(List.of(booking));
        when(bookingRepository.findAllByItemOwnerIdAndEndBefore(anyLong(), any())).thenReturn(List.of(booking));
        when(bookingRepository.findAllByItemOwnerIdAndStartBeforeAndEndAfter(anyLong(), any(), any())).thenReturn(List.of(booking));
        when(bookingRepository.findAllByItemOwnerIdAndStartAfter(anyLong(), any())).thenReturn(List.of(booking));
        when(bookingRepository.findAllByItemOwnerIdAndStatus(anyLong(), any())).thenReturn(List.of(booking));

        assertNotNull(bookingService.findAllByItemOwner(2L, BookingState.ALL));
        assertNotNull(bookingService.findAllByItemOwner(2L, BookingState.PAST));
        assertNotNull(bookingService.findAllByItemOwner(2L, BookingState.CURRENT));
        assertNotNull(bookingService.findAllByItemOwner(2L, BookingState.FUTURE));
        assertNotNull(bookingService.findAllByItemOwner(2L, BookingState.REJECTED));
        assertNotNull(bookingService.findAllByItemOwner(2L, null));
    }

    @Test
    void shouldVerifyIfUserIsItemOwner() {
        assertTrue(bookingService.isOwner(booking, 2L));
        assertFalse(bookingService.isOwner(booking, 1L));
    }

    @Test
    void shouldCreateBooking() {
        when(itemRepository.findOneAvailableById(1L)).thenReturn(Optional.of(item));
        when(bookingRepository.saveAndFlush(any())).thenReturn(booking);

        Booking created = bookingService.createOne(booking);

        assertNotNull(created);
    }

    @Test
    void shouldFailToCreateBookingWhenPeriodIsInvalid() {
        booking.setStart(LocalDateTime.now().plusDays(2));
        booking.setEnd(LocalDateTime.now().plusDays(1));

        assertThrows(InvalidDataException.class, () -> bookingService.createOne(booking));
    }

    @Test
    void shouldFailToCreateBookingWhenItemIsNotAvailable() {
        when(itemRepository.findOneAvailableById(1L)).thenReturn(Optional.empty());

        assertThrows(InvalidDataException.class, () -> bookingService.createOne(booking));
    }

    @Test
    void shouldUpdateBookingStatus() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        when(itemRepository.findOneAvailableById(1L)).thenReturn(Optional.of(item));
        when(bookingRepository.saveAndFlush(any())).thenReturn(booking);
        Booking update = Booking.builder()
                .id(1L)
                .status(BookingStatus.APPROVED)
                .item(item)
                .start(booking.getStart())
                .end(booking.getEnd())
                .build();

        Booking updated = bookingService.updateOne(update);

        assertNotNull(updated);
    }
}
