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

    public User createOne(User creation) {
        assertFreeEmail(creation.getEmail());

        return super.createOne(creation);
    }

    @Override
    public User updateOne(User update) {
        assertFreeEmail(
                update.getEmail(),
                update.getId()
        );

        return super.updateOne(update);
    }

    private void assertFreeEmail(String email) {
        Optional<User> existedUser = repository.findOneByEmail(email);

        if (existedUser.isPresent()) {
            throwEmailDuplicationException(email);
        }
    }

    private void assertFreeEmail(
            String email,
            Long userId
    ) {
        Optional<User> existedUser = repository.findOneByEmail(email);

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
}
