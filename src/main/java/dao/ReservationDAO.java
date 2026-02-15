package dao;

import model.Reservation;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
            stmt.setInt(7, r.getTotalAmount());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Get all reservations
    public List<Reservation> getAllReservations() {
        List<Reservation> list = new ArrayList<>();
        String sql = "SELECT * FROM Reservations";
        try {
            Connection conn = DatabaseConnection.getInstance();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Reservation r = new Reservation();
                r.setReservationId(rs.getInt("reservation_id"));
                r.setGuestName(rs.getString("guest_name"));
                r.setAddress(rs.getString("address"));
                r.setContact(rs.getString("contact_no"));
                r.setRoomType(rs.getString("room_type"));
                r.setCheckIn(rs.getDate("checkin_date").toLocalDate());
                r.setCheckOut(rs.getDate("checkout_date").toLocalDate());
                r.setTotalAmount(rs.getInt("total_amount"));
                list.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ✅ Get a reservation by ID
    public Reservation getReservationById(int id) {
        String sql = "SELECT * FROM Reservations WHERE reservation_id=?";
        try {
            Connection conn = DatabaseConnection.getInstance();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Reservation r = new Reservation();
                r.setReservationId(rs.getInt("reservation_id"));
                r.setGuestName(rs.getString("guest_name"));
                r.setAddress(rs.getString("address"));
                r.setContact(rs.getString("contact_no"));
                r.setRoomType(rs.getString("room_type"));
                r.setCheckIn(rs.getDate("checkin_date").toLocalDate());
                r.setCheckOut(rs.getDate("checkout_date").toLocalDate());
                r.setTotalAmount(rs.getInt("total_amount"));
                return r;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ✅ Update reservation
    public boolean updateReservation(Reservation r) {
        String sql = "UPDATE Reservations SET guest_name=?, address=?, contact_no=?, room_type=?, checkin_date=?, checkout_date=?, total_amount=? WHERE reservation_id=?";
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
            stmt.setInt(8, r.getReservationId());

            int updated = stmt.executeUpdate();
            return updated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
