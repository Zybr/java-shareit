package ru.practicum.shareit.database.providers;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;
import ru.practicum.shareit.features.booking.repository.BookingRepository;
import ru.practicum.shareit.features.item.repository.CommentRepository;
import ru.practicum.shareit.features.item.repository.ItemRepository;
import ru.practicum.shareit.features.request.repository.ItemRequestRepository;
import ru.practicum.shareit.features.user.repository.UserRepository;

@RequiredArgsConstructor
@Value
@Accessors(fluent = true)
public class RepositoryProvider {
    UserRepository user;
    ItemRepository item;
    ItemRequestRepository itemRequest;
    BookingRepository booking;
    CommentRepository comment;
}
