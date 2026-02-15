package controller;

import dao.ReservationDAO;
import model.Reservation;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/ViewReservationsServlet")
public class ViewReservationsServlet extends HttpServlet {

    private ReservationDAO dao = new ReservationDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ----- SESSION CHECK -----
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.html"); // User not logged in
            return;
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<h1>All Reservations</h1>");
        out.println("<a href='/OceanViewResortSystem/reservation.html'>Make New Reservation</a> | ");
        out.println("<a href='/OceanViewResortSystem/'>Logout</a><br><br>");

        // Get reservations from DAO
        List<Reservation> reservations = dao.getAllReservations();

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
                out.println("</tr>");
            }

            out.println("</table>");
        }
    }
}
