package ua.dudka.service.booking;

import ua.dudka.web.booking.dto.CreateBookingRequestDTO;

/**
 * @author Rostislav Dudka
 */
public interface BookingRequestCreator {
    void create(CreateBookingRequestDTO requestDTO);
}