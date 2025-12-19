package ru.practicum.shareit.features.item.mapper.item;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
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

@Service
@RequiredArgsConstructor
public class ItemRelationsProvider extends ModelProvider {
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;

    @Named("getUser")
    public User getUser(Long id) {
        return getModel(userRepository, id);
    }

    @Named("getPreviousBookingTime")
    public LocalDateTime getPreviousBookingTime(Long itemId) {
        return bookingRepository.findByItemOwnerIdAndStatusAndEndBeforeOrderByEndDesc(
                        itemId,
                        BookingStatus.APPROVED,
                        LocalDateTime.now()
                )
                .map(Booking::getEnd)
                .orElse(null);
    }

    @Named("getNextBookingTime")
    public LocalDateTime getNextBookingTime(Long itemId) {
        return bookingRepository.findByItemOwnerIdAndStatusAndStartAfterOrderByStartAsc(
                        itemId,
                        BookingStatus.APPROVED,
                        LocalDateTime.now()
                )
                .map(Booking::getStart)
                .orElse(null);
    }

    @Named("getItemComments")
    public List<Comment> getItemComments(Long itemId) {
        return commentRepository.findByItemId(itemId);
    }
}
