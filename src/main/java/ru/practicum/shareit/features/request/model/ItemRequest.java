package ru.practicum.shareit.features.request.model;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.common.model.Model;
import ru.practicum.shareit.features.user.model.User;

import java.time.LocalDate;

@Getter
@Setter
public class ItemRequest implements Model {
    private Long id;
    private String description;
    private User requester;
    private LocalDate created;
}
