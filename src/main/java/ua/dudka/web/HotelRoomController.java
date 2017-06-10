package ua.dudka.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.dudka.repository.HotelRoomRepository;

import static ua.dudka.web.HotelRoomController.Links.ROOM_PAGE_URL;

/**
 * @author Rostislav Dudka
 */
@Controller
@RequiredArgsConstructor
public class HotelRoomController {

    private final HotelRoomRepository hotelRoomRepository;

    @GetMapping(ROOM_PAGE_URL)
    public String getPage(Model model) {
        model.addAttribute("rooms", hotelRoomRepository.findAll());
        return "/admin/rooms";
    }


    public static class Links {
        public static final String ROOM_PAGE_URL = "/admin/rooms";
    }
}