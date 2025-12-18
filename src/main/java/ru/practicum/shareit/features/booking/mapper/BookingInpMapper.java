package ru.practicum.shareit.features.booking.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.common.mapper.ModelMapper;
import ru.practicum.shareit.features.booking.dto.BookingInpDto;
import ru.practicum.shareit.features.booking.model.Booking;

@Service
@RequiredArgsConstructor
public class BookingInpMapper extends ModelMapper<Booking, BookingInpDto> {
    private final BookingRelationsProvider provider;

    @Override
    public BookingInpDto toDto(Booking model) {
        BookingInpDto dto = new BookingInpDto(
                model.getStart(),
                model.getEnd(),
                getEntityId(model.getItem())
        );
        dto.setId(
                model.getId()
        );
        dto.setStatus(
                model.getStatus()
        );
        dto.setBookerId(getEntityId(
                model.getBooker()
        ));

        return dto;
    }

    @Override
    public Booking toModel(BookingInpDto dto) {
        return Booking
                .builder()
                .id(dto.getId())
                .start(dto.getStart())
                .end(dto.getEnd())
                .status(dto.getStatus())
                .item(provider.getItem(
                        dto.getItemId()
                ))
                .booker(provider.getUser(
                        dto.getBookerId()
                ))
                .build();
    }
}
