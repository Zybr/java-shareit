package ru.practicum.shareit.features.item.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.common.exception.NotFoundException;
import ru.practicum.shareit.common.mapper.ModelMapper;
import ru.practicum.shareit.features.item.dto.ItemDto;
import ru.practicum.shareit.features.item.model.Item;
import ru.practicum.shareit.features.user.model.User;
import ru.practicum.shareit.features.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ItemMapper extends ModelMapper<Item, ItemDto> {
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    @Override
    public ItemDto toDto(Item item) {
        ItemDto dto = new ItemDto(
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                item.getRequest() != null ? item.getRequest().getId() : null
        );
        dto.setId(item.getId());
        dto.setOwner(item.getOwner().getId());

        return dto;
    }

    @Override
    public Item toModel(ItemDto dto) {
        return Item.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .available(dto.getAvailable())
                .owner(getUser(dto.getOwner()))
//                .request() // TODO: Implement in one of the following sprint
                .build();
    }

    @Override
    public Item copyModel(Item model) {
        return objectMapper.convertValue(model, Item.class);
    }

    private User getUser(Long id) {
        return userRepository
                .findOne(id)
                .orElseThrow(
                        () -> new NotFoundException(
                                String.format(
                                        "User \"%d\" not found",
                                        id
                                )
                        )
                );
    }
}
