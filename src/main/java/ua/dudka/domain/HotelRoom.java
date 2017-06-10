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

    @NonNull
    private String description = "";

    @NonNull
    @Setter
    private Status status = Status.FREE;


    public HotelRoom(String description) {
        this.description = description;
    }

    enum Status {
        FREE, RESERVED
    }
}