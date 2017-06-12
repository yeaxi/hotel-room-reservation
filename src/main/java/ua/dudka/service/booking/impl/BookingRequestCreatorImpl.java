package ua.dudka.service.booking.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.dudka.domain.booking.BookingRequest;
import ua.dudka.repository.booking.BookingRequestRepository;
import ua.dudka.service.booking.BookingRequestCreator;
import ua.dudka.web.booking.dto.CreateBookingRequestDTO;

/**
 * @author Rostislav Dudka
 */
@Service
@RequiredArgsConstructor
public class BookingRequestCreatorImpl implements BookingRequestCreator {

    private final BookingRequestRepository repository;

    @Override
    public void create(CreateBookingRequestDTO requestDTO) {
        BookingRequest bookingRequest = buildBookingRequestFrom(requestDTO);
        saveRequest(bookingRequest);
    }

    private BookingRequest buildBookingRequestFrom(CreateBookingRequestDTO requestDTO) {
        return BookingRequest.builder()
                .customer(requestDTO.getCustomer())
                .arriveDate(requestDTO.getArriveLocalDate())
                .departureDate(requestDTO.getDepartureLocalDate())
                .preferences(requestDTO.getPreferences())
                .personAmount(requestDTO.getPersonAmount())
                .paymentType(requestDTO.getPaymentType())
                .build();
    }

    private void saveRequest(BookingRequest bookingRequest) {
        repository.save(bookingRequest);
    }
}