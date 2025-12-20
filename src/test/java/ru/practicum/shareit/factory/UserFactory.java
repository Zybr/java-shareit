package ru.practicum.shareit.factory;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.features.user.model.User;


public class UserFactory extends Factory<User> {
    protected final JpaRepository<User, Long> repository;

    public UserFactory(JpaRepository<User, Long> repository) {
        super(repository);
        this.repository = repository;
    }

    public User make(User attributes) {
        return User.builder()
                .id(getValueOrDefault(
                        attributes != null ? attributes.getId() : null,
                        makeId()
                ))
                .email(getValueOrDefault(
                        attributes != null ? attributes.getEmail() : null,
                        makeUniqueWord() + faker.internet().emailAddress()
                ))
                .name(getValueOrDefault(
                        attributes != null ? attributes.getName() : null,
                        makeUniqueWord()
                ))
                .build();
    }
}
