package controller;

import dao.ReservationDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/DeleteReservationServlet")
public class DeleteReservationServlet extends HttpServlet {

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

        String idParam = request.getParameter("id");
        if (idParam != null) {
            int reservationId = Integer.parseInt(idParam);
            boolean success = dao.deleteReservation(reservationId);

            if (success) {
                response.sendRedirect("ViewReservationsServlet");
            } else {
                response.getWriter().println("<h2>Failed to delete reservation!</h2>");
            }
        } else {
            response.getWriter().println("<h2>Invalid reservation ID!</h2>");
        }
    }
}
