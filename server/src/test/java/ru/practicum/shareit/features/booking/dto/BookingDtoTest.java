package ru.practicum.shareit.features.booking.dto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.common.dto.DtoTestCase;
import ru.practicum.shareit.database.providers.FactoryProvider;
import ru.practicum.shareit.database.providers.RepositoryProvider;
import ru.practicum.shareit.features.booking.model.BookingStatus;

import java.time.LocalDateTime;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class BookingDtoTest extends DtoTestCase<BookingDto> {
    private final FactoryProvider factoryProvider = new FactoryProvider(new RepositoryProvider(null, null, null, null, null));

    @Test
    void shouldSerializeBookingDto() throws Exception {
        LocalDateTime start = LocalDateTime.of(2024, 1, 1, 12, 0, 0);
        LocalDateTime end = start.plusDays(1);
        BookingDto attributes = new BookingDto(start, end);
        attributes.setId(1L);
        attributes.setStatus(BookingStatus.APPROVED);
        BookingDto dto = factoryProvider.bookingDto().make(attributes);

        JsonContent<BookingDto> result = json.write(dto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.status").isEqualTo("APPROVED");
        assertThat(result).extractingJsonPathStringValue("$.start").isEqualTo(formatTime(start));
        assertThat(result).extractingJsonPathStringValue("$.end").isEqualTo(formatTime(end));
    }

    @Test
    void shouldDeserializeBookingDto() throws Exception {
        Map<String, String> data = Map.of(
                "start", "2024-01-01T12:00:00",
                "end", "2024-01-02T12:00:00",
                "status", "REJECTED"
        );
        String content = objectMapper.writeValueAsString(data);
        BookingDto dto = objectMapper.readValue(content, BookingDto.class);

        assertThat(dto.getStart()).isEqualTo(parseTime(data.get("start")));
        assertThat(dto.getEnd()).isEqualTo(parseTime(data.get("end")));
        assertThat(dto.getStatus()).isEqualTo(BookingStatus.REJECTED);
    }
}
