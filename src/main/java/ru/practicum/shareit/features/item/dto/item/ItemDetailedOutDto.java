package ru.practicum.shareit.features.item.dto.item;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.features.item.dto.comment.CommentOutDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ItemDetailedOutDto extends ItemDto {
    private final LocalDateTime lastBooking;

    private final LocalDateTime nextBooking;

    private final List<CommentOutDto> comments = new ArrayList<>();

    public ItemDetailedOutDto(
            String name,
            String description,
            Boolean available,
            Long requestId,
            LocalDateTime lastBooking,
            LocalDateTime nextBooking
    ) {
        super(
                name,
                description,
                available,
                requestId
        );
        this.lastBooking = lastBooking;
        this.nextBooking = nextBooking;
    }

    public void setComments(List<CommentOutDto> comments) {
        this.comments.clear();
        this.comments.addAll(comments);
    }
}
