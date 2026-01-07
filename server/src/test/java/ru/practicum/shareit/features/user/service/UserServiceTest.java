package ru.practicum.shareit.features.user.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.practicum.shareit.common.exception.DuplicationException;
import ru.practicum.shareit.common.exception.NotFoundException;
import ru.practicum.shareit.database.DbTestCase;
import ru.practicum.shareit.features.user.model.User;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest extends DbTestCase {
    @Autowired
    private UserService service;

    @Test
    void shouldCreateUser() {
        User creation = factories().user().make();

        User createdUser = service.createOne(creation);
        User fetchedUser = service.getOne(createdUser.getId());

        assertThat(
                createdUser,
                equalTo(fetchedUser)
        );
    }

    @Test
    void shouldThrowDuplicationWhenCreatingUserWithDuplicateEmail() {
        User user1 = factories().user().create();
        User user2 = factories().user().make();
        user2.setEmail(user1.getEmail());

        Assertions.assertThrows(DuplicationException.class, () -> service.createOne(user2));
    }

    @Test
    void shouldUpdateUser() {
        User user = factories().user().create();
        User update = User.builder()
                .id(user.getId())
                .name("New Name")
                .build();

        User updated = service.updateOne(update);

        assertThat(updated.getName(), equalTo("New Name"));
        assertThat(updated.getEmail(), equalTo(user.getEmail()));
    }

    @Test
    void shouldThrowDuplicationWhenUpdatingUserWithDuplicateEmail() {
        User user1 = factories().user().create();
        User user2 = factories().user().create();
        User update = User.builder()
                .id(user2.getId())
                .email(user1.getEmail())
                .build();

        Assertions.assertThrows(DuplicationException.class, () -> service.updateOne(update));
    }

    @Test
    void shouldThrowNotFoundWhenUserDoesNotExistById() {
        Assertions.assertThrows(NotFoundException.class, () -> service.getOne(999L));
    }

    @Test
    void shouldFindAllUsers() {
        factories().user().createList(3);

        List<User> users = service.findList();

        assertThat(users.size(), equalTo(3));
    }

    @Test
    void shouldDeleteUserById() {
        User user = factories().user().create();

        service.deleteOne(user.getId());

        Assertions.assertThrows(NotFoundException.class, () -> service.getOne(user.getId()));
    }
}