package ua.dudka.web.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.dudka.domain.booking.BookingRequest;
import ua.dudka.domain.booking.BookingRequest.RequestStatus;
import ua.dudka.repository.booking.BookingRequestRepository;

import java.util.List;

import static ua.dudka.web.booking.DisplayBookingRequestsController.Links.BOOKING_REQUESTS_PAGE_URL;

/**
 * @author Rostislav Dudka
 */
@Controller
@RequiredArgsConstructor
public class DisplayBookingRequestsController {

    private final BookingRequestRepository requestRepository;

    @GetMapping(BOOKING_REQUESTS_PAGE_URL)
    public String getPage(
            @RequestParam(required = false) RequestStatus status,
            Model model
    ) {
        setBookingRequestsToModel(status, model);
        return "/admin/booking-requests";
    }

    private void setBookingRequestsToModel(RequestStatus status, Model model) {
        List<BookingRequest> requests = findBookingRequestsByStatus(status);
        model.addAttribute("requests", requests);
    }

    private List<BookingRequest> findBookingRequestsByStatus(RequestStatus status) {
        List<BookingRequest> requests;
        if (status != null)
            requests = requestRepository.findByStatus(status);
        else
            requests = requestRepository.findAll();
        return requests;
    }

    public static class Links {
        public static final String BOOKING_REQUESTS_PAGE_URL = "/admin/booking-requests";
    }
}