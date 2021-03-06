package ua.dudka.repository.booking;

import abstract_test.AbstractRepositoryTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.dudka.domain.booking.BookingRequest;
import ua.dudka.domain.booking.BookingRequest.PaymentType;
import ua.dudka.domain.booking.Customer;
import ua.dudka.domain.booking.HotelRoomPreferences;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.junit.Assert.*;
import static ua.dudka.domain.HotelRoomType.ONE_ROOM;
import static ua.dudka.domain.booking.BookingRequest.RequestStatus;
import static ua.dudka.domain.booking.BookingRequest.RequestStatus.*;
import static ua.dudka.domain.booking.BookingRequest.builder;

/**
 * @author Rostislav Dudka
 */
public class BookingRequestRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private BookingRequestRepository repository;

    private BookingRequest testBookingRequest;

    private BookingRequest approvedBookingRequest;

    private BookingRequest deniedBookingRequest;

    @Before
    public void setUp() throws Exception {
        tearDown();
        setUpRequests();
        saveRequestsToRepository();
    }

    private void setUpRequests() {
        testBookingRequest = buildRequestWithStatus(CREATED);
        approvedBookingRequest = buildRequestWithStatus(APPROVED);
        deniedBookingRequest = buildRequestWithStatus(DENIED);
    }

    private BookingRequest buildRequestWithStatus(RequestStatus status) {
        Customer customer = new Customer("customerName", "customerSurname", "0500000000");
        LocalDate arriveDate = LocalDate.now();
        LocalDate departureDate = arriveDate.plusDays(2);
        HotelRoomPreferences preferences = new HotelRoomPreferences(ONE_ROOM, 2, ONE, TEN);
        int personAmount = 2;
        PaymentType paymentType = PaymentType.CASH;
        return builder()
                .customer(customer)
                .arriveDate(arriveDate)
                .departureDate(departureDate)
                .preferences(preferences)
                .personAmount(personAmount)
                .paymentType(paymentType)
                .status(status)
                .build();
    }

    private void saveRequestsToRepository() {
        testBookingRequest = repository.save(testBookingRequest);
        approvedBookingRequest = repository.save(approvedBookingRequest);
        deniedBookingRequest = repository.save(deniedBookingRequest);
    }

    @After
    public void tearDown() throws Exception {
        repository.deleteAll();
    }

    @Test
    public void bookingRequestShouldBeSaved() throws Exception {
        assertNotNull(testBookingRequest);
        assertNotNull(testBookingRequest.getId());
        assertNotNull(testBookingRequest.getCustomer());
        assertNotNull(testBookingRequest.getPreferences());
    }

    @Test
    public void findAllShouldReturnAllBookingRequest() throws Exception {
        List<BookingRequest> bookingRequests = repository.findAll();

        assertFalse(bookingRequests.isEmpty());
        assertEquals(testBookingRequest, bookingRequests.get(0));
    }

    @Test
    public void findByIdShouldReturnExistentRooms() throws Exception {
        Optional<BookingRequest> request = repository.findById(testBookingRequest.getId());

        assertTrue(request.isPresent());
        assertEquals(testBookingRequest, request.get());
    }

    @Test
    public void findByStatusCreatedShouldReturnOnlyCreatedRequests() throws Exception {
        List<BookingRequest> requests = repository.findByStatus(CREATED);

        assertTrue(requests.size() == 1);
        assertEquals(testBookingRequest, requests.get(0));
    }

    @Test
    public void findByStatusApprovedShouldReturnOnlyApprovedRequests() throws Exception {
        List<BookingRequest> requests = repository.findByStatus(APPROVED);

        assertTrue(requests.size() == 1);
        assertEquals(approvedBookingRequest, requests.get(0));
    }

    @Test
    public void findByStatusDeniedShouldReturnOnlyDeniedRequests() throws Exception {
        List<BookingRequest> requests = repository.findByStatus(DENIED);

        assertTrue(requests.size() == 1);
        assertEquals(deniedBookingRequest, requests.get(0));
    }

    @Test
    public void hotelRoomShouldBeUpdated() throws Exception {
        testBookingRequest.approve();

        BookingRequest updatedRequest = repository.save(testBookingRequest);

        assertTrue(updatedRequest.isApproved());
    }

    @Test
    public void hotelRoomShouldBeDeleted() throws Exception {
        repository.delete(testBookingRequest);

        assertFalse(repository.exists(testBookingRequest.getId()));
    }
}