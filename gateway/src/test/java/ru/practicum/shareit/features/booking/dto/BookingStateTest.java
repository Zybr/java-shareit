package ru.practicum.shareit.features.booking.dto;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class BookingStateTest {

    @Test
    void shouldReturnStateWhenValidString() {
        Optional<BookingState> state = BookingState.from("ALL");
        assertThat(state).isPresent().contains(BookingState.ALL);

        state = BookingState.from("current");
        assertThat(state).isPresent().contains(BookingState.CURRENT);
    }

    @Test
    void shouldReturnEmptyWhenInvalidString() {
        Optional<BookingState> state = BookingState.from("INVALID");
        assertThat(state).isEmpty();
    }
}
