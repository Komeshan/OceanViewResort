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

        out.println("<h1>All Reservations</h1>");

        // LINKS
        out.println("<a href='Reservation.html'>Make New Reservation</a> | ");
        out.println("<a href='" + request.getContextPath() + "/LogoutServlet'>Logout</a><br><br>");

        // SEARCH FORM
        out.println("<form action='ViewReservationsServlet' method='get'>");
        out.println("Name: <input type='text' name='name'> ");
        out.println("Room: <input type='text' name='room'> ");
        out.println("Check-In: <input type='date' name='in'> ");
        out.println("Check-Out: <input type='date' name='out'> ");
        out.println("<input type='submit' value='Search'>");
        out.println("</form><br>");

        // GET SEARCH PARAMETERS
        String name = request.getParameter("name");
        String room = request.getParameter("room");
        String checkIn = request.getParameter("in");
        String checkOut = request.getParameter("out");

        List<Reservation> reservations;

        // SEARCH OR ALL
        if ((name != null && !name.isEmpty()) ||
                (room != null && !room.isEmpty()) ||
                (checkIn != null && !checkIn.isEmpty()) ||
                (checkOut != null && !checkOut.isEmpty())) {

            reservations = dao.searchReservations(name, room, checkIn, checkOut);

        } else {
            reservations = dao.getAllReservations();
        }

        // DISPLAY
        if (reservations.isEmpty()) {
            out.println("<h3>No reservations found.</h3>");
        } else {
            out.println("<table border='1'>");
            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>Guest</th>");
            out.println("<th>Address</th>");
            out.println("<th>Contact</th>");
            out.println("<th>Room</th>");
            out.println("<th>Check-In</th>");
            out.println("<th>Check-Out</th>");
            out.println("<th>Total</th>");
            out.println("<th>Actions</th>");
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
                out.println("<a href='EditReservationServlet?id=" + r.getReservationId() + "'>Edit</a> | ");
                out.println("<a href='DeleteReservationServlet?id=" + r.getReservationId() + "'>Delete</a>");
                out.println("</td>");
                out.println("</tr>");
            }

            out.println("</table>");
        }
    }
}
