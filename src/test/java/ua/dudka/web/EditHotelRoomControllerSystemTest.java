package ua.dudka.web;

import abstract_test.AbstractSystemTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ua.dudka.domain.HotelRoom;
import ua.dudka.repository.HotelRoomRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.dudka.web.EditHotelRoomController.Links.EDIT_ROOM_PAGE_URL;

/**
 * @author Rostislav Dudka
 */
public class EditHotelRoomControllerSystemTest extends AbstractSystemTest {

    @Autowired
    private HotelRoomRepository repository;

    private HotelRoom hotelRoom;


    @Before
    public void setUp() throws Exception {
        hotelRoom = repository.save(new HotelRoom(9348758, "de"));
    }

    @Test
    public void getPageShouldReturnOK() throws Exception {
        mockMvc.perform(get(EDIT_ROOM_PAGE_URL, hotelRoom.getId()).accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().contentType(RESPONSE_HTML_CONTENT_TYPE));
    }
}