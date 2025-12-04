package ru.practicum.shareit.features.item.service;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.common.service.BaseModelService;
import ru.practicum.shareit.features.item.model.Item;
import ru.practicum.shareit.features.item.repository.ItemRepository;

import java.util.List;

@Service
public class ItemService extends BaseModelService<Item> {
    private final ItemRepository repository;

    public ItemService(ItemRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public List<Item> findListByOwner(Long ownerId) {
        return repository.findListByOwner(ownerId);
    }

    public List<Item> findListByOwner(
            Long ownerId,
            String searchText
    ) {
        return repository.findListByOwner(ownerId, searchText);
    }
}
