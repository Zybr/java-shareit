package ru.practicum.shareit.features.user.service;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.common.exception.DuplicationException;
import ru.practicum.shareit.common.service.BaseModelService;
import ru.practicum.shareit.features.user.model.User;
import ru.practicum.shareit.features.user.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService extends BaseModelService<User> {
    protected final UserRepository repository;

    public UserService(UserRepository repository) {
        super(repository);
        this.repository = repository;
    }


    @Override
    protected void validate(User model, Action action) throws RuntimeException {
        switch (action) {
            case UPDATE -> this.assertFreeEmail(model.getEmail(), model.getId());
            case CREATION -> this.assertFreeEmail(model.getEmail());
        }
    }

    private void assertFreeEmail(String email) {
        Optional<User> existedUser = repository.findByEmail(email);

        if (existedUser.isPresent()) {
            throwEmailDuplicationException(email);
        }
    }

    private void assertFreeEmail(
            String email,
            Long userId
    ) {
        Optional<User> existedUser = repository.findByEmail(email);

        if (existedUser.isPresent() && !existedUser.get().getId().equals(userId)) {
            throwEmailDuplicationException(email);
        }
    }

    private void throwEmailDuplicationException(String email) {
        throw new DuplicationException(
                String.format(
                        "Email \"%s\" is used already",
                        email
                )
        );
    }

    @Override
    protected User fill(User source, User target) {
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
