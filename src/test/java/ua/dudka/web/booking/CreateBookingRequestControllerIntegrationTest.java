package ua.dudka.web.booking;

import abstract_test.AbstractWebIntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import ua.dudka.domain.booking.Customer;
import ua.dudka.domain.booking.HotelRoomPreferences;
import ua.dudka.service.booking.BookingRequestCreator;
import ua.dudka.web.booking.dto.CreateBookingRequestDTO;

import java.time.LocalDate;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ua.dudka.domain.booking.BookingRequest.PaymentType;
import static ua.dudka.domain.HotelRoomType.ONE_ROOM;
import static ua.dudka.web.booking.CreateBookingRequestController.Links.CREATE_BOOKING_REQUEST_PAGE_URL;
import static ua.dudka.web.booking.CreateBookingRequestController.Links.CREATE_BOOKING_REQUEST_URL;

/**
 * @author Rostislav Dudka
 */
@WebMvcTest(controllers = CreateBookingRequestController.class, secure = false)
public class CreateBookingRequestControllerIntegrationTest extends AbstractWebIntegrationTest {

    private static CreateBookingRequestDTO request;

    @MockBean
    private BookingRequestCreator creator;

    @Before
    public void setUp() throws Exception {
        setUpRequests();
    }

    private void setUpRequests() {
        Customer customer = new Customer("name", "surname","0500000000");
        LocalDate arriveDate = LocalDate.now();
        LocalDate departureDate = arriveDate.plusDays(2);
        HotelRoomPreferences preferences = new HotelRoomPreferences(ONE_ROOM, 2, ONE, TEN);
        int personAmount = 2;
        PaymentType paymentType = PaymentType.CASH;
        request = CreateBookingRequestDTO
                .builder()
                .customer(customer)
                .arriveDate(arriveDate.toString())
                .departureDate(departureDate.toString())
                .preferences(preferences)
                .personAmount(personAmount)
                .paymentType(paymentType)
                .build();
    }


    @Test
    public void getPageShouldReturnOK() throws Exception {
        mockMvc.perform(get(CREATE_BOOKING_REQUEST_PAGE_URL).accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().contentType(RESPONSE_HTML_CONTENT_TYPE))
                .andExpect(model().hasNoErrors());
    }


    @Test
    public void createBookingRequestShouldOK() throws Exception {
        mockMvc.perform(post(CREATE_BOOKING_REQUEST_URL)
                .param("customer.name", request.getCustomer().getName())
                .param("customer.surname", request.getCustomer().getSurname())
                .param("customer.phoneNumber", request.getCustomer().getPhoneNumber())
                .param("arriveDate", request.getArriveDate())
                .param("departureDate", request.getDepartureDate())
                .param("preferences.roomType", request.getPreferences().getRoomType().toString())
                .param("preferences.hotelRoomAmount", request.getPreferences().getHotelRoomAmount() + "")
                .param("preferences.fromPrice", request.getPreferences().getFromPrice() + "")
                .param("preferences.toPrice", request.getPreferences().getToPrice() + "")
                .param("personAmount", request.getPersonAmount() + "")
                .param("paymentType", request.getPaymentType().toString())
                .accept(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(content().contentType(RESPONSE_HTML_CONTENT_TYPE))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attributeExists("success"));

        verify(creator).create(eq(request));
    }
}