package ru.practicum.shareit.features.booking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime start;

    @NotNull(groups = OnCreate.class)
    @FutureOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime end;

    private BookingStatus status = BookingStatus.WAITING;
}
