package ua.dudka.exception;

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
