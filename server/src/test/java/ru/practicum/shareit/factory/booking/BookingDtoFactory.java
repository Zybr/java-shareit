package ru.practicum.shareit.factory.booking;

import ru.practicum.shareit.factory.DataFactory;
import ru.practicum.shareit.features.booking.dto.BookingDto;
import ru.practicum.shareit.features.booking.model.BookingStatus;

import java.time.LocalDateTime;

public class BookingDtoFactory extends DataFactory<BookingDto> {
    @Override
    public BookingDto make(BookingDto attributes) {
        attributes = attributes != null ? attributes : new BookingDto(null, null);

        BookingDto dto = new BookingDto(
                getValueOrDefault(attributes.getStart(), LocalDateTime.now().plusDays(1)),
                getValueOrDefault(attributes.getEnd(), LocalDateTime.now().plusDays(2))
        );
        dto.setId(getValueOrDefault(attributes.getId(), makeId()));
        dto.setStatus(getValueOrDefault(attributes.getStatus(), BookingStatus.WAITING));

        return dto;
    }
}
