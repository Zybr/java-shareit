package ru.practicum.shareit.features.booking.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.common.constant.CustomHeaders;
import ru.practicum.shareit.features.booking.client.BookingClient;
import ru.practicum.shareit.features.booking.dto.BookingState;
import ru.practicum.shareit.features.booking.dto.CreateBookingDto;


@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
@Validated
public class BookingController {
    private final BookingClient bookingClient;

    @GetMapping
    public ResponseEntity<Object> getBookings(
            @RequestHeader(CustomHeaders.USER_ID) @Positive Long userId,
            @RequestParam(name = "state", defaultValue = "all") String stateParam,
            @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
            @Positive @RequestParam(name = "size", defaultValue = "10") Integer size
    ) {
        return bookingClient.getBookings(
                userId,
                BookingState
                        .from(stateParam)
                        .orElseThrow(() -> new IllegalArgumentException("Unknown state: " + stateParam)),
                from,
                size
        );
    }

    @GetMapping("/owner")
    public ResponseEntity<Object> getItemOwnerBookings(
            @RequestHeader(CustomHeaders.USER_ID) @Positive Long userId,
            @RequestParam(name = "state", defaultValue = "all") String stateParam,
            @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
            @Positive @RequestParam(name = "size", defaultValue = "10") Integer size
    ) {
        return bookingClient.getOwnerBookings(
                userId,
                BookingState
                        .from(stateParam)
                        .orElseThrow(() -> new IllegalArgumentException("Unknown state: " + stateParam)),
                from,
                size
        );
    }

    @PostMapping
    public ResponseEntity<Object> bookItem(
            @RequestHeader(CustomHeaders.USER_ID) @Positive Long userId,
            @RequestBody @Valid CreateBookingDto requestDto
    ) {
        return bookingClient.createBooking(
                userId,
                requestDto
        );
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> getBooking(
            @RequestHeader(CustomHeaders.USER_ID) @Positive Long userId,
            @PathVariable Long bookingId
    ) {
        return bookingClient.getBooking(
                userId,
                bookingId
        );
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> confirmBooking(
            @RequestHeader(CustomHeaders.USER_ID) @Positive Long userId,
            @PathVariable Long bookingId,
            @RequestParam Boolean approved
    ) {
        return bookingClient.confirmBooking(
                userId,
                bookingId,
                approved
        );
    }
}
