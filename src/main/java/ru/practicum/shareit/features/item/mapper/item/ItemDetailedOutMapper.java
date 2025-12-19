package ru.practicum.shareit.features.item.mapper.item;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.features.booking.model.Booking;
import ru.practicum.shareit.features.item.dto.item.ItemDetailedOutDto;
import ru.practicum.shareit.features.item.mapper.comment.CommentOutMapper;
import ru.practicum.shareit.features.item.model.Item;

@Service
public class ItemDetailedOutMapper extends BaseItemMapper<ItemDetailedOutDto> {
    private final CommentOutMapper commentOutMapper;

    public ItemDetailedOutMapper(
            ItemRelationsProvider relationsProvider,
            CommentOutMapper commentOutMapper
    ) {
        super(relationsProvider);
        this.commentOutMapper = commentOutMapper;
    }

    @Override
    public ItemDetailedOutDto toDto(Item item) {
        ItemDetailedOutDto dto = new ItemDetailedOutDto(
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                getEntityId(item.getRequest()),
                relationsProvider
                        .getPreviousBooking(item.getId())
                        .map(Booking::getEnd)
                        .orElse(null),
                relationsProvider.getNextBooking(item.getId())
                        .map(Booking::getStart)
                        .orElse(null)
        );
        dto.setId(item.getId());
        dto.setOwnerId(item.getOwner().getId());
        dto.setComments(
                commentOutMapper.toDto(
                        relationsProvider.getItemComments(item.getId())
                )
        );

        return dto;
    }
}
