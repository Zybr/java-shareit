package ru.practicum.shareit.features.item.controller;

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
import ru.practicum.shareit.features.booking.model.Booking;
import ru.practicum.shareit.features.booking.model.BookingStatus;
import ru.practicum.shareit.features.item.dto.comment.CommentInpDto;
import ru.practicum.shareit.features.item.dto.item.ItemDto;
import ru.practicum.shareit.features.item.model.Item;
import ru.practicum.shareit.features.user.model.User;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ItemControllerTest extends DbTestCase {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetItems() throws Exception {
        User owner = factories().user().create();
        Item item = factories().item().create(
                Item.builder()
                        .owner(owner)
                        .build()
        );

        mvc.perform(
                        get("/items")
                                .header(CustomHeaders.USER_ID, owner.getId())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(item.getId()));
    }

    @Test
    void shouldSearchItems() throws Exception {
        User owner = factories().user().create();
        Item item = factories().item().create(
                Item.builder()
                        .owner(owner)
                        .name("SearchMe")
                        .build()
        );

        mvc.perform(
                        get("/items/search")
                                .header(CustomHeaders.USER_ID, owner.getId())
                                .param("text", "SearchMe")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(item.getId()));
    }

    @Test
    void shouldGetItem() throws Exception {
        Item item = factories().item().create();

        mvc.perform(
                        get("/items/" + item.getId())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(item.getId()));
    }

    @Test
    void shouldCreateItem() throws Exception {
        User owner = factories().user().create();
        ItemDto dto = factories().itemDto().make();
        dto.setId(null);

        mvc.perform(
                        post("/items")
                                .header(CustomHeaders.USER_ID, owner.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(dto.getName()));
    }

    @Test
    void shouldUpdateItem() throws Exception {
        User owner = factories().user().create();
        Item item = factories().item().create(
                Item.builder()
                        .owner(owner)
                        .build()
        );
        ItemDto updateDto = new ItemDto("Updated Name", "Updated Desc", false, null);

        mvc.perform(
                        patch("/items/" + item.getId())
                                .header(CustomHeaders.USER_ID, owner.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateDto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(item.getId()))
                .andExpect(jsonPath("$.name").value("Updated Name"));
    }

    @Test
    void shouldCreateComment() throws Exception {
        User author = factories().user().create();
        Item item = factories().item().create();

        // Need to have a past booking to allow commenting
        factories().booking().create(
                Booking.builder()
                        .item(item)
                        .booker(author)
                        .status(BookingStatus.APPROVED)
                        .start(LocalDateTime.now().minusDays(2))
                        .end(LocalDateTime.now().minusDays(1))
                        .build()
        );

        CommentInpDto inpDto = factories().commentInpDto().make(
                new CommentInpDto(null, "comment")
        );

        mvc.perform(
                        post("/items/" + item.getId() + "/comment")
                                .header(CustomHeaders.USER_ID, author.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(inpDto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.text").value(inpDto.getText()));
    }
}
