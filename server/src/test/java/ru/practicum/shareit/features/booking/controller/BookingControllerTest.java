package ru.practicum.shareit.features.booking.controller;

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
import ru.practicum.shareit.features.booking.dto.BookingInpDto;
import ru.practicum.shareit.features.booking.model.Booking;
import ru.practicum.shareit.features.booking.model.BookingStatus;
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
public class BookingControllerTest extends DbTestCase {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetUserBooking() throws Exception {
        User booker = factories().user().create();
        Booking booking = factories().booking().create(
                Booking.builder()
                        .booker(booker)
                        .build()
        );

        mvc.perform(
                        get("/bookings/" + booking.getId())
                                .header(CustomHeaders.USER_ID, booker.getId())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(booking.getId()));
    }

    @Test
    void shouldGetBookerBookings() throws Exception {
        User booker = factories().user().create();
        Booking booking = factories().booking().create(
                Booking.builder()
                        .booker(booker)
                        .build()
        );

        mvc.perform(
                        get("/bookings")
                                .header(CustomHeaders.USER_ID, booker.getId())
                                .param("state", "ALL")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(booking.getId()));
    }

    @Test
    void shouldGetItemOwnerBookings() throws Exception {
        User owner = factories().user().create();
        Item item = factories().item().create(
                Item.builder()
                        .owner(owner)
                        .build()
        );
        Booking booking = factories().booking().create(
                Booking.builder()
                        .item(item)
                        .build()
        );

        mvc.perform(
                        get("/bookings/owner")
                                .header(CustomHeaders.USER_ID, owner.getId())
                                .param("state", "ALL")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(booking.getId()));
    }

    @Test
    void shouldCreateBooking() throws Exception {
        User booker = factories().user().create();
        Item item = factories().item().create(
                Item.builder()
                        .available(true)
                        .build()
        );

        BookingInpDto inpDto = factories().bookingInpDto().make(
                new BookingInpDto(
                        LocalDateTime.now().plusDays(1),
                        LocalDateTime.now().plusDays(2),
                        item.getId()
                )
        );

        mvc.perform(
                        post("/bookings")
                                .header(CustomHeaders.USER_ID, booker.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(inpDto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.status").value(BookingStatus.WAITING.name()));
    }

    @Test
    void shouldConfirmBooking() throws Exception {
        User owner = factories().user().create();
        Item item = factories().item().create(
                Item.builder()
                        .owner(owner)
                        .build()
        );
        Booking booking = factories().booking().create(
                Booking.builder()
                        .item(item)
                        .status(BookingStatus.WAITING)
                        .build()
        );

        mvc.perform(
                        patch("/bookings/" + booking.getId())
                                .header(CustomHeaders.USER_ID, owner.getId())
                                .param("approved", "true")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(booking.getId()))
                .andExpect(jsonPath("$.status").value(BookingStatus.APPROVED.name()));
    }
}
