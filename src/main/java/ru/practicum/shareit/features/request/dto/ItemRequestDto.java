package ru.practicum.shareit.features.request.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.common.dto.ModelDto;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class ItemRequestDto implements ModelDto {
    private Long id;
    private String description;
    private int requester;
    private LocalDate created;
}
