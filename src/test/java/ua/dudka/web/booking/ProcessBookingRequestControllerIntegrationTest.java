package ua.dudka.web.booking;

import abstract_test.AbstractWebIntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import ua.dudka.domain.booking.BookingRequest;
import ua.dudka.domain.room.HotelRoom;
import ua.dudka.service.booking.DenyRequestService;
import ua.dudka.service.booking.FindRoomsByBookingRequestService;
import ua.dudka.web.booking.dto.BookingRequestWithRooms;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ua.dudka.web.booking.ProcessBookingRequestController.Links.*;

/**
 * @author Rostislav Dudka
 */
@WebMvcTest(controllers = ProcessBookingRequestController.class, secure = false)
public class ProcessBookingRequestControllerIntegrationTest extends AbstractWebIntegrationTest {

    private static final String BOOKING_REQUEST_ID = "101";
    private static final String HOTEL_ROOM_ID = "107";
    private BookingRequestWithRooms bookingRequestWithRooms;
    private BookingRequest testBookingRequest;
    private List<HotelRoom> availableRooms = new ArrayList<>();

    @MockBean
    private FindRoomsByBookingRequestService finder;

    @MockBean
    private DenyRequestService processor;

    @Before
    public void setUp() throws Exception {
        setUpTestData();
        setUpMocks();
    }

    private void setUpTestData() {
        testBookingRequest = BookingRequest.builder().id(BOOKING_REQUEST_ID).build();

        int number = 200, roomAmount = 2;
        BigDecimal price = BigDecimal.valueOf(1000);
        String description = "";
        HotelRoom hotelRoom = new HotelRoom(number, roomAmount, price, description);
        availableRooms.add(hotelRoom);

        bookingRequestWithRooms = new BookingRequestWithRooms(testBookingRequest, availableRooms);
    }

    private void setUpMocks() {
        when(finder.find(eq(BOOKING_REQUEST_ID))).thenReturn(bookingRequestWithRooms);
    }

    @Test
    public void getPageShouldReturnSetDataToModel() throws Exception {
        mockMvc.perform(get(PROCESS_BOOKING_REQUEST_PAGE_URL, BOOKING_REQUEST_ID)
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().contentType(RESPONSE_HTML_CONTENT_TYPE))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attribute("bookingRequestWithRooms", bookingRequestWithRooms));
    }

    @Test
    public void denyShouldCallMethodAndReturnSuccessMessage() throws Exception {
        mockMvc.perform(post(DENY_BOOKING_REQUEST_URL)
                .param("requestId", BOOKING_REQUEST_ID)
                .accept(MediaType.ALL))
                .andExpect(content().contentType(RESPONSE_HTML_CONTENT_TYPE))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attributeExists("successDeny"));

        verify(processor).deny(eq(BOOKING_REQUEST_ID));
    }
}