package ru.practicum.shareit.features.item.mapper.comment;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.features.item.dto.comment.CommentInpDto;
import ru.practicum.shareit.features.item.model.Comment;

@Service
public class CommentInpMapper extends BaseCommentMapper<CommentInpDto> {
    public CommentInpMapper(CommentRelationsProvider relationsProvider) {
        super(relationsProvider);
    }

    @Override
    public CommentInpDto toDto(Comment model) {
        CommentInpDto dto = new CommentInpDto(
                model.getId(),
                model.getText()
        );
        dto.setItemId(model.getItem().getId());
        dto.setAuthorId(model.getAuthor().getId());

        return dto;
    }

    @Override
    public Comment toModel(CommentInpDto dto) {
        Comment comment = new Comment(
                dto.getText()
        );

        comment.setId(dto.getId());
        comment.setItem(
                relationsProvider.getItem(
                        dto.getItemId()
                )
        );
        comment.setAuthor(
                relationsProvider.getUser(
                        dto.getAuthorId()
                )
        );

        return comment;
    }
}
