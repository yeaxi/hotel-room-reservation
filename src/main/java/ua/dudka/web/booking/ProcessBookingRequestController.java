package ua.dudka.web.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.dudka.service.booking.DenyRequestService;
import ua.dudka.service.booking.FindRoomsByBookingRequestService;
import ua.dudka.web.booking.dto.BookingRequestWithRooms;

import static ua.dudka.web.booking.ProcessBookingRequestController.Links.*;

/**
 * @author Rostislav Dudka
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class ProcessBookingRequestController {

    private final FindRoomsByBookingRequestService finder;
    private final DenyRequestService processor;

    @GetMapping(PROCESS_BOOKING_REQUEST_PAGE_URL)
    public String getPage(@PathVariable String id, Model model) {
        setDataToModel(id, model);
        return getPathToPage();
    }


    @PostMapping(DENY_BOOKING_REQUEST_URL)
    public String denyBookingRequest(
            @RequestParam String requestId,
            Model model
    ) {
        denyRequest(requestId, model);
        return getPathToPage();
    }

    private void setDataToModel(String bookingRequestId, Model model) {
        BookingRequestWithRooms requestWithRooms = finder.find(bookingRequestId);
        model.addAttribute("bookingRequestWithRooms", requestWithRooms);
    }

    private void denyRequest(String requestId, Model model) {
        log.info("denying BookingRequest with id{}", requestId);
        processor.deny(requestId);

        setDataToModel(requestId, model);
        model.addAttribute("successDeny", "");
    }

    private String getPathToPage() {
        return "/admin/process-booking-request";
    }

    public static class Links {
        public static final String PROCESS_BOOKING_REQUEST_PAGE_URL = "/admin/process-booking-request/{id}";
        public static final String DENY_BOOKING_REQUEST_URL = "/admin/deny-booking-request";
    }
}