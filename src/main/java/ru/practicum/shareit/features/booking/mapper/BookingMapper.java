package ru.practicum.shareit.features.booking.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.shareit.features.booking.dto.BookingInpDto;
import ru.practicum.shareit.features.booking.dto.BookingOutDto;
import ru.practicum.shareit.features.booking.model.Booking;
import ru.practicum.shareit.features.item.mapper.item.ItemMapper;
import ru.practicum.shareit.features.user.mapper.UserMapper;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {
                BookingRelationsProvider.class,
                UserMapper.class,
                ItemMapper.class
        }
)
public abstract class BookingMapper {
    @Mapping(target = "itemId", source = "item.id")
    @Mapping(target = "bookerId", source = "booker.id")
    public abstract BookingInpDto toDto(Booking model);

    @Mapping(target = "item", source = "itemId", qualifiedByName = "getItem")
    @Mapping(target = "booker", source = "bookerId", qualifiedByName = "getUser")
    public abstract Booking toModel(BookingInpDto dto);

    public abstract BookingOutDto toOutDto(Booking model);

    public abstract List<BookingOutDto> toOutDto(List<Booking> models);

    @Mapping(target = "item", source = "item.id", qualifiedByName = "getItem")
    @Mapping(target = "booker", source = "booker.id", qualifiedByName = "getUser")
    public abstract Booking toModel(BookingOutDto dto);
}
