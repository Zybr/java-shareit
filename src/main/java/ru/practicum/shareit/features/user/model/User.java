package ru.practicum.shareit.features.user.model;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.common.model.Model;

@Data
@Builder
public class User implements Model {
    private Long id;
    private String name;
    private String email;
}
