package ua.dudka.exception.booking;

/**
 * @author Rostislav Dudka
 */
public class BookingRequestNotFoundException extends RuntimeException {
    private static final String MESSAGE = "request with id %s not found!";

    public BookingRequestNotFoundException() {
    }

    public BookingRequestNotFoundException(String requestId) {
        super(String.format(MESSAGE, requestId));
    }
}
