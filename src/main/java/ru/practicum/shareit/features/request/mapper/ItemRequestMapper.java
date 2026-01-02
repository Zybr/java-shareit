package ru.practicum.shareit.features.request.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.shareit.features.item.mapper.item.ItemMapper;
import ru.practicum.shareit.features.request.dto.ItemRequestCreationDto;
import ru.practicum.shareit.features.request.dto.ItemRequestOutDto;
import ru.practicum.shareit.features.request.model.ItemRequest;

@Mapper(
        componentModel = "spring",
        uses = {
                ItemRequestRelationsProvider.class,
                ItemMapper.class
        }
)
public abstract class ItemRequestMapper {
    @Mapping(target = "requester", source = "requester.id")
    @Mapping(target = "items", source = "id", qualifiedByName = "getRequestItems")
    public abstract ItemRequestOutDto toOutDto(ItemRequest itemRequest);

    @Mapping(target = "requester", source = "requesterId", qualifiedByName = "getRequester")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    public abstract ItemRequest toRequest(ItemRequestCreationDto creationDto);
}
