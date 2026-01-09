package ru.practicum.shareit.features.item.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.common.exception.InvalidDataException;
import ru.practicum.shareit.database.DbTestCase;
import ru.practicum.shareit.features.booking.model.Booking;
import ru.practicum.shareit.features.booking.repository.BookingRepository;
import ru.practicum.shareit.features.item.model.Comment;
import ru.practicum.shareit.features.item.model.Item;
import ru.practicum.shareit.features.item.repository.CommentRepository;
import ru.practicum.shareit.features.user.model.User;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest extends DbTestCase {
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private CommentService commentService;

    private Comment comment;

    @BeforeEach
    void setUp() {
        User author = factories().user().make(
                User.builder()
                        .id(1L)
                        .build()
        );
        Item item = factories().item().make(
                Item.builder()
                        .id(1L)
                        .build()
        );
        comment = factories().comment().make(
                Comment.builder()
                        .text("text")
                        .author(author)
                        .item(item)
                        .build()
        );
    }

    @Test
    void shouldCreateCommentWhenUserHasFinishedBooking() {
        when(bookingRepository.findAllByBookerIdAndStatusAndEndBefore(anyLong(), any(), any()))
                .thenReturn(List.of(Booking.builder().build()));
        when(commentRepository.saveAndFlush(any())).thenReturn(comment);

        Comment created = commentService.createOne(comment);

        assertNotNull(created);
    }

    @Test
    void shouldFailToCreateCommentWhenUserHasNoFinishedBooking() {
        when(bookingRepository.findAllByBookerIdAndStatusAndEndBefore(anyLong(), any(), any()))
                .thenReturn(Collections.emptyList());

        assertThrows(InvalidDataException.class, () -> commentService.createOne(comment));
    }
}
