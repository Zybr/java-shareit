package ru.practicum.shareit.factory.user;


import ru.practicum.shareit.database.providers.FactoryProvider;
import ru.practicum.shareit.factory.ModelFactory;
import ru.practicum.shareit.features.user.model.User;
import ru.practicum.shareit.features.user.repository.UserRepository;


public class UserFactory extends ModelFactory<User> {
    protected final UserRepository repository;

    public UserFactory(FactoryProvider factories) {
        super(factories.repositories().user());
        this.repository = factories.repositories().user();
    }

    @Override
    public User make(User attributes) {
        attributes = attributes != null ? attributes : new User();

        return User.builder()
                .id(getValueOrDefault(attributes.getId(), makeId()))
                .email(
                        getValueOrDefault(attributes.getEmail(),
                                makeUniqueWord() + faker.internet().emailAddress())
                )
                .name(getValueOrDefault(attributes.getName(), makeUniqueWord()))
                .build();
    }
}
