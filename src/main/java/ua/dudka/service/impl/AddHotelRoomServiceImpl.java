package ua.dudka.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.dudka.domain.HotelRoom;
import ua.dudka.exception.HotelRoomAlreadyExistsException;
import ua.dudka.repository.HotelRoomRepository;
import ua.dudka.service.AddHotelRoomService;
import ua.dudka.web.dto.AddHotelRoomRequest;

import java.util.List;

/**
 * @author Rostislav Dudka
 */
@Service
@RequiredArgsConstructor
public class AddHotelRoomServiceImpl implements AddHotelRoomService {

    private final HotelRoomRepository repository;

    @Override
    public void add(AddHotelRoomRequest addHotelRoomRequest) {
        checkForExistentHotelRoom(addHotelRoomRequest);
        HotelRoom hotelRoom = createHotelRoom(addHotelRoomRequest);
        repository.save(hotelRoom);
    }

    private void checkForExistentHotelRoom(AddHotelRoomRequest request) {
        int number = request.getHotelRoomNumber();
        List<HotelRoom> existentRooms = repository.findByNumber(number);

        if (!existentRooms.isEmpty()) {
            throw new HotelRoomAlreadyExistsException("Room with number: " + number + " already exists!");
        }
    }

    private HotelRoom createHotelRoom(AddHotelRoomRequest request) {
        int number = request.getHotelRoomNumber();
        String description = request.getDescription();
        return new HotelRoom(number, description);
    }
}