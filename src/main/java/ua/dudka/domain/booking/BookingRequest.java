package ua.dudka.domain.booking;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PACKAGE;

/**
 * @author Rostislav Dudka
 */
@NoArgsConstructor
@AllArgsConstructor(access = PACKAGE)
@Data
@Builder
@EqualsAndHashCode(exclude = "creationDate")
public class BookingRequest {

    @Id
    private String id;

    private Customer customer;

    private LocalDateTime creationDate;
    private LocalDate arriveDate;

    private long tenure;

    private LocalDate departureDate;

    private HotelRoomPreferences preferences;

    private int personAmount;

    private PaymentType paymentType;

    private RequestStatus status;

    public void approve() {
        this.status = RequestStatus.APPROVED;
    }

    public boolean isApproved() {
        return this.status.equals(RequestStatus.APPROVED);
    }

    public void deny() {
        this.status = RequestStatus.DENIED;
    }

    public enum PaymentType {
        CASH, CREDIT_CARD
    }

    public enum RequestStatus {
        CREATED, APPROVED, DENIED
    }


    public static class BookingRequestBuilder {
        private Customer customer = new Customer();

        private LocalDate arriveDate = LocalDate.now();

        private LocalDate departureDate = LocalDate.now();

        private HotelRoomPreferences preferences = new HotelRoomPreferences();

        private PaymentType paymentType = PaymentType.CASH;

        private RequestStatus status = RequestStatus.CREATED;

        public BookingRequest build() {
            long tenure = createTenure();
            LocalDateTime creationDate = LocalDateTime.now();

            return new BookingRequest(null, this.customer, creationDate,
                    this.arriveDate, tenure, this.departureDate,
                    this.preferences, this.personAmount, this.paymentType, this.status);
        }
        private long createTenure() {
            //convert to LocalDateTime because Duration.between() doesn't work with LocalDate
            LocalDateTime from = this.arriveDate.atStartOfDay();
            LocalDateTime to = this.departureDate.atStartOfDay();
            return Duration.between(from, to).toDays();
        }
    }
}