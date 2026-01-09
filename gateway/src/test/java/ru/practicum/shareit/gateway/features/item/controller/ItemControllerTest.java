package ru.practicum.shareit.gateway.features.item.controller;
import ru.practicum.shareit.features.item.controller.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.common.constant.CustomHeaders;
import ru.practicum.shareit.features.item.client.ItemClient;
import ru.practicum.shareit.features.item.dto.CreateItemDto;
import ru.practicum.shareit.features.item.dto.CreateCommentDto;
import ru.practicum.shareit.features.item.dto.UpdateItemDto;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
public class ItemControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ItemClient itemClient;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void shouldCreateItem() throws Exception {
        CreateItemDto dto = new CreateItemDto("Name", "Description", true, null);
        when(itemClient.createItem(anyLong(), any())).thenReturn(ResponseEntity.ok().build());

        mvc.perform(post("/items")
                        .header(CustomHeaders.USER_ID, 1L)
                        .content(mapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotCreateItemWithBlankName() throws Exception {
        CreateItemDto dto = new CreateItemDto("", "Description", true, null);

        mvc.perform(post("/items")
                        .header(CustomHeaders.USER_ID, 1L)
                        .content(mapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdateItem() throws Exception {
        UpdateItemDto dto = new UpdateItemDto("New Name", null, null);
        when(itemClient.updateItem(anyLong(), anyLong(), any())).thenReturn(ResponseEntity.ok().build());

        mvc.perform(patch("/items/1")
                        .header(CustomHeaders.USER_ID, 1L)
                        .content(mapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldCreateComment() throws Exception {
        CreateCommentDto dto = new CreateCommentDto("Comment");
        when(itemClient.createComment(anyLong(), anyLong(), any())).thenReturn(ResponseEntity.ok().build());

        mvc.perform(post("/items/1/comment")
                        .header(CustomHeaders.USER_ID, 1L)
                        .content(mapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotCreateCommentWithBlankText() throws Exception {
        CreateCommentDto dto = new CreateCommentDto("");

        mvc.perform(post("/items/1/comment")
                        .header(CustomHeaders.USER_ID, 1L)
                        .content(mapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetItems() throws Exception {
        when(itemClient.getItems(anyLong())).thenReturn(ResponseEntity.ok().build());

        mvc.perform(get("/items")
                        .header(CustomHeaders.USER_ID, 1L))
                .andExpect(status().isOk());
    }

    @Test
    void shouldSearchItems() throws Exception {
        when(itemClient.searchItems(anyLong(), anyString())).thenReturn(ResponseEntity.ok().build());

        mvc.perform(get("/items/search")
                        .header(CustomHeaders.USER_ID, 1L)
                        .param("text", "search"))
                .andExpect(status().isOk());
    }
}
