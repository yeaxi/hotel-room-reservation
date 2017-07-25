package ua.dudka.service.booking;

import org.junit.BeforeClass;
import org.junit.Test;
import ua.dudka.domain.booking.BookingRequest;
import ua.dudka.domain.booking.Customer;
import ua.dudka.domain.booking.HotelRoomPreferences;
import ua.dudka.repository.booking.BookingRequestRepository;
import ua.dudka.service.booking.BookingRequestCreator;
import ua.dudka.service.booking.impl.BookingRequestCreatorImpl;
import ua.dudka.web.booking.dto.CreateBookingRequestDTO;

import java.time.LocalDate;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static ua.dudka.domain.booking.BookingRequest.PaymentType;
import static ua.dudka.domain.HotelRoomType.ONE_ROOM;

/**
 * @author Rostislav Dudka
 */
public class BookingRequestCreatorTest {

    private static CreateBookingRequestDTO createBookingRequestDTO;

    private static BookingRequestRepository repository;
    private static BookingRequestCreator creator;

    @BeforeClass
    public static void setUp() throws Exception {
        setUpRequest();
        setUpMockRepository();
        setUpBookingRequestCreator();
    }

    private static void setUpBookingRequestCreator() {
        creator = new BookingRequestCreatorImpl(repository);
    }

    private static void setUpRequest() {
        Customer customer = new Customer("customerName", "customerSurname","0500000000");
        LocalDate arriveDate = LocalDate.now();
        LocalDate departureDate = arriveDate.plusDays(2);
        HotelRoomPreferences preferences = new HotelRoomPreferences(ONE_ROOM, 2, ONE, TEN);
        int personAmount = 2;
        PaymentType paymentType = PaymentType.CASH;
        createBookingRequestDTO = CreateBookingRequestDTO
                .builder()
                .customer(customer)
                .arriveDate(arriveDate.toString())
                .departureDate(departureDate.toString())
                .preferences(preferences)
                .personAmount(personAmount)
                .paymentType(paymentType)
                .build();
    }

    private static void setUpMockRepository() {
        repository = mock(BookingRequestRepository.class);
    }

    @Test
    public void createShouldSaveNewBookingRequestToRepository() throws Exception {
        creator.create(createBookingRequestDTO);

        BookingRequest expectedRequest = buildExpectedBookingRequest();
        verify(repository).save(eq(expectedRequest));
    }

    private BookingRequest buildExpectedBookingRequest() {
        return BookingRequest.builder()
                .customer(createBookingRequestDTO.getCustomer())
                .arriveDate(createBookingRequestDTO.getArriveLocalDate())
                .departureDate(createBookingRequestDTO.getDepartureLocalDate())
                .preferences(createBookingRequestDTO.getPreferences())
                .personAmount(createBookingRequestDTO.getPersonAmount())
                .paymentType(createBookingRequestDTO.getPaymentType())
                .build();
    }
}