package ua.dudka.domain.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.dudka.domain.HotelRoomType;

import java.math.BigDecimal;

/**
 * @author Rostislav Dudka
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class HotelRoomPreferences {

    private HotelRoomType roomType;
    private int hotelRoomAmount;
    private BigDecimal fromPrice;
    private BigDecimal toPrice;
}