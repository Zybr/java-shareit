package ru.practicum.shareit.features.booking.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.common.mapper.ModelProvider;
import ru.practicum.shareit.features.item.model.Item;
import ru.practicum.shareit.features.item.repository.ItemRepository;
import ru.practicum.shareit.features.user.model.User;
import ru.practicum.shareit.features.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class BookingRelationsProvider extends ModelProvider {
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public User getUser(Long id) {
        return getModel(userRepository, id);
    }

    public Item getItem(Long id) {
        return getModel(itemRepository, id);
    }
}
