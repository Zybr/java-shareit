package ru.practicum.shareit.common.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.common.exception.NotFoundException;

public abstract class ModelProvider {
    protected  <M> M getModel(
            JpaRepository<M, Long> repository,
            Long id
    ) {
        String modelName = repository.getClass().getSimpleName().replace("Repository", "");

        return repository
                .findById(id)
                .orElseThrow(
                        () -> new NotFoundException(
                                String.format(
                                        "Model %s:%d not found",
                                        modelName,
                                        id
                                )
                        )
                );
    }
}
