package ua.dudka.exception.room;

/**
 * @author Rostislav Dudka
 */
public class HotelRoomAlreadyExistsException extends RuntimeException {
    public HotelRoomAlreadyExistsException() {
        super();
    }

    public HotelRoomAlreadyExistsException(String message) {
        super(message);
    }
}
