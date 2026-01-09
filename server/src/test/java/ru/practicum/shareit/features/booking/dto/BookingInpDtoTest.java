package ru.practicum.shareit.features.booking.dto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.common.dto.DtoTestCase;
import ru.practicum.shareit.database.providers.FactoryProvider;
import ru.practicum.shareit.database.providers.RepositoryProvider;

import java.time.LocalDateTime;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class BookingInpDtoTest extends DtoTestCase<BookingInpDto> {
    private final FactoryProvider factoryProvider = new FactoryProvider(new RepositoryProvider(null, null, null, null, null));

    @Test
    void shouldSerializeBookingInpDto() throws Exception {
        LocalDateTime start = LocalDateTime.of(2024, 1, 1, 12, 0, 0);
        LocalDateTime end = start.plusDays(1);
        BookingInpDto dto = factoryProvider.bookingInpDto().make(new BookingInpDto(start, end, 1L));
        dto.setId(2L);
        dto.setBookerId(3L);

        JsonContent<BookingInpDto> result = json.write(dto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(2);
        assertThat(result).extractingJsonPathStringValue("$.start").isEqualTo(formatTime(start));
        assertThat(result).extractingJsonPathStringValue("$.end").isEqualTo(formatTime(end));
        assertThat(result).extractingJsonPathNumberValue("$.itemId").isEqualTo(1);
        assertThat(result).extractingJsonPathNumberValue("$.bookerId").isEqualTo(3);
    }

    @Test
    void shouldDeserializeBookingInpDto() throws Exception {
        Map<String, Object> data = Map.of(
                "id", 2,
                "start", "2024-01-01T12:00:00",
                "end", "2024-01-02T12:00:00",
                "itemId", 1,
                "bookerId", 3
        );

        String content = objectMapper.writeValueAsString(data);
        BookingInpDto dto = objectMapper.readValue(content, BookingInpDto.class);

        assertThat(dto.getId()).isEqualTo(2L);
        assertThat(dto.getStart()).isEqualTo(parseTime((String) data.get("start")));
        assertThat(dto.getEnd()).isEqualTo(parseTime((String) data.get("end")));
        assertThat(dto.getItemId()).isEqualTo(1L);
        assertThat(dto.getBookerId()).isEqualTo(3L);
    }
}
