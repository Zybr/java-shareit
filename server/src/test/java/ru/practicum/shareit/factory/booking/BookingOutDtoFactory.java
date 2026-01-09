package ru.practicum.shareit.factory.booking;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.database.providers.FactoryProvider;
import ru.practicum.shareit.factory.DataFactory;
import ru.practicum.shareit.features.booking.dto.BookingOutDto;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class BookingOutDtoFactory extends DataFactory<BookingOutDto> {
    private final FactoryProvider factories;

    @Override
    public BookingOutDto make(BookingOutDto attributes) {
        attributes = attributes != null ? attributes : new BookingOutDto(null, null, null, null);

        BookingOutDto dto = new BookingOutDto(
                getValueOrDefault(attributes.getStart(), LocalDateTime.now().plusDays(1)),
                getValueOrDefault(attributes.getEnd(), LocalDateTime.now().plusDays(2)),
                getValueOrDefault(attributes.getItem(), factories.itemDto().make()),
                getValueOrDefault(attributes.getBooker(), factories.userDto().make())
        );
        dto.setId(getValueOrDefault(attributes.getId(), makeId()));
        dto.setStatus(attributes.getStatus());

        return dto;
    }
}
