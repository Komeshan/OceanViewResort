package dao;

import model.Reservation;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {

    // Add a reservation
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
            stmt.setInt(7, r.getTotalAmount());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all reservations
    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT reservation_id, guest_name, address, contact_no, room_type, checkin_date, checkout_date, total_amount FROM Reservations";

        try {
            Connection conn = DatabaseConnection.getInstance();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Reservation r = new Reservation();
                r.setReservationId(rs.getInt("reservation_id"));
                r.setGuestName(rs.getString("guest_name"));
                r.setAddress(rs.getString("address"));
                r.setContact(rs.getString("contact_no"));
                r.setRoomType(rs.getString("room_type"));
                r.setCheckIn(rs.getDate("checkin_date").toLocalDate());
                r.setCheckOut(rs.getDate("checkout_date").toLocalDate());
                r.setTotalAmount(rs.getInt("total_amount"));

                reservations.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservations;
    }
}
