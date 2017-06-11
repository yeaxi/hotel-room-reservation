package ua.dudka.service;

import org.junit.BeforeClass;
import org.junit.Test;
import ua.dudka.domain.HotelRoom;
import ua.dudka.exception.HotelRoomBookedException;
import ua.dudka.exception.HotelRoomNotFoundException;
import ua.dudka.repository.HotelRoomRepository;
import ua.dudka.service.impl.HotelRoomRemoverImpl;

import java.util.Optional;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author Rostislav Dudka
 */
public class HotelRoomRemoverTest {

    private static final String FREE_ROOM_ID = "101";
    private static final String BOOKED_ROOM_ID = "401";
    private static final String NONEXISTENT_ROOM_ID = "404";

    private static HotelRoom freeRoom;
    private static HotelRoom bookedRoom;

    private static HotelRoomRepository repository;
    private static HotelRoomRemover remover;

    @BeforeClass
    public static void setUp() throws Exception {
        setUpRooms();
        setUpMockRepository();
        setUpHotelRoomRemover();
    }

    private static void setUpRooms() {
        freeRoom = new HotelRoom(0, "free room");

        bookedRoom = new HotelRoom(0, "bookedRoom");
        bookedRoom.book();
    }

    private static void setUpHotelRoomRemover() {
        remover = new HotelRoomRemoverImpl(repository);
    }

    private static void setUpMockRepository() {
        repository = mock(HotelRoomRepository.class);
        when(repository.findById(eq(FREE_ROOM_ID))).thenReturn(Optional.of(freeRoom));
        when(repository.findById(eq(BOOKED_ROOM_ID))).thenReturn(Optional.of(bookedRoom));
        when(repository.findById(eq(NONEXISTENT_ROOM_ID))).thenReturn(Optional.empty());
    }


    @Test
    public void removeShouldRemoveRoomFromRepository() throws Exception {
        remover.remove(FREE_ROOM_ID);

        verify(repository).delete(freeRoom);
    }

    @Test(expected = HotelRoomBookedException.class)
    public void removeReservedHotelRoomShouldThrowException() throws Exception {
        remover.remove(BOOKED_ROOM_ID);
    }

    @Test(expected = HotelRoomNotFoundException.class)
    public void removeNonexistentRoomShouldThrowException() throws Exception {
        remover.remove(NONEXISTENT_ROOM_ID);
    }
}