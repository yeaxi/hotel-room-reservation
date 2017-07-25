package ua.dudka.web.room;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.dudka.exception.room.HotelRoomAlreadyExistsException;
import ua.dudka.service.room.AddHotelRoomService;
import ua.dudka.web.room.dto.AddHotelRoomRequest;

import static ua.dudka.web.room.AddHotelRoomController.Links.ADD_ROOM_PAGE_URL;
import static ua.dudka.web.room.AddHotelRoomController.Links.ADD_ROOM_URL;

/**
 * @author Rostislav Dudka
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class AddHotelRoomController {

    private final AddHotelRoomService addHotelRoomService;

    @GetMapping(ADD_ROOM_PAGE_URL)
    public String getPage(Model model) {
        return ADD_ROOM_PAGE_URL;
    }

    @PostMapping(ADD_ROOM_URL)
    public String addRoom(
            @ModelAttribute AddHotelRoomRequest request,
                          Model model) {
        try {
            processRequest(request, model);
        } catch (HotelRoomAlreadyExistsException e) {
            handleError(model, e);
        }
        return ADD_ROOM_PAGE_URL;
    }

    private void processRequest(@ModelAttribute AddHotelRoomRequest request, Model model) {
        log.info("processing request: #{}", request);
        addHotelRoomService.add(request);
        model.addAttribute("success", "");
    }

    private void handleError(Model model, HotelRoomAlreadyExistsException e) {
        model.addAttribute("error", e.getMessage());
        log.warn("handling error %s", e.getMessage());
    }

    public static class Links {
        public static final String ADD_ROOM_PAGE_URL = "/admin/add-room";
        public static final String ADD_ROOM_URL = "/admin/add-room/send";
    }
}