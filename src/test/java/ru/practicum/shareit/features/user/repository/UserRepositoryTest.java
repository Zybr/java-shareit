package ru.practicum.shareit.features.user.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.practicum.shareit.common.repository.ModelRepositoryTestCase;
import ru.practicum.shareit.factory.user.UserFactory;
import ru.practicum.shareit.features.user.model.User;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTest extends ModelRepositoryTestCase<UserRepository, User> {
    @Override
    protected UserRepository repository() {
        return factories().repositories().user();
    }

    @Override
    protected UserFactory factory() {
        return factories().user();
    }

    /**
     * @see UserRepository#findByEmail(String)
     */
    @Test
    void shouldFindByEmail() {
        List<User> users = factory().createList(5);
        User targetUser = users.get(2);

        var fetchedUser = repository().findByEmail(targetUser.getEmail());

        Assertions.assertTrue(fetchedUser.isPresent());
        Assertions.assertEquals(
                targetUser.getId(),
                fetchedUser.get().getId()
        );
    }
}
