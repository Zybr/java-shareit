package ru.practicum.shareit.features.booking.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.common.annotations.SerializableDateTime;
import ru.practicum.shareit.common.dto.ModelDto;
import ru.practicum.shareit.common.validation.action.OnCreate;
import ru.practicum.shareit.features.booking.model.BookingStatus;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class BookingDto implements ModelDto {
    private Long id;

    @NotNull(groups = OnCreate.class)
    @FutureOrPresent
    @SerializableDateTime
    private final LocalDateTime start;

    @NotNull(groups = OnCreate.class)
    @FutureOrPresent
    @SerializableDateTime
    private final LocalDateTime end;

    private BookingStatus status = BookingStatus.WAITING;
}
