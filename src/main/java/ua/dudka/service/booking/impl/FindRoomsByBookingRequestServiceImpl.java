package ua.dudka.service.booking.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.dudka.domain.booking.BookingRequest;
import ua.dudka.domain.booking.HotelRoomPreferences;
import ua.dudka.domain.room.HotelRoom;
import ua.dudka.exception.booking.BookingRequestNotFoundException;
import ua.dudka.repository.booking.BookingRequestRepository;
import ua.dudka.repository.room.HotelRoomRepository;
import ua.dudka.service.booking.FindRoomsByBookingRequestService;
import ua.dudka.web.booking.dto.BookingRequestWithRooms;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Rostislav Dudka
 */
@Service
@RequiredArgsConstructor
public class FindRoomsByBookingRequestServiceImpl implements FindRoomsByBookingRequestService {
    private final HotelRoomRepository hotelRoomRepository;
    private final BookingRequestRepository bookingRequestRepository;

    @Override
    public BookingRequestWithRooms find(String bookingRequestId) {
        List<HotelRoom> freeRooms = findFreeHotelRooms();
        BookingRequest request = getBookingRequestOrThrowException(bookingRequestId);

        List<HotelRoom> availableRooms = findAvailableRooms(freeRooms, request);

        return new BookingRequestWithRooms(request, availableRooms);
    }

    private List<HotelRoom> findAvailableRooms(List<HotelRoom> freeRooms, BookingRequest request) {
        HotelRoomPreferences preferences = request.getPreferences();

        return freeRooms.stream()
                .filter(filterByRoomType(preferences))
                .filter(filterByPrice(preferences))
                .collect(Collectors.toList());
    }

    private Predicate<HotelRoom> filterByRoomType(HotelRoomPreferences preferences) {
        return r -> r.getRoomAmount() == preferences.getRoomType().getRoomAmount();
    }

    private Predicate<HotelRoom> filterByPrice(HotelRoomPreferences preferences) {
        return r -> {
            boolean priceIsGtOrEqToMinPrice = r.getPrice().compareTo(preferences.getFromPrice()) > -1;
            boolean priceIsLtOrEqualToMaxPrice = r.getPrice().compareTo(preferences.getToPrice()) < 1;
            boolean isPriceBetweenMinAndMax = priceIsGtOrEqToMinPrice && priceIsLtOrEqualToMaxPrice;
            return isPriceBetweenMinAndMax;
        };
    }

    private List<HotelRoom> findFreeHotelRooms() {
        return hotelRoomRepository.findByStatus(HotelRoom.Status.FREE);
    }

    private BookingRequest getBookingRequestOrThrowException(String id) {
        return bookingRequestRepository.findById(id)
                .orElseThrow(() -> new BookingRequestNotFoundException(id));
    }
}