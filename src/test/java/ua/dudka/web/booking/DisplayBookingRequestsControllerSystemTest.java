package ua.dudka.web.booking;

import abstract_test.AbstractSystemTest;
import org.junit.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.dudka.web.booking.DisplayBookingRequestsController.Links.BOOKING_REQUESTS_PAGE_URL;
import static ua.dudka.web.room.HotelRoomController.Links.ROOM_PAGE_URL;

/**
 * @author Rostislav Dudka
 */
public class DisplayBookingRequestsControllerSystemTest extends AbstractSystemTest {

    @Test
    public void getPageShouldOK() throws Exception {
        mockMvc.perform(get(BOOKING_REQUESTS_PAGE_URL).accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().contentType(RESPONSE_HTML_CONTENT_TYPE));
    }
}