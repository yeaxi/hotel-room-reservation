package ua.dudka.web.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.dudka.domain.booking.BookingRequest;
import ua.dudka.domain.room.HotelRoom;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rostislav Dudka
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequestWithRooms {
    private BookingRequest request = new BookingRequest();
    private List<HotelRoom> availableRooms = new ArrayList<>();
}