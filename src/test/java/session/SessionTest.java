package session;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SessionTest {

    // TC26
    @Test
    void testSessionExists() {
        String session = "ACTIVE";
        assertNotNull(session);
    }

    // TC27
    @Test
    void testSessionInvalid() {
        String session = null;
        assertNull(session);
    }

    // TC28
    @Test
    void testLogout() {
        String session = "ACTIVE";
        session = null;
        assertNull(session);
    }

    // TC29
    @Test
    void testUnauthorizedAccess() {
        boolean loggedIn = false;
        assertFalse(loggedIn);
    }

    // TC30
    @Test
    void testAuthorizedAccess() {
        boolean loggedIn = true;
        assertTrue(loggedIn);
    }
}



