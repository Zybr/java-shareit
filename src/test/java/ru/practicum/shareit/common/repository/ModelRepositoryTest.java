package ru.practicum.shareit.common.repository;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.common.model.Model;
import ru.practicum.shareit.factory.Factory;

import java.util.List;
import java.util.Optional;

/**
 * @see ModelRepository
 */
@SpringBootTest
@RequiredArgsConstructor
public abstract class ModelRepositoryTest<R extends ModelRepository<M>, M extends Model> {
    @Autowired
    private ObjectMapper mapper;
    @Getter
    private final R repository;
    @Getter
    private final Factory<M> factory;

    @BeforeEach
    protected void clean() {
        repository.findList()
                .forEach(
                        model -> repository
                                .deleteOne(model.getId())
                );
    }

    /**
     * @see ModelRepository#createOne(Model)
     */
    @Test
    public void shouldCreateModel() {
        M createdModel = factory.create();

        assertStoredModel(createdModel);
        assertModelListSize(1);
    }

    /**
     * @see ModelRepository#updateOne(Model)
     */
    @Test
    public void shouldUpdateOne() {
        M createdModel = factory.create();

        M update = factory.make();
        update.setId(createdModel.getId());

        assertStoredModel(repository.updateOne(update));
        assertStoredModel(update);
        assertModelListSize(1);
    }

    /**
     * @see ModelRepository#isExisted(Long)
     */
    @Test
    public void shouldCheckIfExisted() {
        Assertions.assertFalse(
                repository.isExisted(1L)
        );

        M createdModel = factory.create();

        Assertions.assertTrue(
                repository.isExisted(
                        createdModel.getId()
                )
        );
    }

    /**
     * @see ModelRepository#findOne(Long)
     */
    @Test
    public void shouldFindOne() {
        Assertions.assertFalse(
                repository
                        .findOne(1L)
                        .isPresent()
        );

        M createdModel = factory.create();
        Optional<M> fetchedModel = repository.findOne(createdModel.getId());

        Assertions.assertTrue(fetchedModel.isPresent());
        assertEqualModels(
                createdModel,
                fetchedModel.get()
        );
    }

    /**
     * @see ModelRepository#findList()
     */
    @Test
    public void shouldFindList() {
        assertModelListSize(0);

        M createdModelA = factory.create();
        M createdModelB = factory.create();

        assertModelListSize(2);
        assertStoredModel(createdModelA);
        assertStoredModel(createdModelB);

        List<M> fetchedModels = repository.findList();
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

        Optional<M> fetchedModel = repository.findOne(storedModel.getId());
        Assertions.assertTrue(fetchedModel.isPresent());

        assertEqualModels(
                storedModel,
                fetchedModel.get()
        );

        // Fetch all Models

        Optional<M> fetchedFromList = repository.findList()
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
                repository.findList().size()
        );
    }
}
