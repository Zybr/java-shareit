package ru.practicum.shareit.features.request.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.common.mapper.ModelProvider;
import ru.practicum.shareit.features.item.model.Item;
import ru.practicum.shareit.features.item.repository.ItemRepository;
import ru.practicum.shareit.features.user.model.User;
import ru.practicum.shareit.features.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemRequestRelationsProvider extends ModelProvider {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Named("getRequestItems")
    public List<Item> getRequestItems(Long requestId) {
        return itemRepository.findAllByRequestId(requestId);
    }

    @Named("getRequester")
    public User getRequester(Long requesterId) {
        return this.getModel(
                userRepository,
                requesterId
        );
    }
}
