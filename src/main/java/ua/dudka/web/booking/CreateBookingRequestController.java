package ua.dudka.web.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.dudka.service.booking.BookingRequestCreator;
import ua.dudka.web.booking.dto.CreateBookingRequestDTO;

import static ua.dudka.web.booking.CreateBookingRequestController.Links.CREATE_BOOKING_REQUEST_PAGE_URL;
import static ua.dudka.web.booking.CreateBookingRequestController.Links.CREATE_BOOKING_REQUEST_URL;

/**
 * @author Rostislav Dudka
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class CreateBookingRequestController {

    private final BookingRequestCreator creator;

    @GetMapping(CREATE_BOOKING_REQUEST_PAGE_URL)
    public String getPage() {
        return getPathToCreateBookingRequestPage();
    }

    @PostMapping(CREATE_BOOKING_REQUEST_URL)
    public String createBookingRequest(
            @ModelAttribute CreateBookingRequestDTO requestDTO,
            Model model
    ) {
        processCreateBookingRequest(requestDTO, model);
        return getPathToCreateBookingRequestPage();
    }

    private void processCreateBookingRequest(CreateBookingRequestDTO requestDTO, Model model) {
        log.info("processing request : {}", requestDTO);
        creator.create(requestDTO);
        model.addAttribute("success", "");
    }

    private String getPathToCreateBookingRequestPage() {
        return "/user/create-booking-request";
    }

    public static class Links {
        public static final String CREATE_BOOKING_REQUEST_PAGE_URL = "/create-booking-request";
        public static final String CREATE_BOOKING_REQUEST_URL = "/create-booking-request/send";
    }

}