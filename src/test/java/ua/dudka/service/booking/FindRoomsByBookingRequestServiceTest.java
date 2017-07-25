package ua.dudka.service.booking;

import org.junit.Before;
import org.junit.Test;
import ua.dudka.domain.HotelRoomType;
import ua.dudka.domain.booking.BookingRequest;
import ua.dudka.domain.booking.BookingRequest.PaymentType;
import ua.dudka.domain.booking.Customer;
import ua.dudka.domain.booking.HotelRoomPreferences;
import ua.dudka.domain.room.HotelRoom;
import ua.dudka.exception.booking.BookingRequestNotFoundException;
import ua.dudka.repository.booking.BookingRequestRepository;
import ua.dudka.repository.room.HotelRoomRepository;
import ua.dudka.service.booking.impl.FindRoomsByBookingRequestServiceImpl;
import ua.dudka.web.booking.dto.BookingRequestWithRooms;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ua.dudka.domain.HotelRoomType.ONE_ROOM;
import static ua.dudka.domain.HotelRoomType.TWO_ROOM;
import static ua.dudka.domain.room.HotelRoom.Status.FREE;

/**
 * @author Rostislav Dudka
 */
public class FindRoomsByBookingRequestServiceTest {

    private static final String BOOKING_REQUEST_ID = "101";
    private static final String NONEXISTENT_REQUEST_ID = "404";

    private static List<HotelRoom> allRooms = new ArrayList<>();
    private static List<HotelRoom> availableRooms = new ArrayList<>();

    private static BookingRequest bookingRequest;

    private static HotelRoomRepository hotelRoomRepository;
    private static BookingRequestRepository bookingRequestRepository;

    private static FindRoomsByBookingRequestService finder;

    @Before
    public void setUp() throws Exception {
        setUpRooms();
        setUpBookingRequest();
        setUpMockRepositories();
        setUpService();
    }

    private void setUpRooms() {
        allRooms.add(new HotelRoom(1, 1, BigDecimal.valueOf(1000), ""));
        allRooms.add(new HotelRoom(2, 2, BigDecimal.valueOf(2400), ""));
        HotelRoom first = new HotelRoom(3, 2, BigDecimal.valueOf(2600), "");
        HotelRoom second = new HotelRoom(4, 2, BigDecimal.valueOf(3400), "");
        HotelRoom third = new HotelRoom(5, 2, BigDecimal.valueOf(3800), "");
        allRooms.add(first);
        allRooms.add(second);
        allRooms.add(third);
        availableRooms.add(first);
        availableRooms.add(second);
        availableRooms.add(third);
    }

    private void setUpBookingRequest() {
        Customer customer = new Customer("name", "surname", "0500000000");
        LocalDate arriveDate = LocalDate.now();
        LocalDate departureDate = arriveDate.plusDays(2);
        HotelRoomPreferences preferences = getHotelRoomPreferences();

        int personAmount = 2;
        PaymentType paymentType = PaymentType.CASH;
        bookingRequest = BookingRequest
                .builder()
                .customer(customer)
                .arriveDate(arriveDate)
                .departureDate(departureDate)
                .preferences(preferences)
                .personAmount(personAmount)
                .paymentType(paymentType)
                .build();
    }

    private HotelRoomPreferences getHotelRoomPreferences() {
        HotelRoomType roomType = TWO_ROOM;
        int hotelRoomAmount = 2;
        BigDecimal fromPrice = BigDecimal.valueOf(2500);
        BigDecimal toPrice = BigDecimal.valueOf(4000);
        return new HotelRoomPreferences(roomType, hotelRoomAmount, fromPrice, toPrice);
    }

    private void setUpMockRepositories() {
        hotelRoomRepository = mock(HotelRoomRepository.class);
        when(hotelRoomRepository.findByStatus(FREE)).thenReturn(allRooms);

        bookingRequestRepository = mock(BookingRequestRepository.class);
        when(bookingRequestRepository.findById(eq(BOOKING_REQUEST_ID))).thenReturn(Optional.of(bookingRequest));
        when(bookingRequestRepository.findById(eq(NONEXISTENT_REQUEST_ID))).thenReturn(Optional.empty());
    }

    private void setUpService() {
        finder = new FindRoomsByBookingRequestServiceImpl(hotelRoomRepository, bookingRequestRepository);
    }

    @Test
    public void findShouldReturnAvailableRoomsByRequest() throws Exception {
        BookingRequestWithRooms requestWithRooms = finder.find(BOOKING_REQUEST_ID);

        assertEquals(bookingRequest, requestWithRooms.getRequest());
        assertEquals(availableRooms, requestWithRooms.getAvailableRooms());
    }

    @Test(expected = BookingRequestNotFoundException.class)
    public void findByNonexistentRequestIdShouldThrowException() throws Exception {
        finder.find(NONEXISTENT_REQUEST_ID);
    }
}