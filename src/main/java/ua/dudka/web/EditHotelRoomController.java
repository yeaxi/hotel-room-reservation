package ua.dudka.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.dudka.domain.HotelRoom;
import ua.dudka.exception.HotelRoomBookedException;
import ua.dudka.repository.HotelRoomRepository;
import ua.dudka.service.HotelRoomEditor;
import ua.dudka.service.HotelRoomRemover;
import ua.dudka.web.dto.EditHotelRoomRequest;

import java.util.Optional;

import static ua.dudka.web.EditHotelRoomController.Links.*;
import static ua.dudka.web.HotelRoomController.Links.ROOM_PAGE_URL;

/**
 * @author Rostislav Dudka
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class EditHotelRoomController {

    private final HotelRoomRepository repository;
    private final HotelRoomEditor editor;
    private final HotelRoomRemover remover;

    @GetMapping(EDIT_ROOM_PAGE_URL)
    public String getPage(@PathVariable String id, Model model) {
        Optional<HotelRoom> hotelRoom = repository.findById(id);

        if (hotelRoom.isPresent())
            return setRoomToModelAndReturnEditPage(hotelRoom.get(), model);
        else
            return redirectToRoomPage();
    }

    private String setRoomToModelAndReturnEditPage(HotelRoom hotelRoom, Model model) {
        model.addAttribute("room", hotelRoom);
        return "/admin/edit-room";
    }

    private String setRoomToModelAndReturnEditPage(String id, Model model) {
        Optional<HotelRoom> hotelRoom = repository.findById(id);
        return setRoomToModelAndReturnEditPage(hotelRoom.get(), model);
    }

    private String redirectToRoomPage() {
        return "redirect:" + ROOM_PAGE_URL;
    }

    @PostMapping(EDIT_ROOM_URL)
    public String editRoom(
            @ModelAttribute EditHotelRoomRequest request,
            Model model
    ) {
        processEditRoom(request, model);
        return setRoomToModelAndReturnEditPage(request.getRoomId(), model);
    }

    private void processEditRoom(EditHotelRoomRequest request, Model model) {
        log.info("processing request: {}", request);
        editor.edit(request);
        model.addAttribute("success", "");
    }

    @PostMapping(RELEASE_ROOM_URL)
    public String releaseRoom(@RequestParam String id, Model model) {
        processReleaseRoom(id, model);
        return setRoomToModelAndReturnEditPage(id, model);
    }

    private void processReleaseRoom(String id, Model model) {
        log.info("releasing room with id: {}", id);
        editor.release(id);
        model.addAttribute("success", "");
    }

    @PostMapping(REMOVE_ROOM_URL)
    public String removeRoom(@RequestParam("id") String roomId, Model model) {
        try {
            return processRemoveRoom(roomId);
        } catch (HotelRoomBookedException e) {
            return addErrorToModelAndReturnEditPage(roomId, model, e);
        }
    }

    private String processRemoveRoom(String id) {
        remover.remove(id);
        return redirectToRoomPage();
    }

    private String addErrorToModelAndReturnEditPage(String id, Model model, HotelRoomBookedException e) {
        model.addAttribute("error", e.getMessage());
        return setRoomToModelAndReturnEditPage(id, model);
    }

    public static class Links {
        public static final String EDIT_ROOM_PAGE_URL = "/admin/edit-room/{id}";
        public static final String EDIT_ROOM_URL = "/admin/edit-room/send";
        public static final String RELEASE_ROOM_URL = "/admin/release-room/send";
        public static final String REMOVE_ROOM_URL = "/admin/remove-room/send";
    }
}