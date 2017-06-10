package ua.dudka.web.dto;

import lombok.Value;
import ua.dudka.domain.HotelRoom;

/**
 * @author Rostislav Dudka
 */
@Value
public class EditHotelRoomRequest {
    private String roomId;
    private String description;
    private HotelRoom.Status status;
}