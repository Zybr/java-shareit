package ru.practicum.shareit.factory.booking;

import ru.practicum.shareit.database.providers.FactoryProvider;
import ru.practicum.shareit.factory.ModelFactory;
import ru.practicum.shareit.features.booking.model.Booking;
import ru.practicum.shareit.features.booking.model.BookingStatus;

import java.time.LocalDateTime;

public class BookingFactory extends ModelFactory<Booking> {
    private final FactoryProvider factories;

    public BookingFactory(FactoryProvider factories) {
        super(factories.repositories().booking());
        this.factories = factories;
    }

    @Override
    public Booking make(Booking attributes) {
        attributes = attributes == null ? new Booking() : attributes;

        return Booking.builder()
                .id(getValueOrDefault(attributes.getId(), makeId()))
                .start(getValueOrDefault(attributes.getStart(), LocalDateTime.now().plusDays(1)))
                .end(getValueOrDefault(attributes.getEnd(), LocalDateTime.now().plusDays(2)))
                .status(getValueOrDefault(attributes.getStatus(), BookingStatus.WAITING))
                .item(getValueOrDefault(attributes.getItem(), factories.item().make()))
                .booker(getValueOrDefault(attributes.getBooker(), factories.user().make()))
                .build();
    }

    @Override
    public Booking create(Booking attributes) {
        attributes = attributes == null ? new Booking() : attributes;

        Booking model = make(attributes);
        model.setBooker(getValueOrDefault(attributes.getBooker(), factories.user().create()));
        model.setItem(getValueOrDefault(attributes.getItem(), factories.item().create()));

        return repository.saveAndFlush(model);
    }
}
