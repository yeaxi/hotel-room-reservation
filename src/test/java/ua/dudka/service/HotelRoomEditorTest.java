package ua.dudka.service;

import org.junit.BeforeClass;
import org.junit.Test;
import ua.dudka.domain.HotelRoom;
import ua.dudka.exception.HotelRoomNotFoundException;
import ua.dudka.repository.HotelRoomRepository;
import ua.dudka.service.impl.HotelRoomEditorImpl;
import ua.dudka.web.dto.EditHotelRoomRequest;

import java.util.Optional;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static ua.dudka.domain.HotelRoom.Status.RESERVED;

/**
 * @author Rostislav Dudka
 */
public class HotelRoomEditorTest {

    private static final String EXISTENT_ROOM_ID = "201";
    private static final String NONEXISTENT_ROOM_ID = "404";

    private static HotelRoom testHotelRoom;

    private static HotelRoomRepository repository;
    private static HotelRoomEditor editor;

    @BeforeClass
    public static void setUp() throws Exception {
        testHotelRoom = new HotelRoom(12412, "cool description");

        repository = mock(HotelRoomRepository.class);
        when(repository.findById(eq(EXISTENT_ROOM_ID))).thenReturn(Optional.of(testHotelRoom));
        when(repository.findById(eq(NONEXISTENT_ROOM_ID))).thenReturn(Optional.empty());

        editor = new HotelRoomEditorImpl(repository);
    }

    @Test
    public void editShouldUpdateHotelRoomInfoAndSaveToRepository() throws Exception {
        EditHotelRoomRequest request = new EditHotelRoomRequest(EXISTENT_ROOM_ID, "new description", RESERVED);

        editor.edit(request);

        verify(repository).save(eq(testHotelRoom));
    }

    @Test(expected = HotelRoomNotFoundException.class)
    public void editShouldThrowHotelRoomNotFoundExceptionInCaseOfNonexistentId() throws Exception {
        EditHotelRoomRequest request = new EditHotelRoomRequest(NONEXISTENT_ROOM_ID, "desc", RESERVED);

        editor.edit(request);
    }
}