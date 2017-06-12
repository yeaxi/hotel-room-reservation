package ua.dudka.domain.booking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
public class BookingRequest {

    @Id
    private String id;

    private Customer customer;

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

    public enum PaymentType {
        CASH, CREDIT_CARD
    }

    public enum RequestStatus {
        CREATED, APPROVED, DENIED
    }


    public static class BookingRequestBuilder {

        public BookingRequest build() {
            String id = "";
            long tenure = createTenure();
            RequestStatus status = RequestStatus.CREATED;

            return new BookingRequest(id,this.customer, this.arriveDate, tenure, this.departureDate,
                    this.preferences, this.personAmount, this.paymentType, status);
        }

        private long createTenure() {
            //convert to LocalDateTime because Duration.between() doesn't work with LocalDate
            LocalDateTime from = this.arriveDate.atStartOfDay();
            LocalDateTime to = departureDate.atStartOfDay();
            return Duration.between(from, to).toDays();
        }
    }
}