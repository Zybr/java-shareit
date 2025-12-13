package ru.practicum.shareit.common.repository;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.common.model.Model;
import ru.practicum.shareit.factory.Factory;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@RequiredArgsConstructor
public abstract class ModelRepositoryTest<R extends JpaRepository<M, Long>, M extends Model> {
    @Autowired
    private ObjectMapper mapper;
    @Getter
    private final R repository;
    @Getter
    private final Factory<M> factory;

    @BeforeEach
    protected void clean() {
        repository.findAll()
                .forEach(
                        model -> repository
                                .deleteById(model.getId())
                );
    }

    @Test
    public void shouldCreateModel() {
        M createdModel = factory.create();

        assertStoredModel(createdModel);
        assertModelListSize(1);
    }

    @Test
    public void shouldUpdateOne() {
        M createdModel = factory.create();

        M update = factory.make(createdModel);

        assertStoredModel(repository.saveAndFlush(update));
        assertStoredModel(update);
        assertModelListSize(1);
    }

    @Test
    public void shouldCheckIfExisted() {
        Assertions.assertFalse(
                repository.existsById(1L)
        );

        M createdModel = factory.create();

        Assertions.assertTrue(
                repository.existsById(
                        createdModel.getId()
                )
        );
    }

    @Test
    public void shouldFindOne() {
        Assertions.assertFalse(
                repository
                        .findById(1L)
                        .isPresent()
        );

        M createdModel = factory.create();
        Optional<M> fetchedModel = repository.findById(createdModel.getId());

        Assertions.assertTrue(fetchedModel.isPresent());
        assertEqualModels(
                createdModel,
                fetchedModel.get()
        );
    }

    @Test
    public void shouldFindList() {
        assertModelListSize(0);

        M createdModelA = factory.create();
        M createdModelB = factory.create();

        assertModelListSize(2);
        assertStoredModel(createdModelA);
        assertStoredModel(createdModelB);

        List<M> fetchedModels = repository.findAll();
        assertEqualModels(
                createdModelA,
                fetchedModels.get(0)
        );
        assertEqualModels(
                createdModelB,
                fetchedModels.get(1)
        );
    }

    private void assertStoredModel(M storedModel) {
        // Fetch a certain Model

        Optional<M> fetchedModel = repository.findById(storedModel.getId());
        Assertions.assertTrue(fetchedModel.isPresent());

        assertEqualModels(
                storedModel,
                fetchedModel.get()
        );

        // Fetch all Models

        Optional<M> fetchedFromList = repository.findAll()
                .stream().filter(model -> model.getId().equals(storedModel.getId()))
                .findFirst();
        Assertions.assertTrue(fetchedFromList.isPresent());

        assertEqualModels(
                storedModel,
                fetchedFromList.get()
        );
    }

    private void assertEqualModels(
            M modelA,
            M modelB
    ) {
        Assertions.assertEquals(
                mapper.valueToTree(modelA),
                mapper.valueToTree(modelB)
        );
    }

    private void assertModelListSize(int expectedSize) {
        Assertions.assertEquals(
                expectedSize,
                repository.findAll().size()
        );
    }
}
