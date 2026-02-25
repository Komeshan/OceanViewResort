package dao;

import model.Reservation;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {

    // ================= ADD =================
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
            stmt.setDate(5, Date.valueOf(r.getCheckIn()));
            stmt.setDate(6, Date.valueOf(r.getCheckOut()));
            stmt.setInt(7, r.getTotalAmount());

            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    // ================= VIEW ALL =================
    public List<Reservation> getAllReservations() {

        List<Reservation> list = new ArrayList<>();

        String sql = "SELECT * FROM Reservations";

        try {
            Connection conn = DatabaseConnection.getInstance();
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

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

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    // ================= SMART SEARCH =================
    public List<Reservation> searchReservations(
            String name,
            String room,
            String fromDate,
            String toDate) {

        List<Reservation> list = new ArrayList<>();

        StringBuilder sql =
                new StringBuilder("SELECT * FROM Reservations WHERE 1=1");

        List<Object> params = new ArrayList<>();

        // Name filter
        if (name != null && !name.trim().isEmpty()) {
            sql.append(" AND guest_name LIKE ?");
            params.add("%" + name + "%");
        }

        // Room filter
        if (room != null && !room.trim().isEmpty()) {
            sql.append(" AND room_type = ?");
            params.add(room);
        }

        // From date filter
        if (fromDate != null && !fromDate.trim().isEmpty()) {
            sql.append(" AND checkin_date >= ?");
            params.add(fromDate);
        }

        // To date filter
        if (toDate != null && !toDate.trim().isEmpty()) {
            sql.append(" AND checkout_date <= ?");
            params.add(toDate);
        }

        try {
            Connection conn = DatabaseConnection.getInstance();
            PreparedStatement stmt =
                    conn.prepareStatement(sql.toString());

            // Set parameters dynamically
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

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

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    // ================= GET BY ID =================
    public Reservation getReservationById(int id) {

        Reservation r = null;

        String sql = "SELECT * FROM Reservations WHERE reservation_id=?";

        try {
            Connection conn = DatabaseConnection.getInstance();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                r = new Reservation();

                r.setReservationId(rs.getInt("reservation_id"));
                r.setGuestName(rs.getString("guest_name"));
                r.setAddress(rs.getString("address"));
                r.setContact(rs.getString("contact_no"));
                r.setRoomType(rs.getString("room_type"));
                r.setCheckIn(rs.getDate("checkin_date").toLocalDate());
                r.setCheckOut(rs.getDate("checkout_date").toLocalDate());
                r.setTotalAmount(rs.getInt("total_amount"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return r;
    }


    // ================= UPDATE =================
    public boolean updateReservation(Reservation r) {

        String sql =
                "UPDATE Reservations SET " +
                        "guest_name=?, address=?, contact_no=?, room_type=?, " +
                        "checkin_date=?, checkout_date=?, total_amount=? " +
                        "WHERE reservation_id=?";

        try {
            Connection conn = DatabaseConnection.getInstance();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, r.getGuestName());
            stmt.setString(2, r.getAddress());
            stmt.setString(3, r.getContact());
            stmt.setString(4, r.getRoomType());
            stmt.setDate(5, Date.valueOf(r.getCheckIn()));
            stmt.setDate(6, Date.valueOf(r.getCheckOut()));
            stmt.setInt(7, r.getTotalAmount());
            stmt.setInt(8, r.getReservationId());

            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    // ================= DELETE =================
    public boolean deleteReservation(int id) {
        String sql = "DELETE FROM Reservations WHERE reservation_id=?";

        try {
            Connection conn = DatabaseConnection.getInstance();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, id);

            // Capture the number of rows actually deleted
            int rowsDeleted = stmt.executeUpdate();

            // Return true only if 1 or more rows were removed
            return rowsDeleted > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
