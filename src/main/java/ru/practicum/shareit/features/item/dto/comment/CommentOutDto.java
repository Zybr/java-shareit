package ru.practicum.shareit.features.item.dto.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentOutDto extends CommentDto {
    private final String authorName;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
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
