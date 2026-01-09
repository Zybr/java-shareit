package ru.practicum.shareit.features.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.database.DbTestCase;
import ru.practicum.shareit.features.user.dto.UserDto;
import ru.practicum.shareit.features.user.model.User;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest extends DbTestCase {
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private MockMvc mvc;

    @Test
    public void shouldGetUsers() throws Exception {
        User user = factories().user().create();

        mvc.perform(
                        get("/users")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(user.getId()))
                .andExpect(jsonPath("$[0].name").value(user.getName()))
                .andExpect(jsonPath("$[0].email").value(user.getEmail()));
    }

    @Test
    public void shouldGetUser() throws Exception {
        User user = factories().user().create();

        mvc.perform(
                        get("/users/" + user.getId())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.email").value(user.getEmail()));
    }

    @Test
    public void shouldCreateUser() throws Exception {
        UserDto dto = factories().userDto().make();
        dto.setId(null);

        mvc.perform(
                        post("/users")
                                .content(mapper.writeValueAsString(dto))
                                .characterEncoding(StandardCharsets.UTF_8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(dto.getName()))
                .andExpect(jsonPath("$.email").value(dto.getEmail()));
    }

    @Test
    public void shouldUpdateUser() throws Exception {
        User user = factories().user().create();
        UserDto updateDto = new UserDto("Updated Name", "updated@email.com");

        mvc.perform(
                        patch("/users/" + user.getId())
                                .content(mapper.writeValueAsString(updateDto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.name").value(updateDto.getName()))
                .andExpect(jsonPath("$.email").value(updateDto.getEmail()));
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        User user = factories().user().create();

        mvc.perform(delete("/users/" + user.getId()))
                .andExpect(status().isOk());

        assert !factories().repositories().user().existsById(user.getId());
    }
}
