package ru.practicum.shareit.features.user.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.common.repository.ModelRepositoryMemory;
import ru.practicum.shareit.features.user.model.User;

import java.util.Optional;

@Repository
public class UserRepositoryMemory extends ModelRepositoryMemory<User> implements UserRepository {
    @Override
    public Optional<User> findOneByEmail(String email) {
        return email == null
                ? Optional.empty()
                : models.values()
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    @Override
    protected User fill(User source, User target) {
        target.setId(getValueOrDefault(
                source.getId(),
                target.getId()
        ));
        target.setName(getValueOrDefault(
                source.getName(),
                target.getName()
        ));
        target.setEmail(getValueOrDefault(
                source.getEmail(),
                target.getEmail()
        ));

        return target;
    }
}
