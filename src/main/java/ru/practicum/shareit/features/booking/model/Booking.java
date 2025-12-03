package ru.practicum.shareit.features.booking.model;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.common.model.Model;
import ru.practicum.shareit.features.item.model.Item;
import ru.practicum.shareit.features.user.model.User;

import java.time.LocalDate;

@Data
@Builder
public class Booking implements Model {
    private Long id;
    private LocalDate start;
    private LocalDate end;
    private Item item;
    private User booker;
    private BookingStatus status;
}
