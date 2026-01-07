package ru.practicum.shareit.gateway.features.booking.controller;
import ru.practicum.shareit.features.booking.controller.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.common.constant.CustomHeaders;
import ru.practicum.shareit.common.handler.ErrorHandler;
import ru.practicum.shareit.features.booking.client.BookingClient;
import ru.practicum.shareit.features.booking.dto.CreateBookingDto;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({BookingController.class, ErrorHandler.class})
public class BookingControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookingClient bookingClient;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void shouldCreateBooking() throws Exception {
        CreateBookingDto dto = new CreateBookingDto(1L, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));
        when(bookingClient.createBooking(anyLong(), any())).thenReturn(ResponseEntity.ok().build());

        mvc.perform(post("/bookings")
                        .header(CustomHeaders.USER_ID, 1L)
                        .content(mapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotCreateBookingWithNullItemId() throws Exception {
        CreateBookingDto dto = new CreateBookingDto(null, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));

        mvc.perform(post("/bookings")
                        .header(CustomHeaders.USER_ID, 1L)
                        .content(mapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotCreateBookingWithPastEnd() throws Exception {
        CreateBookingDto dto = new CreateBookingDto(1L, LocalDateTime.now().plusDays(1), LocalDateTime.now().minusDays(1));

        mvc.perform(post("/bookings")
                        .header(CustomHeaders.USER_ID, 1L)
                        .content(mapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetBookings() throws Exception {
        when(bookingClient.getBookings(anyLong(), any(), anyInt(), anyInt())).thenReturn(ResponseEntity.ok().build());

        mvc.perform(get("/bookings")
                        .header(CustomHeaders.USER_ID, 1L)
                        .param("state", "ALL")
                        .param("from", "0")
                        .param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotGetBookingsWithInvalidState() throws Exception {
        mvc.perform(get("/bookings")
                        .header(CustomHeaders.USER_ID, 1L)
                        .param("state", "INVALID"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetOwnerBookings() throws Exception {
        when(bookingClient.getOwnerBookings(anyLong(), any(), anyInt(), anyInt())).thenReturn(ResponseEntity.ok().build());

        mvc.perform(get("/bookings/owner")
                        .header(CustomHeaders.USER_ID, 1L)
                        .param("state", "ALL"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldConfirmBooking() throws Exception {
        when(bookingClient.confirmBooking(anyLong(), anyLong(), anyBoolean())).thenReturn(ResponseEntity.ok().build());

        mvc.perform(patch("/bookings/1")
                        .header(CustomHeaders.USER_ID, 1L)
                        .param("approved", "true"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetBooking() throws Exception {
        when(bookingClient.getBooking(anyLong(), anyLong())).thenReturn(ResponseEntity.ok().build());

        mvc.perform(get("/bookings/1")
                        .header(CustomHeaders.USER_ID, 1L))
                .andExpect(status().isOk());
    }
}
