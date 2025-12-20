package ru.practicum.shareit.features.item.mapper.comment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.shareit.features.item.dto.comment.CommentInpDto;
import ru.practicum.shareit.features.item.dto.comment.CommentOutDto;
import ru.practicum.shareit.features.item.model.Comment;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = CommentRelationsProvider.class
)
public interface CommentMapper {
    @Mapping(target = "authorName", source = "author.name")
    CommentOutDto toDto(Comment model);

    List<CommentOutDto> toDto(List<Comment> models);

    @Mapping(target = "itemId", source = "item.id")
    @Mapping(target = "authorId", source = "author.id")
    CommentInpDto toInpDto(Comment model);

    @Mapping(target = "item", source = "itemId", qualifiedByName = "getItem")
    @Mapping(target = "author", source = "authorId", qualifiedByName = "getUser")
    @Mapping(target = "created", ignore = true)
    Comment toModel(CommentInpDto dto);

    @Mapping(target = "item", ignore = true)
    @Mapping(target = "author", ignore = true)
    Comment toModel(CommentOutDto dto);
}
