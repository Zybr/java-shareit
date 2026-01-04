package ru.practicum.shareit.features.booking.service;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.common.exception.InvalidDataException;
import ru.practicum.shareit.common.exception.NotFoundException;
import ru.practicum.shareit.common.service.BaseModelService;
import ru.practicum.shareit.features.booking.model.Booking;
import ru.practicum.shareit.features.booking.model.BookingState;
import ru.practicum.shareit.features.booking.model.BookingStatus;
import ru.practicum.shareit.features.booking.repository.BookingRepository;
import ru.practicum.shareit.features.item.repository.ItemRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService extends BaseModelService<Booking> {
    protected final BookingRepository repository;
    private final ItemRepository itemRepository;

    public BookingService(
            BookingRepository repository,
            ItemRepository itemRepository
    ) {
        super(repository);
        this.repository = repository;
        this.itemRepository = itemRepository;
    }

    public Booking getOneByUser(Long bookingId, Long userId) {
        return repository
                .findByIdAndUserId(bookingId, userId)
                .orElseThrow(
                        () -> new NotFoundException(
                                String.format(
                                        "There is no booking \"%d\" related to user \"%d\"",
                                        bookingId,
                                        userId
                                )
                        )
                );
    }

    public List<Booking> findAllByBooker(Long bookerId, BookingState state) {
        LocalDateTime now = LocalDateTime.now();
        return switch (state) {
            case null -> repository.findAllByBookerId(bookerId);
            case ALL -> repository.findAllByBookerId(bookerId);
            case PAST -> repository.findAllByBookerIdAndEndBefore(bookerId, now);
            case CURRENT -> repository.findAllByBookerIdAndStartBeforeAndEndAfter(bookerId, now, now);
            case FUTURE -> repository.findAllByBookerIdAndStartAfter(bookerId, now);
            case REJECTED -> repository.findAllByBookerIdAndStatus(bookerId, BookingStatus.REJECTED);
        };
    }

    public List<Booking> findAllByItemOwner(Long ownerId, BookingState state) {
        LocalDateTime now = LocalDateTime.now();
        return switch (state) {
            case null -> repository.findAllByItemOwnerId(ownerId);
            case ALL -> repository.findAllByItemOwnerId(ownerId);
            case PAST -> repository.findAllByItemOwnerIdAndEndBefore(ownerId, now);
            case CURRENT -> repository.findAllByItemOwnerIdAndStartBeforeAndEndAfter(ownerId, now, now);
            case FUTURE -> repository.findAllByItemOwnerIdAndStartAfter(ownerId, now);
            case REJECTED -> repository.findAllByItemOwnerIdAndStatus(ownerId, BookingStatus.REJECTED);
        };
    }

    public boolean isOwner(
            Booking booking,
            Long ownerId
    ) {
        return booking
                .getItem()
                .getOwner()
                .getId()
                .equals(ownerId);
    }

    @Override
    protected Booking fill(Booking source, Booking target) {
        target.setStart(getValueOrDefault(
                source.getStart(),
                target.getStart()
        ));
        target.setEnd(getValueOrDefault(
                source.getEnd(),
                target.getEnd()
        ));
        target.setStatus(getValueOrDefault(
                source.getStatus(),
                target.getStatus()
        ));
        target.setItem(getValueOrDefault(
                source.getItem(),
                target.getItem()
        ));
        target.setBooker(getValueOrDefault(
                source.getBooker(),
                target.getBooker()
        ));

        return target;
    }

    @Override
    protected void validate(Booking model, Action action) throws RuntimeException {
        assertValidPeriod(
                model.getStart(),
                model.getEnd()
        );
        assertValidItemId(
                model.getItem().getId()
        );
    }

    private void assertValidPeriod(
            LocalDateTime start,
            LocalDateTime end
    ) {
        if (!start.isBefore(end)) {
            throw new InvalidDataException("Invalid period of time. Start time must be before end time.");
        }
    }

    private void assertValidItemId(
            Long id
    ) {
        itemRepository
                .findOneAvailableById(id)
                .orElseThrow(() -> new InvalidDataException(String.format(
                        "There is not available item \"%d\"",
                        id
                )));
    }
}
