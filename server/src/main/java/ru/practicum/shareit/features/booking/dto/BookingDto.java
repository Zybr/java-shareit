package ru.practicum.shareit.features.booking.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.common.annotations.SerializableDateTime;
import ru.practicum.shareit.common.dto.ModelDto;
import ru.practicum.shareit.features.booking.model.BookingStatus;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class BookingDto implements ModelDto {
    private Long id;

    @SerializableDateTime
    private final LocalDateTime start;

    @SerializableDateTime
    private final LocalDateTime end;

    private BookingStatus status = BookingStatus.WAITING;
}
