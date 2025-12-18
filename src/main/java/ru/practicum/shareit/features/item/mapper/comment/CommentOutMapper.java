package ru.practicum.shareit.features.item.mapper.comment;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.features.item.dto.comment.CommentOutDto;
import ru.practicum.shareit.features.item.model.Comment;

@Service
public class CommentOutMapper extends BaseCommentMapper<CommentOutDto> {
    public CommentOutMapper(CommentRelationsProvider relationsProvider) {
        super(relationsProvider);
    }

    @Override
    public CommentOutDto toDto(Comment model) {
        return new CommentOutDto(
                model.getId(),
                model.getText(),
                model.getAuthor().getName(),
                model.getCreated()
        );
    }

    @Override
    public Comment toModel(CommentOutDto dto) {
        Comment comment = new Comment(
                dto.getText()
        );

        comment.setId(dto.getId());
        comment.setCreated(dto.getCreated());

        return comment;
    }
}
