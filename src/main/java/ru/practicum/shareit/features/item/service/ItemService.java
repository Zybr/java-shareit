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
        return repository.findAllByOwnerId(ownerId);
    }

    public List<Item> findListByOwner(
            Long ownerId,
            String searchText
    ) {
        return repository.findAllByOwnerIdAndSearchText(ownerId, searchText);
    }

    @Override
    protected Item fill(Item source, Item target) {
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
