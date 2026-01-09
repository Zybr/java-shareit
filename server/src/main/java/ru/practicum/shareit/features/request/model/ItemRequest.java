package ru.practicum.shareit.features.request.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import ru.practicum.shareit.common.model.Model;
import ru.practicum.shareit.features.user.model.User;

import java.time.LocalDate;

@Entity
@Table(name = "requests")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequest implements Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false)
    private String description;

    @OneToOne
    @JoinColumn(name = "requester_id")
    private User requester;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDate created;
}
