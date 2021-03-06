package ua.dudka.web.booking;

import abstract_test.AbstractSystemTest;
import org.junit.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.dudka.web.booking.CreateBookingRequestController.Links.CREATE_BOOKING_REQUEST_PAGE_URL;
import static ua.dudka.web.room.AddHotelRoomController.Links.ADD_ROOM_PAGE_URL;

/**
 * @author Rostislav Dudka
 */
public class CreateBookingRequestControllerSystemTest extends AbstractSystemTest {

    @Test
    public void getPageShouldReturnOK() throws Exception {
        mockMvc.perform(get(CREATE_BOOKING_REQUEST_PAGE_URL).accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().contentType(RESPONSE_HTML_CONTENT_TYPE));
    }
}