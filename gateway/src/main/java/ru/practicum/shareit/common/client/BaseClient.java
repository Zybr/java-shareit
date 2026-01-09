package ru.practicum.shareit.common.client;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.common.constant.CustomHeaders;

import java.util.List;
import java.util.Map;

public abstract class BaseClient {
    @Value("${shareit-server.url}")
    private String serverUrl;
    @Autowired
    private RestTemplateBuilder restBuilder;

    protected RestTemplate rest;

    protected abstract String getPathPrefix();

    @PostConstruct
    public void initRest() {
        rest = restBuilder
                .uriTemplateHandler(
                        new DefaultUriBuilderFactory(
                                serverUrl
                                        + "/"
                                        + getPathPrefix().trim().replaceAll("^/+", "")
                        )
                )
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                .build();
    }

    protected ResponseEntity<Object> get(String path) {
        return makeAndSendRequest(HttpMethod.GET, path, null, null, null);
    }

    protected ResponseEntity<Object> get(String path, long userId) {
        return makeAndSendRequest(HttpMethod.GET, path, userId, null, null);
    }

    protected ResponseEntity<Object> get(String path, Long userId, @Nullable Map<String, Object> parameters) {
        return makeAndSendRequest(HttpMethod.GET, path, userId, parameters, null);
    }

    protected <T> ResponseEntity<Object> post(String path, T body) {
        return makeAndSendRequest(HttpMethod.POST, path, null, null, body);
    }

    protected <T> ResponseEntity<Object> post(String path, long userId, T body) {
        return makeAndSendRequest(HttpMethod.POST, path, userId, null, body);
    }

    protected <T> ResponseEntity<Object> patch(String path, T body) {
        return makeAndSendRequest(HttpMethod.PATCH, path, null, null, body);
    }

    protected <T> ResponseEntity<Object> patch(String path, long userId) {
        return makeAndSendRequest(HttpMethod.PATCH, path, userId, null, null);
    }

    protected <T> ResponseEntity<Object> patch(String path, long userId, T body) {
        return makeAndSendRequest(HttpMethod.PATCH, path, userId, null, body);
    }

    protected ResponseEntity<Object> delete(String path) {
        return makeAndSendRequest(HttpMethod.DELETE, path, null, null, null);
    }

    private <T> ResponseEntity<Object> makeAndSendRequest(
            HttpMethod method,
            String path,
            Long userId,
            @Nullable Map<String, Object> parameters,
            @Nullable T body
    ) {
        HttpEntity<T> requestEntity = new HttpEntity<>(body, defaultHeaders(userId));

        ResponseEntity<Object> shareitServerResponse;
        try {
            if (parameters != null) {
                shareitServerResponse = rest.exchange(path, method, requestEntity, Object.class, parameters);
            } else {
                shareitServerResponse = rest.exchange(path, method, requestEntity, Object.class);
            }
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsByteArray());
        }
        return prepareGatewayResponse(shareitServerResponse);
    }

    private HttpHeaders defaultHeaders(
            Long userId
    ) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        if (userId != null) {
            headers.set(CustomHeaders.USER_ID, String.valueOf(userId));
        }

        return headers;
    }

    private static ResponseEntity<Object> prepareGatewayResponse(
            ResponseEntity<Object> response
    ) {
        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        }

        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());

        if (response.hasBody()) {
            return responseBuilder.body(response.getBody());
        }

        return responseBuilder.build();
    }
}
