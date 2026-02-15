package dao;

import model.Reservation;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReservationDAO {

    public boolean addReservation(Reservation r) {
        String sql = "INSERT INTO Reservations " +
                "(guest_name, address, contact_no, room_type, checkin_date, checkout_date, total_amount) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = DatabaseConnection.getInstance();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, r.getGuestName());
            stmt.setString(2, r.getAddress());
            stmt.setString(3, r.getContact());
            stmt.setString(4, r.getRoomType());
            stmt.setDate(5, java.sql.Date.valueOf(r.getCheckIn()));
            stmt.setDate(6, java.sql.Date.valueOf(r.getCheckOut()));
            stmt.setInt(7, r.getTotalAmount()); // ✅ totalAmount parameter

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
