package ru.practicum.shareit.features.item.service;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.common.exception.InvalidDataException;
import ru.practicum.shareit.common.service.BaseModelService;
import ru.practicum.shareit.features.booking.model.BookingStatus;
import ru.practicum.shareit.features.booking.repository.BookingRepository;
import ru.practicum.shareit.features.item.model.Comment;
import ru.practicum.shareit.features.item.repository.CommentRepository;

import java.time.LocalDateTime;

@Service
public class CommentService extends BaseModelService<Comment> {
    private final BookingRepository bookingRepository;

    public CommentService(
            CommentRepository repository,
            BookingRepository bookingRepository
    ) {
        super(repository);
        this.bookingRepository = bookingRepository;
    }

    @Override
    protected Comment fill(Comment source, Comment target) {
        target.setCreated(getValueOrDefault(
                source.getCreated(),
                target.getCreated()
        ));
        target.setItem(getValueOrDefault(
                source.getItem(),
                target.getItem()
        ));
        target.setAuthor(getValueOrDefault(
                source.getAuthor(),
                target.getAuthor()
        ));

        return target;
    }

    @Override
    protected void validate(Comment model, Action action) {
        assertAuthorHasBooked(model);
    }

    private void assertAuthorHasBooked(Comment model) {
        boolean hasAuthorBooked = !bookingRepository
                .findAllByBookerIdAndStatusAndEndBefore(
                        model.getAuthor().getId(),
                        BookingStatus.APPROVED,
                        LocalDateTime.now()
                ).isEmpty();

        if (!hasAuthorBooked) {
            throw new InvalidDataException(String.format(
                    "User %d has never booked the item %d",
                    model.getAuthor().getId(),
                    model.getItem().getId()
            ));
        }
    }
}
