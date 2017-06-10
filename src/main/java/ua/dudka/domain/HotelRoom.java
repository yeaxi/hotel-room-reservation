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

    private static int numberCounter = 0;
    private int number;

    @NonNull
    private String description = "";

    @NonNull
    @Setter
    private Status status = Status.FREE;


    public HotelRoom(String description) {
        this.description = description;
        numberCounter++;
        this.number = numberCounter;
    }

    enum Status {
        FREE, RESERVED
    }
}