package ua.dudka.repository.booking;

import org.springframework.data.mongodb.repository.MongoRepository;
import ua.dudka.domain.booking.BookingRequest;

import java.util.List;
import java.util.Optional;

import static ua.dudka.domain.booking.BookingRequest.*;

/**
 * @author Rostislav Dudka
 */
public interface BookingRequestRepository extends MongoRepository<BookingRequest, String> {
    Optional<BookingRequest> findById(String id);

    List<BookingRequest> findByStatus(RequestStatus status);
}