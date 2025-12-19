package ru.practicum.shareit.features.item.mapper.comment;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.common.mapper.ModelMapper;
import ru.practicum.shareit.features.item.dto.comment.CommentDto;
import ru.practicum.shareit.features.item.model.Comment;


@RequiredArgsConstructor
public abstract class BaseCommentMapper<D extends CommentDto> extends ModelMapper<Comment, D> {
    protected final CommentRelationsProvider relationsProvider;
}
