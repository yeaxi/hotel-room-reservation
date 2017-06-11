package ua.dudka.repository;

import abstract_test.AbstractRepositoryTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.dudka.domain.HotelRoom;

import java.util.List;

import static org.junit.Assert.*;
import static ua.dudka.domain.HotelRoom.Status;

/**
 * @author Rostislav Dudka
 */
public class HotelRoomRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private HotelRoomRepository repository;

    private HotelRoom testRoom;


    @Before
    public void setUp() throws Exception {
        tearDown();
        testRoom = new HotelRoom(100500, "test room");
        testRoom = repository.save(testRoom);
    }

    @After
    public void tearDown() throws Exception {
        repository.deleteAll();
    }

    @Test
    public void hotelRoomShouldBeSaved() throws Exception {
        HotelRoom hotelRoom = repository.save(new HotelRoom(0, "desc"));

        assertNotNull(hotelRoom);
        assertNotNull(hotelRoom.getId());
        assertNotNull(hotelRoom.getNumber());
        assertNotNull(hotelRoom.getDescription());
        assertEquals(Status.FREE, hotelRoom.getStatus());
    }

    @Test
    public void findAllShouldReturnAllRooms() throws Exception {
        List<HotelRoom> rooms = repository.findAll();

        assertFalse(rooms.isEmpty());
        assertEquals(testRoom, rooms.get(0));
    }

    @Test
    public void findByNumberShouldReturnExistentRooms() throws Exception {
        List<HotelRoom> existentRooms = repository.findByNumber(testRoom.getNumber());

        assertFalse(existentRooms.isEmpty());
        assertEquals(testRoom, existentRooms.get(0));
    }

    @Test
    public void hotelRoomShouldBeUpdated() throws Exception {
        testRoom.book();

        HotelRoom updatedRoom = repository.save(testRoom);

        assertTrue(updatedRoom.isBooked());
    }

    @Test
    public void hotelRoomShouldBeDeleted() throws Exception {
        repository.delete(testRoom);

        assertFalse(repository.exists(testRoom.getId()));
    }
}