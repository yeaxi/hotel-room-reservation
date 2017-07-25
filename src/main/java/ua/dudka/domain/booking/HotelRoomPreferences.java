package ua.dudka.domain.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.dudka.domain.HotelRoomType;

import java.math.BigDecimal;

import static ua.dudka.domain.HotelRoomType.ONE_ROOM;

/**
 * @author Rostislav Dudka
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class HotelRoomPreferences {

    private HotelRoomType roomType = ONE_ROOM;
    private int hotelRoomAmount = 1;
    private BigDecimal fromPrice = BigDecimal.ONE;
    private BigDecimal toPrice = BigDecimal.valueOf(100_000);
}