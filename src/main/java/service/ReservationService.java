package service;

import dao.ReservationDAO;
import model.Reservation;

import java.time.temporal.ChronoUnit;

public class ReservationService {

    private ReservationDAO dao = new ReservationDAO();

    public boolean createReservation(Reservation r) {
        // Calculate nights
        long nights = ChronoUnit.DAYS.between(r.getCheckIn(), r.getCheckOut());

        // Price per room type
        int pricePerNight;
        switch (r.getRoomType().toLowerCase()) {
            case "single":
                pricePerNight = 5000;
                break;
            case "double":
                pricePerNight = 8000;
                break;
            case "suite":
                pricePerNight = 12000;
                break;
            default:
                pricePerNight = 5000; // fallback
        }

        r.setTotalAmount((int) nights * pricePerNight);

        return dao.addReservation(r);
    }
}
