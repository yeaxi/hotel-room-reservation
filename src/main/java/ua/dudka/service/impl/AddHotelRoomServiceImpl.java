package ua.dudka.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.dudka.domain.HotelRoom;
import ua.dudka.exception.HotelRoomAlreadyExistsException;
import ua.dudka.repository.HotelRoomRepository;
import ua.dudka.service.AddHotelRoomService;
import ua.dudka.web.dto.AddHotelRoomRequest;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Rostislav Dudka
 */
@Service
@RequiredArgsConstructor
public class AddHotelRoomServiceImpl implements AddHotelRoomService {

    private final HotelRoomRepository repository;

    @Override
    public void add(AddHotelRoomRequest request) {
        checkForExistentHotelRoom(request);
        HotelRoom hotelRoom = createHotelRoom(request);
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
        int roomAmount = request.getRoomAmount();
        BigDecimal price = request.getPrice();
        String description = request.getDescription();

        return new HotelRoom(number, roomAmount, price, description);
    }
}