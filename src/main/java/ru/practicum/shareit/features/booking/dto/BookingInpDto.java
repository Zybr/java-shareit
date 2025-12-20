package ru.practicum.shareit.features.booking.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BookingInpDto extends BookingDto {
    private final Long itemId;

    private Long bookerId;

    public BookingInpDto(
            LocalDateTime start,
            LocalDateTime end,
            Long itemId
    ) {
        super(start, end);
        this.itemId = itemId;
    }
}
