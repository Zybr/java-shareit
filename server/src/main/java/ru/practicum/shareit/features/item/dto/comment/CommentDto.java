package ru.practicum.shareit.features.item.dto.comment;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.common.dto.ModelDto;

@Data
@RequiredArgsConstructor
public class CommentDto implements ModelDto {
    protected final Long id;

    protected final String text;
}
