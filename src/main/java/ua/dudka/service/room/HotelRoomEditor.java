package ua.dudka.service.room;

import ua.dudka.web.room.dto.EditHotelRoomRequest;

/**
 * @author Rostislav Dudka
 */
public interface HotelRoomEditor {
    void edit(EditHotelRoomRequest request);

    void release(String hotelRoomId);
}