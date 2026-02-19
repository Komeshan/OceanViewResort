package controller;

import dao.ReservationDAO;
import model.Reservation;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/ViewReservationsServlet")
public class ViewReservationsServlet extends HttpServlet {

    private ReservationDAO dao = new ReservationDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // SESSION CHECK
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.html");
            return;
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html><head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>All Reservations</title>");
        out.println("<link rel='stylesheet' type='text/css' href='" + request.getContextPath() + "/css/style.css'>");
        out.println("</head><body>");

        // ---------------- Header / Navbar ----------------
        out.println("<header>");
        out.println("<h1>Ocean View Resort</h1>");
        out.println("<nav>");
        out.println("<a href='Reservation.html'>New Reservation</a>");
        out.println("<a href='help.html'>Help</a>");
        out.println("<a href='LogoutServlet'>Logout</a>");
        out.println("</nav>");
        out.println("</header>");

        out.println("<div class='view-container'>");

        // ---------------- Page Title & Make New Reservation ----------------
        out.println("<h2 class='view-title'>All Reservations</h2>");

        // ---------------- Search Form ----------------
        out.println("<form class='search-form' action='ViewReservationsServlet' method='get'>");
        out.println("<input type='text' name='name' placeholder='Guest Name'>");
        out.println("<input type='text' name='room' placeholder='Room Type'>");
        out.println("<input type='date' name='in' placeholder='Check-In'>");
        out.println("<input type='date' name='out' placeholder='Check-Out'>");
        out.println("<input type='submit' value='Search'>");
        out.println("</form>");

        // ---------------- Get Search Parameters ----------------
        String name = request.getParameter("name");
        String room = request.getParameter("room");
        String checkIn = request.getParameter("in");
        String checkOut = request.getParameter("out");

        List<Reservation> reservations;
        if ((name != null && !name.isEmpty()) ||
                (room != null && !room.isEmpty()) ||
                (checkIn != null && !checkIn.isEmpty()) ||
                (checkOut != null && !checkOut.isEmpty())) {

            reservations = dao.searchReservations(name, room, checkIn, checkOut);

        } else {
            reservations = dao.getAllReservations();
        }

        // ---------------- Display Table ----------------
        if (reservations.isEmpty()) {
            out.println("<h3>No reservations found.</h3>");
        } else {
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>ID</th><th>Guest</th><th>Address</th><th>Contact</th>");
            out.println("<th>Room</th><th>Check-In</th><th>Check-Out</th><th>Total</th><th>Actions</th>");
            out.println("</tr>");

            for (Reservation r : reservations) {
                out.println("<tr>");
                out.println("<td>" + r.getReservationId() + "</td>");
                out.println("<td>" + r.getGuestName() + "</td>");
                out.println("<td>" + r.getAddress() + "</td>");
                out.println("<td>" + r.getContact() + "</td>");
                out.println("<td>" + r.getRoomType() + "</td>");
                out.println("<td>" + r.getCheckIn() + "</td>");
                out.println("<td>" + r.getCheckOut() + "</td>");
                out.println("<td>" + r.getTotalAmount() + "</td>");
                out.println("<td>");
                out.println("<a href='EditReservationServlet?id=" + r.getReservationId() + "' class='btn-small btn-edit'>Edit</a>");
                out.println("<a href='DeleteReservationServlet?id=" + r.getReservationId() + "' class='btn-small btn-delete'>Delete</a>");
                out.println("</td>");
                out.println("</tr>");
            }
            out.println("</table>");
        }

        out.println("</div>"); // end container
        out.println("</body></html>");
    }

}
