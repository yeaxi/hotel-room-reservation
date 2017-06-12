package ua.dudka.web.booking;

import abstract_test.AbstractWebIntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import ua.dudka.domain.booking.BookingRequest;
import ua.dudka.repository.booking.BookingRequestRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ua.dudka.domain.booking.BookingRequest.RequestStatus.*;
import static ua.dudka.web.booking.DisplayBookingRequestsController.Links.BOOKING_REQUESTS_PAGE_URL;

/**
 * @author Rostislav Dudka
 */
@WebMvcTest(controllers = DisplayBookingRequestsController.class, secure = false)
public class DisplayBookingRequestsControllerIntegrationTest extends AbstractWebIntegrationTest {

    private List<BookingRequest> requests = new ArrayList<>();
    private List<BookingRequest> createdRequests = new ArrayList<>();
    private List<BookingRequest> approvedRequests = new ArrayList<>();
    private List<BookingRequest> deniedRequests = new ArrayList<>();

    @MockBean
    private BookingRequestRepository repository;

    @Before
    public void setUp() throws Exception {
        setUpRequests();
        setUpMockRepository();
    }

    private void setUpRequests() {
        BookingRequest createdRequest = BookingRequest.builder().status(CREATED).build();
        BookingRequest approvedRequest = BookingRequest.builder().status(APPROVED).build();
        BookingRequest deniedRequest = BookingRequest.builder().status(DENIED).build();

        requests.add(createdRequest);
        requests.add(approvedRequest);
        requests.add(deniedRequest);

        createdRequests.add(createdRequest);
        approvedRequests.add(approvedRequest);
        deniedRequests.add(deniedRequest);
    }

    private void setUpMockRepository() {
        when(repository.findAll()).thenReturn(requests);
        when(repository.findByStatus(CREATED)).thenReturn(createdRequests);
        when(repository.findByStatus(APPROVED)).thenReturn(approvedRequests);
        when(repository.findByStatus(DENIED)).thenReturn(deniedRequests);
    }

    @Test
    public void getPageShouldSetBookingRequestsToModel() throws Exception {
        mockMvc.perform(get(BOOKING_REQUESTS_PAGE_URL).accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().contentType(RESPONSE_HTML_CONTENT_TYPE))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attributeExists("requests"))
                .andExpect(model().attribute("requests", requests));
    }


    @Test
    public void getPageWithCreatedRequestsShouldSetCreatedRequestsModel() throws Exception {
        mockMvc.perform(get(BOOKING_REQUESTS_PAGE_URL)
                .param("status", CREATED.toString())
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().contentType(RESPONSE_HTML_CONTENT_TYPE))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attributeExists("requests"))
                .andExpect(model().attribute("requests", createdRequests));
    }

    @Test
    public void getPageWithApprovedRequestsShouldSetApprovedRequestsModel() throws Exception {
        mockMvc.perform(get(BOOKING_REQUESTS_PAGE_URL)
                .param("status", APPROVED.toString())
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().contentType(RESPONSE_HTML_CONTENT_TYPE))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attributeExists("requests"))
                .andExpect(model().attribute("requests", approvedRequests));
    }

    @Test
    public void getPageWithDeniedRequestsShouldSetDeniedRequestsToModel() throws Exception {
        mockMvc.perform(get(BOOKING_REQUESTS_PAGE_URL)
                .param("status", DENIED.toString())
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().contentType(RESPONSE_HTML_CONTENT_TYPE))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attributeExists("requests"))
                .andExpect(model().attribute("requests", deniedRequests));
    }
}