package ru.practicum.shareit.features.item.dto.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentInpDto extends CommentDto {
    private Long itemId;

    private Long authorId;

    public CommentInpDto(Long id, String text) {
        super(id, text);
    }
}
