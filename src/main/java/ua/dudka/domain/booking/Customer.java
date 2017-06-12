package ua.dudka.domain.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rostislav Dudka
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    private String name = "";
    private String surname =  "";
    private String phoneNumber = "";

}