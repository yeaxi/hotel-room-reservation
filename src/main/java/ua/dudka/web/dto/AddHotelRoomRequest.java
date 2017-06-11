package ua.dudka.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Rostislav Dudka
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddHotelRoomRequest {
    private int hotelRoomNumber;
    private String description;
    private int roomAmount;
    private BigDecimal price;
}