package ua.dudka.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ua.dudka.domain.HotelRoom;

/**
 * @author Rostislav Dudka
 */
public interface HotelRoomRepository extends MongoRepository<HotelRoom, String> {
}