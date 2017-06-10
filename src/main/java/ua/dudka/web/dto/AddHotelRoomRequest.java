package ua.dudka.web.dto;

import lombok.Value;

/**
 * @author Rostislav Dudka
 */
@Value
public class AddHotelRoomRequest {
    private int hotelRoomNumber;
    private String description;
}
