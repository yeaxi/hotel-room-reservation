package ua.dudka.service;

import org.junit.BeforeClass;
import org.junit.Test;
import ua.dudka.domain.HotelRoom;
import ua.dudka.exception.HotelRoomNotFoundException;
import ua.dudka.repository.HotelRoomRepository;
import ua.dudka.service.impl.HotelRoomEditorImpl;
import ua.dudka.web.dto.EditHotelRoomRequest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author Rostislav Dudka
 */
public class HotelRoomEditorTest {

    private static final String EXISTENT_ROOM_ID = "201";
    private static final String NONEXISTENT_ROOM_ID = "404";
    private static final int HOTEL_ROOM_NUMBER = 12412;
    private static final int HOTEL_ROOM_AMOUNT = 1;

    private static HotelRoom testHotelRoom;
    private static HotelRoomRepository repository;
    private static HotelRoomEditor editor;

    @BeforeClass
    public static void setUp() throws Exception {
        setUpHotelRoom();
        setUpMockRepository();
        setUpService();
    }

    private static void setUpHotelRoom() {
        BigDecimal price = BigDecimal.ONE;
        String description = "cool description";
        testHotelRoom = new HotelRoom(HOTEL_ROOM_NUMBER, HOTEL_ROOM_AMOUNT, price, description);
        testHotelRoom.book();
    }

    private static void setUpMockRepository() {
        repository = mock(HotelRoomRepository.class);
        when(repository.findById(eq(EXISTENT_ROOM_ID))).thenReturn(Optional.of(testHotelRoom));
        when(repository.findById(eq(NONEXISTENT_ROOM_ID))).thenReturn(Optional.empty());
    }

    private static void setUpService() {
        editor = new HotelRoomEditorImpl(repository);
    }

    @Test
    public void editShouldUpdateHotelRoomInfoAndSaveToRepository() throws Exception {
        String roomId = EXISTENT_ROOM_ID;
        EditHotelRoomRequest request = buildEditHotelRoomRequest(roomId);

        editor.edit(request);

        HotelRoom expectedRoom = buildExpectedHotelRoom(request);
        verify(repository, atLeastOnce()).save(eq(expectedRoom));
    }


    @Test(expected = HotelRoomNotFoundException.class)
    public void editShouldThrowHotelRoomNotFoundExceptionInCaseOfNonexistentId() throws Exception {
        String roomId = NONEXISTENT_ROOM_ID;
        EditHotelRoomRequest request = buildEditHotelRoomRequest(roomId);

        editor.edit(request);
    }


    @Test
    public void releaseBookedRoomShouldChangeStatusInHotelRoomAndSaveToRepository() throws Exception {

        editor.release(EXISTENT_ROOM_ID);

        HotelRoom expectedHotelRoom = getExpectedReleasedRoom();
        verify(repository).save(eq(expectedHotelRoom));
    }

    @Test(expected = HotelRoomNotFoundException.class)
    public void releaseNonexistentRoomShouldThrowException() throws Exception {
        editor.release(NONEXISTENT_ROOM_ID);
    }

    private HotelRoom getExpectedReleasedRoom() {
        BigDecimal price = testHotelRoom.getPrice();
        String description = testHotelRoom.getDescription();

        return new HotelRoom(HOTEL_ROOM_NUMBER, HOTEL_ROOM_AMOUNT, price, description);
    }

    private HotelRoom buildExpectedHotelRoom(EditHotelRoomRequest request) {
        return new HotelRoom(HOTEL_ROOM_NUMBER, HOTEL_ROOM_AMOUNT, request.getPrice(), request.getDescription());
    }

    private EditHotelRoomRequest buildEditHotelRoomRequest(String roomId) {
        return new EditHotelRoomRequest(roomId, BigDecimal.TEN, "desc");
    }
}