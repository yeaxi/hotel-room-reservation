package ua.dudka.exception;

/**
 * @author Rostislav Dudka
 */
public class HotelRoomBookedException extends RuntimeException {
    private static final String MESSAGE = "cannot remove booked room!";

    public HotelRoomBookedException() {
        super(MESSAGE);
    }
}