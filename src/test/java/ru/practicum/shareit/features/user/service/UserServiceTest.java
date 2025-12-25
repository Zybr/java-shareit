package ru.practicum.shareit.features.user.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.practicum.shareit.config.PersistenceConfig;
import ru.practicum.shareit.factory.user.UserFactory;
import ru.practicum.shareit.features.user.model.User;
import ru.practicum.shareit.features.user.repository.UserRepository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@ActiveProfiles("test")
@SpringJUnitConfig(classes = {
        UserService.class,
        PersistenceConfig.class,
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
@EntityScan(basePackages = "ru.practicum.shareit")
public class UserServiceTest {
    private final UserRepository repository;
    private UserFactory factory;
    private final UserService service;

    @Test
    void testCreateOne() {
        User creation = getUserFactory().make();

        User createdUser = service.createOne(creation);
        User fetchedUser = service.getOne(createdUser.getId());

        assertThat(
                createdUser,
                equalTo(fetchedUser)
        );
    }

    private UserFactory getUserFactory() {
        if (factory == null) {
            factory = new UserFactory(repository);
        }

        return factory;
    }
}