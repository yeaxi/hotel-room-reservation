package ua.dudka.service.booking;

import ua.dudka.web.booking.dto.BookingRequestWithRooms;

/**
 * @author Rostislav Dudka
 */
public interface FindRoomsByBookingRequestService {
    BookingRequestWithRooms find(String bookingRequestId);
}
