package ua.dudka.repository.booking;

import org.springframework.data.mongodb.repository.MongoRepository;
import ua.dudka.domain.booking.BookingRequest;

import java.util.Optional;

/**
 * @author Rostislav Dudka
 */
public interface BookingRequestRepository extends MongoRepository<BookingRequest, String> {
    Optional<BookingRequest> findById(String id);
}