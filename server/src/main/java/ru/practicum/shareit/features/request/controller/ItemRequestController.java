package ru.practicum.shareit.features.request.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.common.constants.CustomHeaders;
import ru.practicum.shareit.features.request.dto.ItemRequestCreationDto;
import ru.practicum.shareit.features.request.dto.ItemRequestOutDto;
import ru.practicum.shareit.features.request.mapper.ItemRequestMapper;
import ru.practicum.shareit.features.request.service.ItemRequestService;

import java.util.List;

@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class ItemRequestController {
    private final ItemRequestService requestService;
    private final ItemRequestMapper requestMapper;

    @GetMapping("/all")
    public List<ItemRequestOutDto> getRequests() {
        return requestService
                .findList()
                .stream()
                .map(requestMapper::toOutDto)
                .toList();
    }

    @GetMapping
    public List<ItemRequestOutDto> getUserRequests(
            @RequestHeader(CustomHeaders.USER_ID) Long ownerId
    ) {
        return requestService
                .findAllByRequesterId(ownerId)
                .stream()
                .map(requestMapper::toOutDto)
                .toList();
    }

    @GetMapping("/{requestId}")
    public ItemRequestOutDto getRequest(
            @PathVariable Long requestId
    ) {
        return requestMapper.toOutDto(
                requestService.getOne(requestId)
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemRequestOutDto createRequest(
            @RequestHeader(CustomHeaders.USER_ID) Long requesterId,
            @RequestBody ItemRequestCreationDto creationDto
    ) {
        return requestMapper.toOutDto(
                requestService.createOne(
                        requestMapper.toRequest(
                                creationDto.setRequesterId(requesterId)
                        )
                )
        );
    }
}
