package ua.dudka.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.dudka.exception.HotelRoomAlreadyExistsException;
import ua.dudka.service.AddHotelRoomService;
import ua.dudka.web.dto.AddHotelRoomRequest;

import static ua.dudka.web.AddHotelRoomController.Links.ADD_ROOM_PAGE_URL;
import static ua.dudka.web.AddHotelRoomController.Links.ADD_ROOM_URL;

/**
 * @author Rostislav Dudka
 */
@Controller
@RequiredArgsConstructor
public class AddHotelRoomController {

    private final AddHotelRoomService addHotelRoomService;

    @GetMapping(ADD_ROOM_PAGE_URL)
    public String getPage(Model model) {
        return ADD_ROOM_PAGE_URL;
    }

    @PostMapping(ADD_ROOM_URL)
    public String addRoom(@RequestParam int number,
                          @RequestParam String description,
                          Model model) {
        AddHotelRoomRequest request = new AddHotelRoomRequest(number, description);
        try {
            addHotelRoomService.add(request);
            model.addAttribute("success", "");
        } catch (HotelRoomAlreadyExistsException e) {
            model.addAttribute("error", e.getMessage());
        }
        return ADD_ROOM_PAGE_URL;
    }

    public static class Links {
        public static final String ADD_ROOM_PAGE_URL = "/admin/add-room";
        public static final String ADD_ROOM_URL = "/admin/add-room/send";
    }
}