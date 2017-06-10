package ua.dudka.web;

import abstract_test.AbstractWebIntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import ua.dudka.exception.HotelRoomAlreadyExistsException;
import ua.dudka.service.AddHotelRoomService;
import ua.dudka.web.dto.AddHotelRoomRequest;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ua.dudka.web.AddHotelRoomController.Links.ADD_ROOM_PAGE_URL;
import static ua.dudka.web.AddHotelRoomController.Links.ADD_ROOM_URL;

/**
 * @author Rostislav Dudka
 */
@WebMvcTest(controllers = AddHotelRoomController.class, secure = false)
public class AddHotelRoomControllerIntegrationTest extends AbstractWebIntegrationTest {

    private static final int EXISTENT_ROOM_NUMBER = 105;
    private static final int NON_EXISTENT_ROOM_NUMBER = 404;

    private static AddHotelRoomRequest requestWithNonexistentNumber;
    private static AddHotelRoomRequest requestWithExistentNumber;

    @MockBean
    private AddHotelRoomService addHotelRoomService;


    @Before
    public void setUp() throws Exception {

        requestWithNonexistentNumber = new AddHotelRoomRequest(NON_EXISTENT_ROOM_NUMBER, "description");
        requestWithExistentNumber = new AddHotelRoomRequest(EXISTENT_ROOM_NUMBER, "description");

        doThrow(new HotelRoomAlreadyExistsException("room with that number is already exists!"))
                .when(addHotelRoomService).add(eq(requestWithExistentNumber));
    }

    @Test
    public void getPageShouldReturnOK() throws Exception {
        mockMvc.perform(get(ADD_ROOM_PAGE_URL).accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().contentType(RESPONSE_HTML_CONTENT_TYPE))
                .andExpect(model().hasNoErrors());
    }


    @Test
    public void addRoomWithNonexistentNumberShouldOK() throws Exception {
        mockMvc.perform(post(ADD_ROOM_URL)
                .param("number", requestWithNonexistentNumber.getHotelRoomNumber() + "")
                .param("description", requestWithNonexistentNumber.getDescription())
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().contentType(RESPONSE_HTML_CONTENT_TYPE))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attributeExists("success"));

        verify(addHotelRoomService).add(eq(requestWithNonexistentNumber));
    }

    @Test
    public void addRoomWithExistentNumberShouldSetErrorToModel() throws Exception {

        mockMvc.perform(post(ADD_ROOM_URL)
                .param("number", requestWithExistentNumber.getHotelRoomNumber() + "")
                .param("description", requestWithExistentNumber.getDescription())
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().contentType(RESPONSE_HTML_CONTENT_TYPE))
                .andExpect(model().attributeExists("error"));

        verify(addHotelRoomService).add(eq(requestWithExistentNumber));
    }
}