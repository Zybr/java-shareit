package ru.practicum.shareit.features.request.service;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.common.service.BaseModelService;
import ru.practicum.shareit.features.request.model.ItemRequest;
import ru.practicum.shareit.features.request.repository.ItemRequestRepository;

import java.util.List;

@Service
public class ItemRequestService extends BaseModelService<ItemRequest> {
    private final ItemRequestRepository repository;

    public ItemRequestService(
            ItemRequestRepository repository
    ) {
        super(repository);
        this.repository = repository;
    }

    public List<ItemRequest> findAllByRequesterId(Long requesterId) {
        return repository.findAllByRequesterId(requesterId);
    }

    @Override
    protected ItemRequest fill(ItemRequest source, ItemRequest target) {
        target.setDescription(getValueOrDefault(
                source.getDescription(),
                target.getDescription()
        ));
        target.setRequester(getValueOrDefault(
                source.getRequester(),
                target.getRequester()
        ));
        target.setCreated(getValueOrDefault(
                source.getCreated(),
                target.getCreated()
        ));
        return target;
    }
}
