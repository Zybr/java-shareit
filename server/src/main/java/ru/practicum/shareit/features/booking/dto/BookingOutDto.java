package ru.practicum.shareit.features.booking.dto;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.features.item.dto.item.ItemDto;
import ru.practicum.shareit.features.user.dto.UserDto;

import java.time.LocalDateTime;

@Getter
@Setter
public class BookingOutDto extends BookingDto {
    private final ItemDto item;

    private final UserDto booker;

    public BookingOutDto(
            LocalDateTime start,
            LocalDateTime end,
            ItemDto item,
            UserDto booker
    ) {
        super(start, end);
        this.item = item;
        this.booker = booker;
    }
}
