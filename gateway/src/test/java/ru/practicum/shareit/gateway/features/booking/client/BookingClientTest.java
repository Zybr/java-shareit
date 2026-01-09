package ru.practicum.shareit.gateway.features.booking.client;
import ru.practicum.shareit.features.booking.client.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.client.MockRestServiceServer;
import ru.practicum.shareit.features.booking.dto.BookingState;
import ru.practicum.shareit.features.booking.dto.CreateBookingDto;

import java.time.LocalDateTime;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(BookingClient.class)
@TestPropertySource(properties = "shareit-server.url=" + BookingClientTest.URL)
public class BookingClientTest {
    public static final String URL = "http://shareit-server";

    @Autowired
    private BookingClient client;

    @Autowired
    private MockRestServiceServer server;

    @Test
    void shouldGetBookings() {
        server.expect(requestTo(URL + "/bookings?state=ALL&from=0&size=10"))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header("X-Sharer-User-Id", "1"))
                .andRespond(withSuccess("[]", MediaType.APPLICATION_JSON));

        client.getBookings(1L, BookingState.ALL, 0, 10);
    }

    @Test
    void shouldGetOwnerBookings() {
        server.expect(requestTo(URL + "/bookings/owner?state=ALL&from=0&size=10"))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header("X-Sharer-User-Id", "1"))
                .andRespond(withSuccess("[]", MediaType.APPLICATION_JSON));

        client.getOwnerBookings(1L, BookingState.ALL, 0, 10);
    }

    @Test
    void shouldGetBooking() {
        server.expect(requestTo(URL + "/bookings/1"))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header("X-Sharer-User-Id", "1"))
                .andRespond(withSuccess("{}", MediaType.APPLICATION_JSON));

        client.getBooking(1L, 1L);
    }

    @Test
    void shouldCreateBooking() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(2);
        CreateBookingDto dto = new CreateBookingDto(1L, start, end);
        server.expect(requestTo(URL + "/bookings"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(header("X-Sharer-User-Id", "1"))
                .andRespond(withSuccess("{}", MediaType.APPLICATION_JSON));

        client.createBooking(1L, dto);
    }

    @Test
    void shouldConfirmBooking() {
        server.expect(requestTo(URL + "/bookings/1?approved=true"))
                .andExpect(method(HttpMethod.PATCH))
                .andExpect(header("X-Sharer-User-Id", "1"))
                .andRespond(withSuccess("{}", MediaType.APPLICATION_JSON));

        client.confirmBooking(1L, 1L, true);
    }
}
