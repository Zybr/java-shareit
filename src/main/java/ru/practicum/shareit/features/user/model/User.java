package ru.practicum.shareit.features.user.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Tolerate;
import ru.practicum.shareit.common.model.Model;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class User implements Model {
    @Tolerate
    public User() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    private String name;

    @Column(length = 50, nullable = false, unique = true)
    private String email;
}
