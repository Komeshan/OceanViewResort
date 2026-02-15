package controller;

import service.ReservationService;
import model.Reservation;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/ViewReservationsServlet")
public class ViewReservationsServlet extends HttpServlet {

    private ReservationService service = new ReservationService();

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.html");
            return;
        }

        List<Reservation> reservations = service.getAllReservations();

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<h1>All Reservations</h1>");
        out.println("<table border='1'>");
        out.println("<tr><th>ID</th><th>Guest</th><th>Address</th><th>Contact</th><th>Room</th><th>Check-In</th><th>Check-Out</th><th>Total</th></tr>");

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
            out.println("</tr>");
        }

        out.println("</table>");
    }
}
