package ua.dudka.domain;

import lombok.*;
import org.springframework.data.annotation.Id;

/**
 * @author Rostislav Dudka
 */
@Getter
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
@ToString
public class HotelRoom {

    @Id
    private String id;

    private int number;

    @NonNull
    private String description = "";

    @NonNull
    private Status status = Status.FREE;


    public HotelRoom(int number, String description) {
        this.number = number;
        this.description = description;
    }

    public void changeDescription(String description) {
        this.description = description;
    }

    public void changeStatus(Status status) {
        this.status = status;
    }

    public void book() {
        this.status = Status.BOOKED;
    }

    public boolean isBooked() {
        return this.status.equals(Status.BOOKED);
    }

    public enum Status {
        FREE, BOOKED
    }
}