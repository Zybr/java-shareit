package ru.practicum.shareit.gateway.features.item.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.client.MockRestServiceServer;
import ru.practicum.shareit.features.item.client.ItemClient;
import ru.practicum.shareit.features.item.dto.CreateCommentDto;
import ru.practicum.shareit.features.item.dto.CreateItemDto;
import ru.practicum.shareit.features.item.dto.UpdateItemDto;

import java.util.Map;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(ItemClient.class)
@TestPropertySource(properties = "shareit-server.url=" + ItemClientTest.URL)
public class ItemClientTest {
    public static final String URL = "http://shareit-server";

    @Autowired
    private ItemClient client;

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void shouldGetItems() {
        server.expect(requestTo(URL + "/items"))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header("X-Sharer-User-Id", "1"))
                .andRespond(withSuccess("[]", MediaType.APPLICATION_JSON));

        client.getItems(1L);
    }

    @Test
    void shouldGetItem() {
        server.expect(requestTo(URL + "/items/1"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{}", MediaType.APPLICATION_JSON));

        client.getItem(1L);
    }

    @Test
    void shouldSearchItems() {
        server.expect(requestTo(URL + "/items/search?text=search"))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header("X-Sharer-User-Id", "1"))
                .andRespond(withSuccess("[]", MediaType.APPLICATION_JSON));

        client.searchItems(1L, "search");
    }

    @Test
    void shouldCreateItem() throws Exception {
        CreateItemDto dto = new CreateItemDto("Name", "Description", true, null);
        String json = mapper.writeValueAsString(Map.of(
                "name", "Name",
                "description", "Description",
                "available", true
        ));

        server.expect(requestTo(URL + "/items"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(header("X-Sharer-User-Id", "1"))
                .andExpect(content().json(json))
                .andRespond(withSuccess("{}", MediaType.APPLICATION_JSON));

        client.createItem(1L, dto);
    }

    @Test
    void shouldUpdateItem() throws Exception {
        UpdateItemDto dto = new UpdateItemDto("New Name", null, null);
        String json = mapper.writeValueAsString(Map.of("name", "New Name"));

        server.expect(requestTo(URL + "/items/1"))
                .andExpect(method(HttpMethod.PATCH))
                .andExpect(header("X-Sharer-User-Id", "1"))
                .andExpect(content().json(json))
                .andRespond(withSuccess("{}", MediaType.APPLICATION_JSON));

        client.updateItem(1L, 1L, dto);
    }

    @Test
    void shouldDeleteItem() {
        server.expect(requestTo(URL + "/items/1"))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withSuccess());

        client.deleteItem(1L);
    }

    @Test
    void shouldCreateComment() throws Exception {
        CreateCommentDto dto = new CreateCommentDto("Comment");
        String json = mapper.writeValueAsString(Map.of("text", "Comment"));

        server.expect(requestTo(URL + "/items/1/comment"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(header("X-Sharer-User-Id", "1"))
                .andExpect(content().json(json))
                .andRespond(withSuccess("{}", MediaType.APPLICATION_JSON));

        client.createComment(1L, 1L, dto);
    }

    @Test
    void shouldHandleError() {
        server.expect(requestTo(URL + "/items/1"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withServerError());

        client.getItem(1L);
    }
}
