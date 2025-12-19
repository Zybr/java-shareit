package ru.practicum.shareit.features.item.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Tolerate;
import ru.practicum.shareit.common.model.Model;
import ru.practicum.shareit.features.request.model.ItemRequest;
import ru.practicum.shareit.features.user.model.User;

@Entity
@Table(name = "items")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Item implements Model {
    @Tolerate
    public Item() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(name = "is_available", nullable = false)
    private Boolean available;

    @OneToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @Transient
    private ItemRequest request;
}
