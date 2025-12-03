package ru.practicum.shareit.features.item.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.common.repository.ModelRepositoryMemory;
import ru.practicum.shareit.features.item.model.Item;

import java.util.List;

@Repository
public class ItemRepositoryMemory extends ModelRepositoryMemory<Item> implements ItemRepository {
    @Override
    public List<Item> findListByOwner(Long ownerId) {
        return models
                .values()
                .stream()
                .filter(
                        item -> item
                                .getOwner()
                                .getId()
                                .equals(ownerId)
                )
                .toList();
    }

    @Override
    public List<Item> findListByOwner(Long ownerId, String searchText) {
        return findListByOwner(ownerId)
                .stream()
                .filter(item ->
                        item.getAvailable() // (!) According to the Postman's test, it's considered only on search
                                && (
                                item.getName().toLowerCase().contains(searchText.toLowerCase())
                                        || item.getDescription().contains(searchText.toLowerCase())
                        )
                )
                .toList();
    }

    @Override
    protected Item fill(Item source, Item target) {
        target.setId(getValueOrDefault(
                source.getId(),
                target.getId()
        ));
        target.setName(getValueOrDefault(
                source.getName(),
                target.getName()
        ));
        target.setDescription(getValueOrDefault(
                source.getDescription(),
                target.getDescription()
        ));
        target.setAvailable(getValueOrDefault(
                source.getAvailable(),
                target.getAvailable()
        ));
        target.setOwner(getValueOrDefault(
                source.getOwner(),
                target.getOwner()
        ));
        target.setRequest(getValueOrDefault(
                source.getRequest(),
                target.getRequest()
        ));

        return target;
    }
}
