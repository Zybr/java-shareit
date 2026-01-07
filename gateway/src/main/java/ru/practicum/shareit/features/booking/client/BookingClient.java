package ru.practicum.shareit.features.booking.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.common.client.BaseClient;
import ru.practicum.shareit.features.booking.dto.CreateBookingDto;
import ru.practicum.shareit.features.booking.dto.BookingState;

import java.util.Map;

@Component
public class BookingClient extends BaseClient {
    private static final String SEARCH_QUERY = "?state={state}&from={from}&size={size}";

    @Override
    protected String getPathPrefix() {
        return "bookings";
    }

    public ResponseEntity<Object> getBookings(
            long userId,
            BookingState state,
            Integer from,
            Integer size
    ) {
        return get(
                SEARCH_QUERY,
                userId,
                Map.of(
                        "state", state.name(),
                        "from", from,
                        "size", size
                )
        );
    }

    public ResponseEntity<Object> getOwnerBookings(
            long userId,
            BookingState state,
            Integer from,
            Integer size
    ) {
        return get(
                "/owner" + SEARCH_QUERY,
                userId,
                Map.of(
                        "state", state.name(),
                        "from", from,
                        "size", size
                )
        );
    }

    public ResponseEntity<Object> getBooking(
            long userId,
            Long bookingId
    ) {
        return get("/" + bookingId, userId);
    }

    public ResponseEntity<Object> createBooking(
            long userId,
            CreateBookingDto createDto
    ) {
        return post(
                "",
                userId,
                createDto
        );
    }

    public ResponseEntity<Object> confirmBooking(
            long userId,
            Long bookingId,
            Boolean approved
    ) {
        return patch(
                "/" + bookingId + "?approved=" + approved,
                userId
        );
    }
}
