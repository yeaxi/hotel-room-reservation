package ua.dudka.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.dudka.domain.HotelRoom;
import ua.dudka.repository.HotelRoomRepository;

import java.util.List;

import static ua.dudka.web.HotelRoomController.Links.FIND_ROOM_BY_NUMBER_URL;
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

    @PostMapping(FIND_ROOM_BY_NUMBER_URL)
    public String findRoomByNumber(@RequestParam int number, Model model) {
        List<HotelRoom> hotelRooms = hotelRoomRepository.findByNumber(number);
        model.addAttribute("rooms", hotelRooms);
        if (hotelRooms.isEmpty())
            model.addAttribute("error", "No room with such number");
        return "/admin/rooms";
    }

    public static class Links {
        public static final String ROOM_PAGE_URL = "/admin/rooms";
        public static final String FIND_ROOM_BY_NUMBER_URL = "/admin/rooms";
    }
}