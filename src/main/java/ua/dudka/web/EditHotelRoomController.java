package ua.dudka.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.dudka.domain.HotelRoom;
import ua.dudka.repository.HotelRoomRepository;
import ua.dudka.service.HotelRoomEditor;
import ua.dudka.web.dto.EditHotelRoomRequest;

import java.util.Optional;

import static ua.dudka.web.EditHotelRoomController.Links.EDIT_ROOM_PAGE_URL;
import static ua.dudka.web.EditHotelRoomController.Links.EDIT_ROOM_URL;
import static ua.dudka.web.HotelRoomController.Links.ROOM_PAGE_URL;

/**
 * @author Rostislav Dudka
 */
@Controller
@RequiredArgsConstructor
public class EditHotelRoomController {

    private final HotelRoomRepository repository;
    private final HotelRoomEditor editor;

    @GetMapping(EDIT_ROOM_PAGE_URL)
    public String getPage(@PathVariable String id, Model model) {
        Optional<HotelRoom> hotelRoom = repository.findById(id);
        if (hotelRoom.isPresent()) {
            model.addAttribute("room", hotelRoom.get());
            return "/admin/edit-room";
        } else
            return "redirect:" + ROOM_PAGE_URL;

    }

    @PostMapping(EDIT_ROOM_URL)
    public String editRoom(
            @RequestParam String id,
            @RequestParam String description,
            @RequestParam HotelRoom.Status status,
            Model model
    ) {
        editor.edit(new EditHotelRoomRequest(id, description, status));
        model.addAttribute("success", "");
        repository.findById(id).ifPresent(r -> model.addAttribute("room", r));
        return "/admin/edit-room";
    }

    public static class Links {
        public static final String EDIT_ROOM_PAGE_URL = "/admin/edit-room/{id}";
        public static final String EDIT_ROOM_URL = "/admin/edit-room/send";
    }
}
