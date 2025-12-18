package ru.practicum.shareit.features.item.dto.comment;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.common.dto.ModelDto;

@Data
@RequiredArgsConstructor
public class CommentDto implements ModelDto {
    protected final Long id;

    @NotNull()
    protected final String text;
}
