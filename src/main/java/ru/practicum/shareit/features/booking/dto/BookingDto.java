package ru.practicum.shareit.features.booking.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.features.booking.model.BookingStatus;
import ru.practicum.shareit.common.dto.ModelDto;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class BookingDto implements ModelDto {
    private final Long id;
    private final LocalDate start;
    private final LocalDate end;
    private final int item;
    private final int booker;
    private final BookingStatus status;
}
