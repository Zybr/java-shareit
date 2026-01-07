package ru.practicum.shareit.factory.item;

import ru.practicum.shareit.factory.DataFactory;
import ru.practicum.shareit.features.item.dto.comment.CommentOutDto;

import java.time.LocalDateTime;

public class CommentOutDtoFactory extends DataFactory<CommentOutDto> {
    @Override
    public CommentOutDto make(CommentOutDto attributes) {
        attributes = attributes != null ? attributes : new CommentOutDto(null, null, null, null);

        return new CommentOutDto(
                getValueOrDefault(attributes.getId(), makeId()),
                getValueOrDefault(attributes.getText(), makeUniqueWord()),
                getValueOrDefault(attributes.getAuthorName(), makeUniqueWord()),
                getValueOrDefault(attributes.getCreated(), LocalDateTime.now())
        );
    }
}
