package ru.practicum.shareit.features.request.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.common.client.BaseClient;
import ru.practicum.shareit.features.request.dto.ItemRequestCreationDto;

@Component
public class ItemRequestClient extends BaseClient {
    @Override
    protected String getPathPrefix() {
        return "requests";
    }

    public ResponseEntity<Object> getRequests() {
        return get("/all");
    }

    public ResponseEntity<Object> getUserRequests(
            long userId
    ) {
        return get(
                "",
                userId
        );
    }

    public ResponseEntity<Object> getRequest(
            long requestId
    ) {
        return get("/" + requestId);
    }

    public ResponseEntity<Object> createRequest(
            long userId,
            ItemRequestCreationDto createDto
    ) {
        return post(
                "",
                userId,
                createDto
        );
    }
}
