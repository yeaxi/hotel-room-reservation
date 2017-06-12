package ua.dudka.web.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.dudka.domain.booking.Customer;
import ua.dudka.domain.booking.HotelRoomPreferences;

import java.time.LocalDate;

import static lombok.AccessLevel.PACKAGE;
import static ua.dudka.domain.booking.BookingRequest.PaymentType;

/**
 * @author Rostislav Dudka
 */
@NoArgsConstructor
@AllArgsConstructor(access = PACKAGE)
@Data
@Builder
public class CreateBookingRequestDTO {

    private Customer customer;

    private String arriveDate;
    private String departureDate;

    private HotelRoomPreferences preferences;

    private int personAmount;

    private PaymentType paymentType;

    public LocalDate getArriveLocalDate() {
        return LocalDate.parse(arriveDate);
    }

    public LocalDate getDepartureLocalDate() {
        return LocalDate.parse(departureDate);
    }

}