package ru.practicum.shareit.gateway.features.booking.dto;
import ru.practicum.shareit.features.booking.dto.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class BookingDtoTest {

    @Autowired
    private JacksonTester<CreateBookingDto> json;

    @Test
    void testCreateBookingDto() throws Exception {
        LocalDateTime start = LocalDateTime.of(2024, 1, 1, 12, 0);
        LocalDateTime end = start.plusDays(1);
        CreateBookingDto dto = new CreateBookingDto(1L, start, end);

        JsonContent<CreateBookingDto> result = json.write(dto);

        assertThat(result).hasJsonPathNumberValue("$.itemId", 1);
        assertThat(result).hasJsonPathStringValue("$.start");
        assertThat(result).hasJsonPathStringValue("$.end");
    }
}
