package ua.dudka.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.dudka.domain.HotelRoom;
import ua.dudka.exception.HotelRoomBookedException;
import ua.dudka.exception.HotelRoomNotFoundException;
import ua.dudka.repository.HotelRoomRepository;
import ua.dudka.service.HotelRoomRemover;

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