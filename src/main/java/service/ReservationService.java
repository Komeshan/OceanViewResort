package service;

import dao.ReservationDAO;
import model.Reservation;

import java.time.temporal.ChronoUnit;
import java.util.List;

public class ReservationService {

    private ReservationDAO dao = new ReservationDAO();

    // Create reservation (calculates total amount)
    public boolean createReservation(Reservation r) {
        long nights = ChronoUnit.DAYS.between(r.getCheckIn(), r.getCheckOut());
        int pricePerNight = 5000; // adjust based on room type if needed
        r.setTotalAmount((int) nights * pricePerNight);

        return dao.addReservation(r);
    }

    // Get all reservations
    public List<Reservation> getAllReservations() {
        return dao.getAllReservations();
    }
}
