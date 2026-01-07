package ru.practicum.shareit.gateway.features.user.client;
import ru.practicum.shareit.features.user.client.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.client.MockRestServiceServer;
import ru.practicum.shareit.features.user.dto.CreateUserDto;
import ru.practicum.shareit.features.user.dto.UpdateUserDto;

import java.util.Map;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(UserClient.class)
@TestPropertySource(properties = "shareit-server.url=" + UserClientTest.URL)
public class UserClientTest {
    public static final String URL = "http://shareit-server";

    @Autowired
    private UserClient client;

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void shouldGetUsers() {
        server.expect(requestTo(URL + "/users"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("[]", MediaType.APPLICATION_JSON));

        client.getUsers();
    }

    @Test
    void shouldGetUser() {
        server.expect(requestTo(URL + "/users/1"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{}", MediaType.APPLICATION_JSON));

        client.getUser(1L);
    }

    @Test
    void shouldCreateUser() throws Exception {
        CreateUserDto dto = new CreateUserDto("Name", "email@mail.com");
        String json = mapper.writeValueAsString(Map.of(
                "name", "Name",
                "email", "email@mail.com"
        ));

        server.expect(requestTo(URL + "/users"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().json(json))
                .andRespond(withSuccess("{}", MediaType.APPLICATION_JSON));

        client.createUser(dto);
    }

    @Test
    void shouldUpdateUser() throws Exception {
        UpdateUserDto dto = new UpdateUserDto("New Name", "new@mail.com");
        String json = mapper.writeValueAsString(Map.of(
                "name", "New Name",
                "email", "new@mail.com"
        ));

        server.expect(requestTo(URL + "/users/1"))
                .andExpect(method(HttpMethod.PATCH))
                .andExpect(content().json(json))
                .andRespond(withSuccess("{}", MediaType.APPLICATION_JSON));

        client.updateUser(1L, dto);
    }

    @Test
    void shouldDeleteUser() {
        server.expect(requestTo(URL + "/users/1"))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withSuccess());

        client.deleteUser(1L);
    }
}
