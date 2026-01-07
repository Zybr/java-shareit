package ru.practicum.shareit.database.providers;

import lombok.Value;
import lombok.experimental.Accessors;
import ru.practicum.shareit.factory.booking.BookingDtoFactory;
import ru.practicum.shareit.factory.booking.BookingFactory;
import ru.practicum.shareit.factory.booking.BookingInpDtoFactory;
import ru.practicum.shareit.factory.booking.BookingOutDtoFactory;
import ru.practicum.shareit.factory.item.*;
import ru.practicum.shareit.factory.request.ItemRequestCreationDtoFactory;
import ru.practicum.shareit.factory.request.ItemRequestFactory;
import ru.practicum.shareit.factory.request.ItemRequestOutDtoFactory;
import ru.practicum.shareit.factory.user.UserDtoFactory;
import ru.practicum.shareit.factory.user.UserFactory;

@Value
@Accessors(fluent = true)
public class FactoryProvider {
    RepositoryProvider repositories;

    UserFactory user;
    UserDtoFactory userDto;

    ItemFactory item;
    ItemDtoFactory itemDto;
    ItemDetailedOutDtoFactory itemDetailedOut;

    ItemRequestFactory itemRequest;
    ItemRequestOutDtoFactory itemRequestOut;
    ItemRequestCreationDtoFactory itemRequestCreationDto;

    BookingFactory booking;
    BookingDtoFactory bookingDto;
    BookingInpDtoFactory bookingInpDto;
    BookingOutDtoFactory bookingOutDto;

    CommentFactory comment;
    CommentInpDtoFactory commentInpDto;
    CommentOutDtoFactory commentOutDto;

    public FactoryProvider(RepositoryProvider repositories) {
        this.repositories = repositories;

        this.user = new UserFactory(this);
        this.userDto = new UserDtoFactory();

        this.item = new ItemFactory(this);
        this.itemDto = new ItemDtoFactory();
        this.itemDetailedOut = new ItemDetailedOutDtoFactory();

        this.itemRequest = new ItemRequestFactory(this);
        this.itemRequestOut = new ItemRequestOutDtoFactory();
        this.itemRequestCreationDto = new ItemRequestCreationDtoFactory();

        this.booking = new BookingFactory(this);
        this.bookingDto = new BookingDtoFactory();
        this.bookingInpDto = new BookingInpDtoFactory();
        this.bookingOutDto = new BookingOutDtoFactory(this);

        this.comment = new CommentFactory(this);
        this.commentInpDto = new CommentInpDtoFactory();
        this.commentOutDto = new CommentOutDtoFactory();
    }
}
