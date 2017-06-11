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

import static ua.dudka.web.IndexController.Links.FIND_ROOM_URL;

/**
 * @author Rostislav Dudka
 */
@Controller
@RequiredArgsConstructor
public class IndexController {

    private final HotelRoomRepository hotelRoomRepository;

    @GetMapping("/")
    public String getPage(@RequestParam(required = false) HotelRoom.Status status, Model model) {
        addRoomsToModel(model, status);
        return "index";
    }

    @PostMapping(FIND_ROOM_URL)
    public String findRoomByNumber(@RequestParam int number, Model model) {
        List<HotelRoom> hotelRooms = hotelRoomRepository.findByNumber(number);
        addRoomsToModel(model, hotelRooms);

        if (hotelRooms.isEmpty())
            addErrorToModel(model);

        return "index";
    }

    private void addRoomsToModel(Model model, List<HotelRoom> hotelRooms) {
        model.addAttribute("rooms", hotelRooms);
    }

    private void addRoomsToModel(Model model, HotelRoom.Status status) {
        List<HotelRoom> rooms;
        if (status == null)
            rooms = hotelRoomRepository.findAll();
        else
            rooms = hotelRoomRepository.findByStatus(status);
        model.addAttribute("rooms", rooms);
    }

    private void addErrorToModel(Model model) {
        model.addAttribute("error", "No room with such number");
    }

    public static class Links {

        public static final String FIND_ROOM_URL = "/find-room";
    }
}