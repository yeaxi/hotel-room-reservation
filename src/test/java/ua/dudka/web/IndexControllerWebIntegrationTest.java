package ua.dudka.web;

import abstract_test.AbstractWebIntegrationTest;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import ua.dudka.domain.room.HotelRoom;
import ua.dudka.repository.room.HotelRoomRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ua.dudka.domain.room.HotelRoom.Status.FREE;
import static ua.dudka.web.IndexController.Links.FIND_ROOM_URL;

/**
 * @author Rostislav Dudka
 */
@WebMvcTest(controllers = IndexController.class, secure = false)
public class IndexControllerWebIntegrationTest extends AbstractWebIntegrationTest {

    private static final int TEST_ROOM_NUMBER = 105;
    private static final int NON_EXISTENT_ROOM_NUMBER = 404;

    private List<HotelRoom> testRooms = new ArrayList<>();
    private List<HotelRoom> freeRooms = new ArrayList<>();
    private List<HotelRoom> listWithOneRoom = new ArrayList<>();
    private List<HotelRoom> emptyList = Lists.emptyList();

    @MockBean
    private HotelRoomRepository repository;

    @Before
    public void setUp() throws Exception {
        setUpHotelRooms();
        setUpMockRepository();
    }

    private void setUpHotelRooms() {
        HotelRoom e = new HotelRoom();
        e.book();
        testRooms.add(e);
        testRooms.add(new HotelRoom());
        testRooms.add(new HotelRoom());
        testRooms.add(new HotelRoom());

        listWithOneRoom.add(new HotelRoom(0, "one room"));
        freeRooms.addAll(testRooms.subList(1, testRooms.size()));
    }

    private void setUpMockRepository() {
        when(repository.findAll()).thenReturn(testRooms);
        when(repository.findByStatus(eq(FREE))).thenReturn(freeRooms);
        when(repository.findByNumber(eq(TEST_ROOM_NUMBER))).thenReturn(listWithOneRoom);
        when(repository.findByNumber(eq(NON_EXISTENT_ROOM_NUMBER))).thenReturn(emptyList);
    }

    @Test
    public void getPageShouldSetHotelRoomsToModel() throws Exception {
        mockMvc.perform(get("/").accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().contentType(RESPONSE_HTML_CONTENT_TYPE))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attributeExists("rooms"))
                .andExpect(model().attribute("rooms", testRooms));
    }

    @Test
    public void getPageWithFreeRoomsParamShouldSetHotelRoomsToModel() throws Exception {
        mockMvc.perform(get("/")
                .param("status", FREE.toString()).accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().contentType(RESPONSE_HTML_CONTENT_TYPE))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attributeExists("rooms"))
                .andExpect(model().attribute("rooms", freeRooms));
    }


    @Test
    public void findRoomByExistentNumberShouldSetHotelRoomsToModel() throws Exception {
        mockMvc.perform(post(FIND_ROOM_URL)
                .param("number", TEST_ROOM_NUMBER + "").accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().contentType(RESPONSE_HTML_CONTENT_TYPE))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attributeExists("rooms"))
                .andExpect(model().attribute("rooms", listWithOneRoom));
    }

    @Test
    public void findRoomByNoneExistentNumberShouldSetErrorToModel() throws Exception {
        mockMvc.perform(post(FIND_ROOM_URL)
                .param("number", NON_EXISTENT_ROOM_NUMBER + "")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().contentType(RESPONSE_HTML_CONTENT_TYPE))
                .andExpect(model().attribute("rooms", emptyList))
                .andExpect(model().attributeExists("error"));
    }
}