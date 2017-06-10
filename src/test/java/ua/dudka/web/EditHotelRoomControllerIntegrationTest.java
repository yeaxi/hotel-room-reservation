package ua.dudka.web;

import abstract_test.AbstractWebIntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import ua.dudka.domain.HotelRoom;
import ua.dudka.repository.HotelRoomRepository;
import ua.dudka.service.HotelRoomEditor;
import ua.dudka.web.dto.EditHotelRoomRequest;

import java.util.Optional;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ua.dudka.domain.HotelRoom.Status.RESERVED;
import static ua.dudka.web.EditHotelRoomController.Links.EDIT_ROOM_PAGE_URL;
import static ua.dudka.web.EditHotelRoomController.Links.EDIT_ROOM_URL;
import static ua.dudka.web.HotelRoomController.Links.ROOM_PAGE_URL;

/**
 * @author Rostislav Dudka
 */
@WebMvcTest(controllers = EditHotelRoomController.class, secure = false)
public class EditHotelRoomControllerIntegrationTest extends AbstractWebIntegrationTest {

    private static final String EXISTENT_ROOM_ID = "105";
    private static final String NONEXISTENT_ROOM_ID = "404";

    private static EditHotelRoomRequest editHotelRoomRequest;

    @MockBean
    private HotelRoomRepository repository;

    @MockBean
    private HotelRoomEditor hotelRoomEditor;


    @Before
    public void setUp() throws Exception {
        HotelRoom hotelRoom = new HotelRoom(10, "desc");

        when(repository.findById(eq(EXISTENT_ROOM_ID))).thenReturn(Optional.of(hotelRoom));
        when(repository.findById(eq(NONEXISTENT_ROOM_ID))).thenReturn(Optional.empty());

        editHotelRoomRequest = new EditHotelRoomRequest(EXISTENT_ROOM_ID, "description", RESERVED);
    }

    @Test
    public void getPageWithExistentRoomNumberShouldReturnOK() throws Exception {
        mockMvc.perform(get(EDIT_ROOM_PAGE_URL, EXISTENT_ROOM_ID)
                .param("id", EXISTENT_ROOM_ID)
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().contentType(RESPONSE_HTML_CONTENT_TYPE))
                .andExpect(model().hasNoErrors());
    }

    @Test
    public void getPageWithNonexistentRoomNumberShouldRedirectToRoomsPage() throws Exception {
        mockMvc.perform(get(EDIT_ROOM_PAGE_URL, NONEXISTENT_ROOM_ID).accept(MediaType.TEXT_PLAIN))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(ROOM_PAGE_URL));
    }


    @Test
    public void editExistentRoomShouldOK() throws Exception {
        mockMvc.perform(post(EDIT_ROOM_URL)
                .param("id", EXISTENT_ROOM_ID)
                .param("description", editHotelRoomRequest.getDescription())
                .param("status", editHotelRoomRequest.getStatus().toString())
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().contentType(RESPONSE_HTML_CONTENT_TYPE))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attributeExists("success"));

        verify(hotelRoomEditor).edit(eq(editHotelRoomRequest));
    }
}