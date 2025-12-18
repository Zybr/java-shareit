package ru.practicum.shareit.features.user.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.common.repository.ModelRepositoryTest;
import ru.practicum.shareit.factory.UserFactory;
import ru.practicum.shareit.features.user.model.User;

import java.util.List;

@SpringBootTest()
public class UserRepositoryTest extends ModelRepositoryTest<UserRepository, User> {
    public UserRepositoryTest(
            @Autowired
            UserRepository repository
    ) {
        super(
                repository,
                new UserFactory(
                        repository
                )
        );
    }

    /**
     * @see UserRepository#findByEmail(String)
     */
    @Test
    void shouldFindByEmail() {
        List<User> users = getFactory().createList(5);
        User targetUser = users.get(2);

        var fetchedUser = getRepository().findByEmail(targetUser.getEmail());

        Assertions.assertTrue(fetchedUser.isPresent());
        Assertions.assertEquals(
                targetUser.getId(),
                fetchedUser.get().getId()
        );
    }
}
