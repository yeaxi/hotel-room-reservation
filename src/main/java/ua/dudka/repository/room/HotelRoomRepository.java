package ua.dudka.repository.room;

import org.springframework.data.mongodb.repository.MongoRepository;
import ua.dudka.domain.room.HotelRoom;

import java.util.List;
import java.util.Optional;

/**
 * @author Rostislav Dudka
 */
public interface HotelRoomRepository extends MongoRepository<HotelRoom, String> {
    List<HotelRoom> findByNumber(int number);

    Optional<HotelRoom> findById(String id);

    List<HotelRoom> findByStatus(HotelRoom.Status status);
}