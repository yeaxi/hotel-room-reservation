package ua.dudka.service;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import ua.dudka.domain.HotelRoom;
import ua.dudka.exception.HotelRoomAlreadyExistsException;
import ua.dudka.repository.HotelRoomRepository;
import ua.dudka.service.impl.AddHotelRoomServiceImpl;
import ua.dudka.web.dto.AddHotelRoomRequest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author Rostislav Dudka
 */
public class AddHotelRoomServiceTest {

    private static final int EXISTENT_ROOM_NUMBER = 105;
    private static final int NON_EXISTENT_ROOM_NUMBER = 404;

    private static HotelRoomRepository repository;
    private static AddHotelRoomService addHotelRoomService;

    @Before
    public void setUp() throws Exception {
        repository = mock(HotelRoomRepository.class);

        List<HotelRoom> existentRooms = new ArrayList<>();
        existentRooms.add(new HotelRoom());

        when(repository.findByNumber(EXISTENT_ROOM_NUMBER)).thenReturn(existentRooms);
        when(repository.findByNumber(NON_EXISTENT_ROOM_NUMBER)).thenReturn(Lists.emptyList());

        addHotelRoomService = new AddHotelRoomServiceImpl(repository);
    }

    @Test
    public void addShouldSaveCreatedHotelRoomToRepository() throws Exception {
        AddHotelRoomRequest request = new AddHotelRoomRequest(NON_EXISTENT_ROOM_NUMBER, "description");

        addHotelRoomService.add(request);

        verify(repository).save(eq(new HotelRoom(request.getHotelRoomNumber(), request.getDescription())));
    }

    @Test(expected = HotelRoomAlreadyExistsException.class)
    public void addShouldThrowExceptionInCaseOfExistentRoomNumber() throws Exception {
        AddHotelRoomRequest request = new AddHotelRoomRequest(EXISTENT_ROOM_NUMBER, "description");

        addHotelRoomService.add(request);
    }
}