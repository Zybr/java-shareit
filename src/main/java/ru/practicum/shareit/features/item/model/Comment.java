package ru.practicum.shareit.features.item.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.common.model.Model;
import ru.practicum.shareit.features.user.model.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
public class Comment implements Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Comment(String text) {
        this.text = text;
    }

    @Column(nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime created = LocalDateTime.now();
}
