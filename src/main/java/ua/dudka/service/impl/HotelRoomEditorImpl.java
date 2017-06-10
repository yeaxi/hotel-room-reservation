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
        HotelRoom room = findHotelRoom(request.getRoomId());

        room.changeDescription(request.getDescription());
        room.changeStatus(request.getStatus());

        repository.save(room);
    }

    private HotelRoom findHotelRoom(String roomId) {
        return repository.findById(roomId).orElseThrow(HotelRoomNotFoundException::new);
    }
}