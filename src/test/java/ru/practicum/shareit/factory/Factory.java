package ru.practicum.shareit.factory;

import com.github.javafaker.Faker;
import ru.practicum.shareit.common.model.Model;
import ru.practicum.shareit.common.repository.ModelRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * "Making" is without saving in durable storage
 * "Creation" is with saving in durable storage
 */
public abstract class Factory<M extends Model> {
    protected final Faker faker = new Faker();
    protected final ModelRepository<M> repository;

    protected Factory(ModelRepository<M> repository) {
        this.repository = repository;
    }

    // Model makers >>>

    public abstract M make(M attributes);

    public M make() {
        return make(null);
    }

    public M create() {
        return repository.createOne(
                make()
        );
    }

    public M create(M attributes) {
        return repository.createOne(
                make(attributes)
        );
    }

    public List<M> makeList() {
        return makeList(10);
    }

    public List<M> makeList(int size) {
        return collectList(size, this::make);
    }

    public List<M> createList() {
        return createList(10);
    }

    public List<M> createList(int size) {
        return collectList(size, this::create);
    }

    private List<M> collectList(int size, Create<M> createItem) {
        List<M> list = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            list.add(create());
        }

        return list;
    }

    // <<< Model makers

    // Scalar makers >>>

    protected Long makeId() {
        return Math.abs(faker.random().nextLong(1000));
    }

    protected String makeUniqueWord() {
        return faker.lorem().word() + makeId();
    }

    protected LocalDate dateToLocal(
            Date date
    ) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    // <<< Scalar makers

    @FunctionalInterface
    private interface Create<M> {
        M create();
    }

    protected <T> T getValueOrDefault(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }
}
