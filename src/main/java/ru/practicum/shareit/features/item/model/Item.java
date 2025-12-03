package ru.practicum.shareit.features.item.model;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.common.model.Model;
import ru.practicum.shareit.features.request.model.ItemRequest;
import ru.practicum.shareit.features.user.model.User;

@Data
@Builder
public class Item implements Model {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private User owner;
    private ItemRequest request;
}
