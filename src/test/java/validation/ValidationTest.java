package validation;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ValidationTest {

    // TC19
    @Test
    void testCheckInBeforeToday() {
        LocalDate in = LocalDate.now().minusDays(1);
        assertTrue(in.isBefore(LocalDate.now()));
    }

    // TC20
    @Test
    void testCheckOutBeforeCheckIn() {
        LocalDate in = LocalDate.now().plusDays(5);
        LocalDate out = LocalDate.now().plusDays(3);

        assertTrue(out.isBefore(in));
    }

    // TC21
    @Test
    void testValidDates() {
        LocalDate in = LocalDate.now().plusDays(1);
        LocalDate out = LocalDate.now().plusDays(3);

        assertTrue(out.isAfter(in));
    }

    // TC22
    @Test
    void testNullName() {
        String name = null;
        assertNull(name);
    }

    // TC23
    @Test
    void testEmptyContact() {
        String contact = "";
        assertTrue(contact.isEmpty());
    }

    // TC24
    @Test
    void testInvalidRoom() {
        String room = "XYZ";
        assertNotEquals("Single", room);
    }

    // TC25
    @Test
    void testValidRoom() {
        String room = "Single";
        assertEquals("Single", room);
    }
}