package ru.practicum.shareit.gateway.features.request.client;
import ru.practicum.shareit.features.request.client.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.client.MockRestServiceServer;
import ru.practicum.shareit.features.request.dto.ItemRequestCreationDto;

import java.util.Map;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(ItemRequestClient.class)
@TestPropertySource(properties = "shareit-server.url=" + ItemRequestClientTest.URL)
public class ItemRequestClientTest {
    public static final String URL = "http://shareit-server";

    @Autowired
    private ItemRequestClient client;

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void shouldGetRequests() {
        server.expect(requestTo(URL + "/requests/all"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("[]", MediaType.APPLICATION_JSON));

        client.getRequests();
    }

    @Test
    void shouldGetUserRequests() {
        server.expect(requestTo(URL + "/requests"))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header("X-Sharer-User-Id", "1"))
                .andRespond(withSuccess("[]", MediaType.APPLICATION_JSON));

        client.getUserRequests(1L);
    }

    @Test
    void shouldGetRequest() {
        server.expect(requestTo(URL + "/requests/1"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{}", MediaType.APPLICATION_JSON));

        client.getRequest(1L);
    }

    @Test
    void shouldCreateRequest() throws Exception {
        ItemRequestCreationDto dto = new ItemRequestCreationDto("Description");
        String json = mapper.writeValueAsString(Map.of("description", "Description"));

        server.expect(requestTo(URL + "/requests"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(header("X-Sharer-User-Id", "1"))
                .andExpect(content().json(json))
                .andRespond(withSuccess("{}", MediaType.APPLICATION_JSON));

        client.createRequest(1L, dto);
    }
}
