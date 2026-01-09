package ru.practicum.shareit.factory;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class DataFactory<D extends Object> {
    protected final Faker faker = new Faker();

    // Data makers >>>

    public abstract D make(D attributes);

    public D make() {
        return make(null);
    }

    public List<D> makeList() {
        return makeList(10);
    }

    public List<D> makeList(int size) {
        return collectList(size, this::make);
    }

    protected List<D> collectList(int size, Maker<D> maker) {
        List<D> list = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            list.add(maker.make());
        }

        return list;
    }

    // <<< Data makers

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

    protected <V> V getValueOrDefault(V value, V defaultValue) {
        return value == null ? defaultValue : value;
    }

    @FunctionalInterface
    protected interface Maker<M> {
        M make();
    }
}
