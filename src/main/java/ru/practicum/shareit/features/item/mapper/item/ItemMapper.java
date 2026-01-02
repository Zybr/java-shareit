package ru.practicum.shareit.features.item.mapper.item;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.shareit.features.item.dto.item.ItemDetailedOutDto;
import ru.practicum.shareit.features.item.dto.item.ItemDto;
import ru.practicum.shareit.features.item.mapper.comment.CommentMapper;
import ru.practicum.shareit.features.item.model.Item;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {
                ItemRelationsProvider.class,
                CommentMapper.class
        }
)
public abstract class ItemMapper {
    @Mapping(target = "ownerId", source = "owner.id")
    @Mapping(target = "requestId", source = "request.id")
    public abstract ItemDto toDto(Item item);

    public abstract List<ItemDto> toDto(List<Item> items);

    @Mapping(target = "owner", source = "ownerId", qualifiedByName = "getUser")
    @Mapping(target = "request", source = "requestId", qualifiedByName = "getRequest")
    public abstract Item toModel(ItemDto dto);

    @Mapping(target = "ownerId", source = "owner.id")
    @Mapping(target = "requestId", source = "request.id")
    @Mapping(target = "lastBooking", source = "id", qualifiedByName = "getPreviousBookingTime")
    @Mapping(target = "nextBooking", source = "id", qualifiedByName = "getNextBookingTime")
    @Mapping(target = "comments", source = "id", qualifiedByName = "getItemComments")
    public abstract ItemDetailedOutDto toDetailedDto(Item item);
}
