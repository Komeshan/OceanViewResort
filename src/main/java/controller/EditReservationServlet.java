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

        // Show pre-filled form
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h1>Edit Reservation</h1>");
        out.println("<form action='EditReservationServlet' method='post'>");
        out.println("<input type='hidden' name='id' value='" + r.getReservationId() + "'>");
        out.println("Name: <input type='text' name='name' value='" + r.getGuestName() + "' required><br><br>");
        out.println("Address: <input type='text' name='address' value='" + r.getAddress() + "' required><br><br>");
        out.println("Contact: <input type='text' name='contact' value='" + r.getContact() + "' required><br><br>");
        out.println("Room: <input type='text' name='room' value='" + r.getRoomType() + "' required><br><br>");
        out.println("Check-In: <input type='date' name='in' value='" + r.getCheckIn() + "' required><br><br>");
        out.println("Check-Out: <input type='date' name='out' value='" + r.getCheckOut() + "' required><br><br>");
        out.println("<input type='submit' value='Update Reservation'>");
        out.println("</form>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        Reservation r = new Reservation();
        r.setReservationId(id);
        r.setGuestName(request.getParameter("name"));
        r.setAddress(request.getParameter("address"));
        r.setContact(request.getParameter("contact"));
        r.setRoomType(request.getParameter("room"));
        r.setCheckIn(java.time.LocalDate.parse(request.getParameter("in")));
        r.setCheckOut(java.time.LocalDate.parse(request.getParameter("out")));

        // Recalculate total amount
        long nights = java.time.temporal.ChronoUnit.DAYS.between(r.getCheckIn(), r.getCheckOut());
        int pricePerNight = 5000;
        r.setTotalAmount((int) nights * pricePerNight);

        boolean result = dao.updateReservation(r);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        if (result) {
            out.println("<h2>Reservation Updated Successfully!</h2>");
        } else {
            out.println("<h2>Error Updating Reservation!</h2>");
        }
        out.println("<a href='ViewReservationsServlet'>Back to All Reservations</a>");
    }
}
