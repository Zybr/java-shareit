package ru.practicum.shareit.features.booking.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.common.mapper.ModelMapper;
import ru.practicum.shareit.features.booking.dto.BookingOutDto;
import ru.practicum.shareit.features.booking.model.Booking;
import ru.practicum.shareit.features.item.mapper.item.ItemMapper;
import ru.practicum.shareit.features.user.mapper.UserMapper;

@Service
@RequiredArgsConstructor
public class BookingOutMapper extends ModelMapper<Booking, BookingOutDto> {
    private final BookingRelationsProvider provider;
    private final UserMapper userOutMapper;
    private final ItemMapper itemOutMapper;

    @Override
    public BookingOutDto toDto(Booking model) {
        BookingOutDto dto = new BookingOutDto(
                model.getStart(),
                model.getEnd(),
                itemOutMapper.toDto(
                        model.getItem()
                ),
                userOutMapper.toDto(
                        model.getBooker()
                )
        );
        dto.setId(
                model.getId()
        );
        dto.setStatus(
                model.getStatus()
        );
        return dto;
    }

    @Override
    public Booking toModel(BookingOutDto dto) {
        return Booking.builder()
                .id(dto.getId())
                .start(dto.getStart())
                .end(dto.getEnd())
                .status(dto.getStatus())
                .item(provider.getItem(
                        getEntityId(
                                dto.getItem()
                        )
                ))
                .booker(provider.getUser(
                        getEntityId(
                                dto.getBooker()
                        )
                ))
                .build();
    }
}
