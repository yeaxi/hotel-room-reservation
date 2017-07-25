package ua.dudka.domain.room;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

/**
 * @author Rostislav Dudka
 */
@Getter
@EqualsAndHashCode(exclude = "id")
@ToString
public class HotelRoom {

    @Id
    private String id;

    private final int number;
    private final int roomAmount;

    @Setter
    private BigDecimal price;

    @NonNull
    @Setter
    private String description = "";

    @NonNull
    private Status status = Status.FREE;

    public HotelRoom() {
        this.number = 0;
        this.roomAmount = 1;
    }

    public HotelRoom(int number, String description) {
        this.number = number;
        this.roomAmount = 1;
        this.price = BigDecimal.ZERO;
        this.description = description;
    }

    public HotelRoom(int number, int roomAmount, BigDecimal price, String description) {
        this.number = number;
        this.roomAmount = roomAmount;
        this.price = price;
        this.description = description;
    }
    public void book() {
        this.status = Status.BOOKED;
    }

    public void release() {
        this.status = Status.FREE;
    }

    public boolean isBooked() {
        return this.status.equals(Status.BOOKED);
    }

    public enum Status {
        FREE, BOOKED
    }
}