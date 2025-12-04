package ru.practicum.shareit.features.request.model;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.common.model.Model;
import ru.practicum.shareit.features.user.model.User;

import java.time.LocalDate;

@Data
@Builder
public class ItemRequest implements Model {
    private Long id;
    private String description;
    private User requester;
    private LocalDate created;
}
