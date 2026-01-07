package ru.practicum.shareit.features.booking.dto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.common.dto.DtoTestCase;
import ru.practicum.shareit.database.providers.FactoryProvider;
import ru.practicum.shareit.database.providers.RepositoryProvider;
import ru.practicum.shareit.features.booking.model.BookingStatus;
import ru.practicum.shareit.features.item.dto.item.ItemDto;
import ru.practicum.shareit.features.user.dto.UserDto;

import java.time.LocalDateTime;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class BookingOutDtoTest extends DtoTestCase<BookingOutDto> {
    private final FactoryProvider factoryProvider = new FactoryProvider(new RepositoryProvider(null, null, null, null, null));

    @Test
    void shouldSerializeBookingOutDto() throws Exception {
        LocalDateTime start = LocalDateTime.of(2024, 1, 1, 12, 0, 0);
        LocalDateTime end = start.plusDays(1);

        ItemDto itemDto = factoryProvider.itemDto().make(new ItemDto("Item Name", "Item Description", true, null));
        itemDto.setId(2L);

        UserDto bookerDto = factoryProvider.userDto().make(new UserDto("Booker", "booker@email.com"));
        bookerDto.setId(3L);

        BookingOutDto attributes = new BookingOutDto(start, end, itemDto, bookerDto);
        attributes.setId(1L);
        attributes.setStatus(BookingStatus.WAITING);
        BookingOutDto dto = factoryProvider.bookingOutDto().make(attributes);

        JsonContent<BookingOutDto> result = json.write(dto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.status").isEqualTo("WAITING");
        assertThat(result).extractingJsonPathStringValue("$.start").isEqualTo(formatTime(start));
        assertThat(result).extractingJsonPathStringValue("$.end").isEqualTo(formatTime(end));

        assertThat(result).extractingJsonPathNumberValue("$.item.id").isEqualTo(2);
        assertThat(result).extractingJsonPathStringValue("$.item.name").isEqualTo("Item Name");

        assertThat(result).extractingJsonPathNumberValue("$.booker.id").isEqualTo(3);
        assertThat(result).extractingJsonPathStringValue("$.booker.name").isEqualTo("Booker");
    }

    @Test
    void shouldDeserializeBookingOutDto() throws Exception {
        Map<String, Object> data = Map.of(
                "id", 1,
                "status", "WAITING",
                "start", "2024-01-01T12:00:00",
                "end", "2024-01-02T12:00:00",
                "item", Map.of(
                        "id", 2,
                        "name", "Item Name"
                ),
                "booker", Map.of(
                        "id", 3,
                        "name", "Booker"
                )
        );

        String content = objectMapper.writeValueAsString(data);
        BookingOutDto dto = objectMapper.readValue(content, BookingOutDto.class);

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getStatus()).isEqualTo(BookingStatus.WAITING);
        assertThat(dto.getStart()).isEqualTo(parseTime((String) data.get("start")));
        assertThat(dto.getEnd()).isEqualTo(parseTime((String) data.get("end")));
        assertThat(dto.getItem().getId()).isEqualTo(2L);
        assertThat(dto.getBooker().getId()).isEqualTo(3L);
    }
}
