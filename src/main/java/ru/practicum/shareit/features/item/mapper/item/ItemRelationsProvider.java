package ru.practicum.shareit.features.item.mapper.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.common.mapper.ModelProvider;
import ru.practicum.shareit.features.booking.model.Booking;
import ru.practicum.shareit.features.booking.model.BookingStatus;
import ru.practicum.shareit.features.booking.repository.BookingRepository;
import ru.practicum.shareit.features.item.model.Comment;
import ru.practicum.shareit.features.item.repository.CommentRepository;
import ru.practicum.shareit.features.user.model.User;
import ru.practicum.shareit.features.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemRelationsProvider extends ModelProvider {
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;

    public User getUser(Long id) {
        return getModel(userRepository, id);
    }

    public Optional<Booking> getPreviousBooking(Long itemId) {
        return bookingRepository.findByItemOwnerIdAndStatusAndEndBeforeOrderByEndDesc(
                itemId,
                BookingStatus.APPROVED,
                LocalDateTime.now()
        );
    }

    public Optional<Booking> getNextBooking(Long itemId) {
        return bookingRepository.findByItemOwnerIdAndStatusAndStartAfterOrderByStartAsc(
                itemId,
                BookingStatus.APPROVED,
                LocalDateTime.now()
        );
    }

    public List<Comment> getItemComments(Long itemId) {
        return commentRepository.findByItemId(itemId);
    }
}
