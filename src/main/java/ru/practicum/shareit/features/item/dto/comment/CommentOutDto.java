package ru.practicum.shareit.features.item.dto.comment;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.common.annotations.SerializableDateTime;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentOutDto extends CommentDto {
    private final String authorName;

    @SerializableDateTime
    private final LocalDateTime created;

    public CommentOutDto(
            Long id,
            String text,
            String authorName,
            LocalDateTime created
    ) {
        super(id, text);
        this.authorName = authorName;
        this.created = created;
    }
}
