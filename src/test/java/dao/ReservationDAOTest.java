package dao;

import model.Reservation;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationDAOTest {

    ReservationDAO dao = new ReservationDAO();

    // TC11
    @Test
    void testAddReservation() {
        Reservation r = createReservation();
        assertTrue(dao.addReservation(r));
    }

    // TC12
    @Test
    void testGetAllReservations() {
        List<Reservation> list = dao.getAllReservations();
        assertNotNull(list);
    }

    // TC13
    @Test
    void testGetByIdInvalid() {
        Reservation r = dao.getReservationById(-1);
        assertNull(r);
    }

    // TC14
    @Test
    void testSearchByName() {
        List<Reservation> list =
                dao.searchReservations("Test", null, null, null);
        assertNotNull(list);
    }

    // TC15
    @Test
    void testSearchByRoom() {
        List<Reservation> list =
                dao.searchReservations(null, "Single", null, null);
        assertNotNull(list);
    }

    // TC16
    @Test
    void testSearchEmpty() {
        List<Reservation> list =
                dao.searchReservations(null, null, null, null);
        assertNotNull(list);
    }

    // TC17
    @Test
    void testUpdateReservation() {

        Reservation r = createReservation();
        dao.addReservation(r);

        r.setGuestName("Updated");

        assertTrue(dao.updateReservation(r));
    }

    // TC18
    @Test
    void testDeleteInvalid() {
        assertFalse(dao.deleteReservation(-1));
    }

    // Helper
    private Reservation createReservation() {

        Reservation r = new Reservation();

        r.setGuestName("Test");
        r.setAddress("Colombo");
        r.setContact("0771234567");
        r.setRoomType("Single");

        r.setCheckIn(LocalDate.now().plusDays(2));
        r.setCheckOut(LocalDate.now().plusDays(4));

        r.setTotalAmount(10000);

        return r;
    }
}