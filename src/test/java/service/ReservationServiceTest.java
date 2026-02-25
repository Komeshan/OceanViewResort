package service;

import model.Reservation;
import org.junit.jupiter.api.Test;
import service.ReservationService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationServiceTest {

    // TC01
    @Test
    void testSingleRoomPrice() {
        Reservation r = baseReservation("Single", 2);
        new ReservationService().createReservation(r);
        assertEquals(10000, r.getTotalAmount());
    }

    // TC02
    @Test
    void testDoubleRoomPrice() {
        Reservation r = baseReservation("Double", 3);
        new ReservationService().createReservation(r);
        assertEquals(24000, r.getTotalAmount());
    }

    // TC03
    @Test
    void testSuiteRoomPrice() {
        Reservation r = baseReservation("Suite", 1);
        new ReservationService().createReservation(r);
        assertEquals(12000, r.getTotalAmount());
    }

    // TC04
    @Test
    void testInvalidRoomDefaults() {
        Reservation r = baseReservation("Unknown", 2);
        new ReservationService().createReservation(r);
        assertEquals(10000, r.getTotalAmount());
    }

    // TC05
    @Test
    void testLowercaseRoom() {
        Reservation r = baseReservation("single", 1);
        new ReservationService().createReservation(r);
        assertEquals(5000, r.getTotalAmount());
    }

    // TC06
    @Test
    void testUppercaseRoom() {
        Reservation r = baseReservation("DOUBLE", 1);
        new ReservationService().createReservation(r);
        assertEquals(8000, r.getTotalAmount());
    }

    // TC07
    @Test
    void testLongStay() {
        Reservation r = baseReservation("Suite", 10);
        new ReservationService().createReservation(r);
        assertEquals(120000, r.getTotalAmount());
    }


    // TC08
    @Test
    void testZeroNights() {
        Reservation r = baseReservation("Single", 0);
        new ReservationService().createReservation(r);
        assertEquals(0, r.getTotalAmount());
    }

    // TC09
    @Test
    void testOneNightStay() {
        Reservation r = baseReservation("Double", 1);
        new ReservationService().createReservation(r);
        assertEquals(8000, r.getTotalAmount());
    }

    // TC10
    @Test
    void testMultipleCalculations() {
        Reservation r = baseReservation("Single", 5);
        new ReservationService().createReservation(r);
        assertTrue(r.getTotalAmount() > 0);
    }

    // Helper
    private Reservation baseReservation(String room, int nights) {
        Reservation r = new Reservation();

        r.setGuestName("Test");
        r.setRoomType(room);
        r.setCheckIn(LocalDate.now().plusDays(1));
        r.setCheckOut(LocalDate.now().plusDays(1 + nights));

        return r;
    }
}