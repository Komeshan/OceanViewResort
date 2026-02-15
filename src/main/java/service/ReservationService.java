package service;

import dao.ReservationDAO;
import model.Reservation;

public class ReservationService {

    private ReservationDAO dao = new ReservationDAO();

    public boolean createReservation(Reservation r) {

        if (r.getGuestName() == null || r.getGuestName().isEmpty()) {
            return false;
        }

        if (r.getContact() == null || r.getContact().isEmpty()) {
            return false;
        }

        return dao.addReservation(r);
    }
}
