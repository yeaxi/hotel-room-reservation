package ua.dudka.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.dudka.domain.HotelRoom;
import ua.dudka.exception.HotelRoomNotFoundException;
import ua.dudka.repository.HotelRoomRepository;
import ua.dudka.service.HotelRoomEditor;
import ua.dudka.web.dto.EditHotelRoomRequest;

/**
 * @author Rostislav Dudka
 */
@Service
@RequiredArgsConstructor
public class HotelRoomEditorImpl implements HotelRoomEditor {
    private final HotelRoomRepository repository;

    @Override
    public void edit(EditHotelRoomRequest request) {
        HotelRoom room = findHotelRoomOrThrowException(request.getRoomId());
        editRoom(request, room);
        repository.save(room);
    }

    private void editRoom(EditHotelRoomRequest request, HotelRoom room) {
        room.setDescription(request.getDescription());
        room.setPrice(request.getPrice());
    }

    private HotelRoom findHotelRoomOrThrowException(String roomId) {
        return repository.findById(roomId).orElseThrow(HotelRoomNotFoundException::new);
    }

    @Override
    public void release(String hotelRoomId) {
        HotelRoom room = findHotelRoomOrThrowException(hotelRoomId);

        room.release();
        repository.save(room);
    }
}