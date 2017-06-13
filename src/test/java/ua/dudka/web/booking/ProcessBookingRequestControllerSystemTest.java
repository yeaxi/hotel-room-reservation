package ua.dudka.web.booking;

import abstract_test.AbstractSystemTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ua.dudka.domain.booking.BookingRequest;
import ua.dudka.repository.booking.BookingRequestRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.dudka.web.booking.ProcessBookingRequestController.Links.PROCESS_BOOKING_REQUEST_PAGE_URL;

/**
 * @author Rostislav Dudka
 */
public class ProcessBookingRequestControllerSystemTest extends AbstractSystemTest {

    private BookingRequest bookingRequest;

    @Autowired
    private BookingRequestRepository repository;

    @Before
    public void setUp() throws Exception {
        bookingRequest = repository.save(BookingRequest.builder().build());
    }

    @Test
    public void getPageShouldReturnOK() throws Exception {
        mockMvc.perform(get(PROCESS_BOOKING_REQUEST_PAGE_URL, bookingRequest.getId()).accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().contentType(RESPONSE_HTML_CONTENT_TYPE));
    }
}