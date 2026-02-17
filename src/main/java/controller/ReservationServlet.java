package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.Reservation;
import service.ReservationService;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

@WebServlet("/ReservationServlet")
public class ReservationServlet extends HttpServlet {

    private ReservationService service = new ReservationService();

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        // ----- SESSION CHECK -----
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.html");
            return;
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<h1>Reservation Page</h1>");
        out.println("<a href='ViewReservationsServlet'>View All Reservations</a> | ");
        out.println("<a href='" + request.getContextPath() + "/LogoutServlet'>Logout</a><br><br>");

        // ----- GET DATES -----
        LocalDate checkIn = LocalDate.parse(request.getParameter("in"));
        LocalDate checkOut = LocalDate.parse(request.getParameter("out"));
        LocalDate today = LocalDate.now();

        // ----- VALIDATION -----
        if (checkIn.isBefore(today)) {

            out.println("<h2 style='color:red'>❌ Check-in cannot be before today!</h2>");
            out.println("<a href='Reservation.html'>Go Back</a>");
            return;

        }

        if (checkOut.isBefore(checkIn)) {

            out.println("<h2 style='color:red'>❌ Check-out cannot be before Check-in!</h2>");
            out.println("<a href='Reservation.html'>Go Back</a>");
            return;

        }

        // ----- CREATE RESERVATION -----
        Reservation r = new Reservation();

        r.setGuestName(request.getParameter("name"));
        r.setAddress(request.getParameter("address"));
        r.setContact(request.getParameter("contact"));
        r.setRoomType(request.getParameter("room"));
        r.setCheckIn(checkIn);
        r.setCheckOut(checkOut);

        boolean result = service.createReservation(r);

        if (result) {
            out.println("<h2 style='color:green'>✅ Reservation Saved!</h2>");
        } else {
            out.println("<h2 style='color:red'>❌ Error Saving Reservation</h2>");
        }

        out.println("<a href='Reservation.html'>Make Another Reservation</a>");
    }
}
