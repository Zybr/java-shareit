package ru.practicum.shareit.features.user.repository;

import ru.practicum.shareit.common.repository.ModelRepository;
import ru.practicum.shareit.features.user.model.User;

import java.util.Optional;

public interface UserRepository extends ModelRepository<User> {
    Optional<User> findOneByEmail(String email);
}
