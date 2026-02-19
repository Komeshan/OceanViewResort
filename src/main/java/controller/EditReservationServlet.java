package controller;

import model.Reservation;
import service.ReservationService;
import dao.ReservationDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/EditReservationServlet")
public class EditReservationServlet extends HttpServlet {

    private ReservationDAO dao = new ReservationDAO();
    private ReservationService service = new ReservationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.sendRedirect("ViewReservationsServlet");
            return;
        }

        int id = Integer.parseInt(idParam);
        Reservation r = dao.getReservationById(id);

        if (r == null) {
            response.sendRedirect("ViewReservationsServlet");
            return;
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html><head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Edit Reservation</title>");
        out.println("<link rel='stylesheet' type='text/css' href='" + request.getContextPath() + "/css/style.css'>");
        out.println("</head><body>");

        // ---------------- Header / Navbar ----------------
        out.println("<header>");
        out.println("<h1>Ocean View Resort</h1>");
        out.println("<nav>");
        out.println("<a href='ViewReservationsServlet'>View Reservations</a>");
        out.println("<a href='Reservation.html'>New Reservation</a>");
        out.println("<a href='help.html'>Help</a>");
        out.println("<a href='LogoutServlet'>Logout</a>");
        out.println("</nav>");
        out.println("</header>");

        out.println("<div class='container'>");

        // ---------------- Page Title & Back Button ----------------
        out.println("<h2>Edit Reservation</h2>");
        out.println("<a href='ViewReservationsServlet' class='back-btn'>← Back to All Reservations</a>");

        // ---------------- Edit Form ----------------
        out.println("<form action='EditReservationServlet' method='post'>");
        out.println("<input type='hidden' name='id' value='" + r.getReservationId() + "'>");
        out.println("<label>Name:</label>");
        out.println("<input type='text' name='name' value='" + r.getGuestName() + "' required>");
        out.println("<label>Address:</label>");
        out.println("<input type='text' name='address' value='" + r.getAddress() + "' required>");
        out.println("<label>Contact:</label>");
        out.println("<input type='text' name='contact' value='" + r.getContact() + "' required>");
        out.println("<label>Room:</label>");
        out.println("<input type='text' name='room' value='" + r.getRoomType() + "' required>");
        out.println("<label>Check-In:</label>");
        out.println("<input type='date' name='in' value='" + r.getCheckIn() + "' required>");
        out.println("<label>Check-Out:</label>");
        out.println("<input type='date' name='out' value='" + r.getCheckOut() + "' required>");
        out.println("<input type='submit' value='Update Reservation'>");
        out.println("</form>");

        out.println("</div>"); // end container
        out.println("</body></html>");
    }

}
