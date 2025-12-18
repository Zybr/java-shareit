package ru.practicum.shareit.features.item.mapper.comment;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.common.mapper.ModelProvider;
import ru.practicum.shareit.features.item.model.Item;
import ru.practicum.shareit.features.item.repository.ItemRepository;
import ru.practicum.shareit.features.user.model.User;
import ru.practicum.shareit.features.user.repository.UserRepository;

@Service
public class CommentRelationsProvider extends ModelProvider {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public CommentRelationsProvider(
            ItemRepository itemRepository,
            UserRepository userRepository
    ) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    public User getUser(Long id) {
        return getModel(userRepository, id);
    }

    public Item getItem(Long id) {
        return getModel(itemRepository, id);
    }
}
