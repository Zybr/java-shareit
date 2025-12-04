package ru.practicum.shareit.features.item.repository;

import ru.practicum.shareit.common.repository.ModelRepository;
import ru.practicum.shareit.features.item.model.Item;

import java.util.List;

public interface ItemRepository extends ModelRepository<Item> {
    List<Item> findListByOwner(Long ownerId);

    List<Item> findListByOwner(
            Long ownerId,
            String searchText
    );
}
