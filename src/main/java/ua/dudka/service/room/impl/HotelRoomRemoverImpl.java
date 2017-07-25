package ua.dudka.service.room.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.dudka.domain.room.HotelRoom;
import ua.dudka.exception.room.HotelRoomBookedException;
import ua.dudka.exception.room.HotelRoomNotFoundException;
import ua.dudka.repository.room.HotelRoomRepository;
import ua.dudka.service.room.HotelRoomRemover;

/**
 * @author Rostislav Dudka
 */
@Service
@RequiredArgsConstructor
public class HotelRoomRemoverImpl implements HotelRoomRemover {
    private final HotelRoomRepository repository;

    @Override
    public void remove(String roomId) {
        HotelRoom hotelRoom = findRoomOrThrowException(roomId);
        checkIsRoomBooked(hotelRoom);
        removeRoom(hotelRoom);
    }

    private HotelRoom findRoomOrThrowException(String roomId) {
        return repository.findById(roomId)
                .orElseThrow(HotelRoomNotFoundException::new);
    }

    private void checkIsRoomBooked(HotelRoom hotelRoom) {
        if (hotelRoom.isBooked()) {
            throw new HotelRoomBookedException();
        }
    }

    private void removeRoom(HotelRoom hotelRoom) {
        repository.delete(hotelRoom);
    }
}