package ru.practicum.shareit.database;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import ru.practicum.shareit.database.providers.FactoryProvider;
import ru.practicum.shareit.database.providers.RepositoryProvider;
import ru.practicum.shareit.features.booking.repository.BookingRepository;
import ru.practicum.shareit.features.item.repository.CommentRepository;
import ru.practicum.shareit.features.item.repository.ItemRepository;
import ru.practicum.shareit.features.request.repository.ItemRequestRepository;
import ru.practicum.shareit.features.user.repository.UserRepository;

@Transactional
public class DbTestCase {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemRequestRepository itemRequestRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private CommentRepository commentRepository;

    private RepositoryProvider repositoryProvider;
    private FactoryProvider factoryProvider;

    protected FactoryProvider factories() {
        if (factoryProvider == null) {
            factoryProvider = new FactoryProvider(repositories());
        }

        return factoryProvider;
    }

    private RepositoryProvider repositories() {
        if (repositoryProvider == null) {
            repositoryProvider = new RepositoryProvider(
                    userRepository,
                    itemRepository,
                    itemRequestRepository,
                    bookingRepository,
                    commentRepository
            );
        }

        return repositoryProvider;
    }
}
