package ua.dudka.web.room.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Rostislav Dudka
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditHotelRoomRequest {
    private String roomId;
    private BigDecimal price;
    private String description;
}