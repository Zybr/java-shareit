package ru.practicum.shareit.features.booking.controller;

import jakarta.annotation.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.common.constants.CustomHeaders;
import ru.practicum.shareit.common.exception.AccessException;
import ru.practicum.shareit.features.booking.dto.BookingInpDto;
import ru.practicum.shareit.features.booking.dto.BookingOutDto;
import ru.practicum.shareit.features.booking.mapper.BookingMapper;
import ru.practicum.shareit.features.booking.model.Booking;
import ru.practicum.shareit.features.booking.model.BookingState;
import ru.practicum.shareit.features.booking.model.BookingStatus;
import ru.practicum.shareit.features.booking.service.BookingService;
import ru.practicum.shareit.features.user.service.UserService;

import java.util.List;

@RestController
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingService service;
    private final UserService userService;
    private final BookingMapper mapper;

    public BookingController(
            BookingMapper mapper,
            BookingService service,
            UserService userService
    ) {
        this.mapper = mapper;
        this.service = service;
        this.userService = userService;
    }

    @GetMapping("{id}")
    public BookingOutDto getUserBooking(
            @PathVariable("id") Long bookingId,
            @RequestHeader(CustomHeaders.USER_ID) Long userId
    ) {
        return mapper.toOutDto(
                service.getOneByUser(
                        bookingId,
                        userId
                )
        );
    }

    @GetMapping()
    public List<BookingOutDto> getBookerBookings(
            @RequestHeader(CustomHeaders.USER_ID) Long bookerId,
            @RequestParam("state") @Nullable BookingState state
    ) {
        return mapper.toOutDto(
                service.findAllByBooker(
                        bookerId,
                        state
                )
        );
    }

    @GetMapping("owner")
    public List<BookingOutDto> getItemOwnerBookings(
            @RequestHeader(CustomHeaders.USER_ID) Long ownerId,
            @RequestParam("state") @Nullable BookingState state
    ) {
        userService.getOne(ownerId); // Assert existing

        return mapper.toOutDto(
                service.findAllByItemOwner(
                        ownerId,
                        state
                )
        );
    }

    @PostMapping
    public BookingOutDto createBooking(
            @RequestBody BookingInpDto creation,
            @RequestHeader(CustomHeaders.USER_ID) Long bookerId
    ) {
        creation.setBookerId(bookerId);

        return mapper.toOutDto(
                service.createOne(
                        mapper.toModel(creation)
                )
        );
    }

    @PatchMapping("{id}")
    public BookingOutDto confirmBooking(
            @PathVariable("id") Long id,
            @RequestHeader(CustomHeaders.USER_ID) Long ownerId,
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

        return mapper.toOutDto(
                service.updateOne(
                        booking
                )
        );
    }
}
