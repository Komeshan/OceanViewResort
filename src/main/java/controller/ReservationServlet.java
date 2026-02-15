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
            response.sendRedirect("login.html"); // User not logged in
            return;
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<h1>Reservation Page</h1>");
        out.println("<a href='ViewReservationsServlet'>View All Reservations</a> | ");
        out.println("<a href='LogoutServlet'>Logout</a><br><br>");

        Reservation r = new Reservation();

        r.setGuestName(request.getParameter("name"));
        r.setAddress(request.getParameter("address"));
        r.setContact(request.getParameter("contact"));
        r.setRoomType(request.getParameter("room"));
        r.setCheckIn(LocalDate.parse(request.getParameter("in")));
        r.setCheckOut(LocalDate.parse(request.getParameter("out")));

        boolean result = service.createReservation(r);

        if(result){
            out.println("<h2>Reservation Saved!</h2>");
        } else {
            out.println("<h2>Error Saving Reservation</h2>");
        }

        out.println("<a href='Reservation.html'>Make Another Reservation</a>");
    }
}
