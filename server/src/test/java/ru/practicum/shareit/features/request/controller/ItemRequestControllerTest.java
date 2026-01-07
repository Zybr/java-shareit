package ru.practicum.shareit.features.request.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.common.constants.CustomHeaders;
import ru.practicum.shareit.database.DbTestCase;
import ru.practicum.shareit.features.request.dto.ItemRequestCreationDto;
import ru.practicum.shareit.features.request.model.ItemRequest;
import ru.practicum.shareit.features.user.model.User;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ItemRequestControllerTest extends DbTestCase {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    void shouldCreateRequest() throws Exception {
        User requester = factories().user().create();
        ItemRequestCreationDto creationDto = factories().itemRequestCreationDto().make();

        mvc.perform(
                        post("/requests")
                                .header(CustomHeaders.USER_ID, requester.getId())
                                .content(mapper.writeValueAsString(creationDto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.description").value(creationDto.getDescription()));
    }

    @Test
    void shouldGetUserRequests() throws Exception {
        User requester = factories().user().create();
        ItemRequest request = factories().itemRequest().create(
                ItemRequest.builder()
                        .requester(requester)
                        .build()
        );

        mvc.perform(
                        get("/requests")
                                .header(CustomHeaders.USER_ID, requester.getId())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(request.getId()));
    }

    @Test
    void shouldGetAllRequestsFromOthers() throws Exception {
        factories().itemRequest().create();

        mvc.perform(
                        get("/requests/all")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void shouldGetRequestById() throws Exception {
        ItemRequest request = factories().itemRequest().create();

        mvc.perform(
                        get("/requests/" + request.getId())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(request.getId()));
    }
}
