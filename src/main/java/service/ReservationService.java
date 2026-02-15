package service;

import dao.ReservationDAO;
import model.Reservation;

import java.time.temporal.ChronoUnit;

public class ReservationService {

    private ReservationDAO dao = new ReservationDAO();

    public boolean createReservation(Reservation r) {
        // Automatically calculate totalAmount
        long nights = ChronoUnit.DAYS.between(r.getCheckIn(), r.getCheckOut());
        int pricePerNight = 5000; // You can adjust per room type if you want
        r.setTotalAmount((int) nights * pricePerNight);

        return dao.addReservation(r);
    }
}
