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
    @Setter
    private Status status = Status.FREE;


    public HotelRoom(int number, String description) {
        this.number = number;
        this.description = description;
    }

    public enum Status {
        FREE, RESERVED
    }
}