package ru.practicum.shareit.factory.item;

import ru.practicum.shareit.factory.DataFactory;
import ru.practicum.shareit.features.item.dto.comment.CommentInpDto;

public class CommentInpDtoFactory extends DataFactory<CommentInpDto> {
    @Override
    public CommentInpDto make(CommentInpDto attributes) {
        attributes = attributes != null ? attributes : new CommentInpDto(null, null);

        CommentInpDto dto = new CommentInpDto(
                getValueOrDefault(attributes.getId(), makeId()),
                getValueOrDefault(attributes.getText(), makeUniqueWord())
        );
        dto.setItemId(getValueOrDefault(attributes.getItemId(), makeId()));
        dto.setAuthorId(getValueOrDefault(attributes.getAuthorId(), makeId()));

        return dto;
    }
}
