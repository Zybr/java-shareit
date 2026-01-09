package ru.practicum.shareit.features.request.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.common.constant.CustomHeaders;
import ru.practicum.shareit.features.request.client.ItemRequestClient;
import ru.practicum.shareit.features.request.dto.ItemRequestCreationDto;

@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
@Validated
public class ItemRequestController {
    private final ItemRequestClient requestClient;

    @GetMapping("/all")
    public ResponseEntity<Object> getRequests() {
        return requestClient.getRequests();
    }

    @GetMapping
    public ResponseEntity<Object> getUserRequests(
            @RequestHeader(CustomHeaders.USER_ID) @Positive Long userId
    ) {
        return requestClient.getUserRequests(userId);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getRequest(
            @PathVariable @Positive Long requestId
    ) {
        return requestClient.getRequest(requestId);
    }

    @PostMapping
    public ResponseEntity<Object> createRequest(
            @RequestHeader(CustomHeaders.USER_ID) @Positive Long userId,
            @RequestBody @Valid ItemRequestCreationDto createDto
    ) {
        return requestClient.createRequest(
                userId,
                createDto
        );
    }
}
