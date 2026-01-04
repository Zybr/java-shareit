package ru.practicum.shareit.factory.booking;

import ru.practicum.shareit.factory.DataFactory;
import ru.practicum.shareit.features.booking.dto.BookingInpDto;

import java.time.LocalDateTime;

public class BookingInpDtoFactory extends DataFactory<BookingInpDto> {
    @Override
    public BookingInpDto make(BookingInpDto attributes) {
        attributes = attributes != null ? attributes : new BookingInpDto(null, null, null);

        BookingInpDto dto = new BookingInpDto(
                getValueOrDefault(attributes.getStart(), LocalDateTime.now().plusDays(1)),
                getValueOrDefault(attributes.getEnd(), LocalDateTime.now().plusDays(2)),
                getValueOrDefault(attributes.getItemId(), makeId())
        );
        dto.setId(getValueOrDefault(attributes.getId(), makeId()));
        dto.setBookerId(getValueOrDefault(attributes.getBookerId(), makeId()));
        dto.setStatus(attributes.getStatus());

        return dto;
    }
}
