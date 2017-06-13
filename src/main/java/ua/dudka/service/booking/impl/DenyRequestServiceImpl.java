package ua.dudka.service.booking.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.dudka.domain.booking.BookingRequest;
import ua.dudka.exception.booking.BookingRequestNotFoundException;
import ua.dudka.repository.booking.BookingRequestRepository;
import ua.dudka.service.booking.DenyRequestService;

/**
 * @author Rostislav Dudka
 */
@Service
@RequiredArgsConstructor
public class DenyRequestServiceImpl implements DenyRequestService {
    private final BookingRequestRepository requestRepository;

    @Override
    public void deny(String bookingRequestId) {
        BookingRequest request = getBookingRequest(bookingRequestId);
        request.deny();
        requestRepository.save(request);
    }

    private BookingRequest getBookingRequest(String bookingRequestId) {
        return requestRepository.findById(bookingRequestId).orElseThrow(() -> new BookingRequestNotFoundException(bookingRequestId));
    }
}