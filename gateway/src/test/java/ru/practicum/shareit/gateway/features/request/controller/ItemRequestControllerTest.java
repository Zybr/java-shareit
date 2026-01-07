package ru.practicum.shareit.gateway.features.request.controller;
import ru.practicum.shareit.features.request.controller.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.common.constant.CustomHeaders;
import ru.practicum.shareit.features.request.client.ItemRequestClient;
import ru.practicum.shareit.features.request.dto.ItemRequestCreationDto;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemRequestController.class)
public class ItemRequestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ItemRequestClient requestClient;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void shouldCreateRequest() throws Exception {
        ItemRequestCreationDto dto = new ItemRequestCreationDto("Description");
        when(requestClient.createRequest(anyLong(), any())).thenReturn(ResponseEntity.ok().build());

        mvc.perform(post("/requests")
                        .header(CustomHeaders.USER_ID, 1L)
                        .content(mapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotCreateRequestWithBlankDescription() throws Exception {
        ItemRequestCreationDto dto = new ItemRequestCreationDto("");

        mvc.perform(post("/requests")
                        .header(CustomHeaders.USER_ID, 1L)
                        .content(mapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetUserRequests() throws Exception {
        when(requestClient.getUserRequests(anyLong())).thenReturn(ResponseEntity.ok().build());

        mvc.perform(get("/requests")
                        .header(CustomHeaders.USER_ID, 1L))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetRequests() throws Exception {
        when(requestClient.getRequests()).thenReturn(ResponseEntity.ok().build());

        mvc.perform(get("/requests/all"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetRequest() throws Exception {
        when(requestClient.getRequest(anyLong())).thenReturn(ResponseEntity.ok().build());

        mvc.perform(get("/requests/1"))
                .andExpect(status().isOk());
    }
}
