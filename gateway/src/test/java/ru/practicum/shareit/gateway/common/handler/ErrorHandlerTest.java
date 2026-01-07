package ru.practicum.shareit.gateway.common.handler;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.common.handler.ErrorHandler;
import ru.practicum.shareit.features.user.client.UserClient;
import ru.practicum.shareit.features.user.controller.UserController;

import java.util.HashSet;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({ErrorHandler.class, UserController.class})
public class ErrorHandlerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserClient userClient;

    @Test
    void shouldHandleIllegalArgumentException() throws Exception {
        when(userClient.getUsers()).thenThrow(new IllegalArgumentException("Invalid argument"));

        mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid argument"));
    }

    @Test
    void shouldHandleConstraintViolationException() throws Exception {
        when(userClient.getUsers()).thenThrow(new ConstraintViolationException("Violation", new HashSet<>()));

        mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Violation"));
    }

    @Test
    void shouldHandleThrowable() throws Exception {
        when(userClient.getUsers()).thenThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("Internal Server Error: Unexpected error"));
    }
}
