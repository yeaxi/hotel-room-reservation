package ua.dudka.web;

import abstract_test.AbstractWebIntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import ua.dudka.domain.HotelRoom;
import ua.dudka.repository.HotelRoomRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ua.dudka.web.HotelRoomController.Links.ROOM_PAGE_URL;

/**
 * @author Rostislav Dudka
 */
@WebMvcTest(controllers = HotelRoomController.class, secure = false)
public class HotelRoomControllerIntegrationTest extends AbstractWebIntegrationTest {

    private List<HotelRoom> testRooms = new ArrayList<>();

    @MockBean
    private HotelRoomRepository repository;

    @Before
    public void setUp() throws Exception {
        testRooms.add(new HotelRoom());
        testRooms.add(new HotelRoom());
        testRooms.add(new HotelRoom());
        testRooms.add(new HotelRoom());

        when(repository.findAll()).thenReturn(testRooms);
    }

    @Test
    public void getPageShouldSetHotelRoomsToModel() throws Exception {
        mockMvc.perform(get(ROOM_PAGE_URL).accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().contentType(RESPONSE_HTML_CONTENT_TYPE))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attributeExists("rooms"))
                .andExpect(model().attribute("rooms", testRooms));
    }
}