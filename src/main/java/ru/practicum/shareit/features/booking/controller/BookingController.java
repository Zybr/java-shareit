package ru.practicum.shareit.features.booking.controller;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.common.constants.CustomHeaders;
import ru.practicum.shareit.common.controller.ModelController;
import ru.practicum.shareit.common.exception.AccessException;
import ru.practicum.shareit.common.validation.action.OnCreate;
import ru.practicum.shareit.features.booking.dto.BookingInpDto;
import ru.practicum.shareit.features.booking.dto.BookingOutDto;
import ru.practicum.shareit.features.booking.mapper.BookingInpMapper;
import ru.practicum.shareit.features.booking.mapper.BookingOutMapper;
import ru.practicum.shareit.features.booking.model.Booking;
import ru.practicum.shareit.features.booking.model.BookingState;
import ru.practicum.shareit.features.booking.model.BookingStatus;
import ru.practicum.shareit.features.booking.service.BookingService;

import java.util.List;

@RestController
@RequestMapping(path = "/bookings")
public class BookingController extends ModelController<Booking, BookingInpDto, BookingOutDto> {
    private final BookingService service;

    public BookingController(
            BookingInpMapper inpMapper,
            BookingOutMapper outMapper,
            BookingService service
    ) {
        super(inpMapper, outMapper);
        this.service = service;
    }

    @GetMapping("{id}")
    public BookingOutDto getUserBooking(
            @PathVariable("id") @Positive Long bookingId,
            @RequestHeader(CustomHeaders.USER_ID) @Positive Long userId
    ) {
        return toOutDto(
                service.getOneByUser(
                        bookingId,
                        userId
                )
        );
    }

    @GetMapping()
    public List<BookingOutDto> getBookerBookings(
            @RequestHeader(CustomHeaders.USER_ID) @Positive Long bookerId,
            @RequestParam("state") @Nullable BookingState state
    ) {
        return toOutDto(
                service.findAllByBooker(
                        bookerId,
                        state
                )
        );
    }

    @GetMapping("owner")
    public List<BookingOutDto> getItemOwnerBookings(
            @RequestHeader(CustomHeaders.USER_ID) @Positive Long ownerId,
            @RequestParam("state") @Nullable BookingState state
    ) {
        service.getOne(ownerId); // Assert existing

        return toOutDto(
                service.findAllByItemOwner(
                        ownerId,
                        state
                )
        );
    }

    @PostMapping
    public BookingOutDto createBooking(
            @RequestBody @Validated(OnCreate.class) BookingInpDto creation,
            @RequestHeader(CustomHeaders.USER_ID) @Positive Long bookerId
    ) {
        creation.setBookerId(bookerId);

        return toOutDto(
                service.createOne(
                        toInpModel(creation)
                )
        );
    }

    @PatchMapping("{id}")
    public BookingOutDto confirmBooking(
            @PathVariable("id") @Positive Long id,
            @RequestHeader(CustomHeaders.USER_ID) @Positive Long ownerId,
            @RequestParam("approved") Boolean isApproved
    ) {
        Booking booking = service.getOne(id);

        if (!service.isOwner(booking, ownerId)) {
            throw new AccessException("The user can't change requested booking.");
        }

        booking.setStatus(
                isApproved == true
                        ? BookingStatus.APPROVED
                        : BookingStatus.REJECTED
        );

        return toOutDto(
                service.updateOne(
                        booking
                )
        );
    }
}
