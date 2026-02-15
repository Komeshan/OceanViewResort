package controller;

import model.Reservation;
import service.ReservationService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

@WebServlet("/ReservationServlet")
public class ReservationServlet extends HttpServlet {

    private ReservationService service = new ReservationService();

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("Servlet reached!"); // Debug

        Reservation r = new Reservation();
        r.setGuestName(request.getParameter("name"));
        r.setAddress(request.getParameter("address"));
        r.setContact(request.getParameter("contact"));
        r.setRoomType(request.getParameter("room"));
        r.setCheckIn(LocalDate.parse(request.getParameter("in")));
        r.setCheckOut(LocalDate.parse(request.getParameter("out")));

        boolean result = service.createReservation(r);

        if(result){
            out.println("<h2>Reservation Saved! Total: " + r.getTotalAmount() + "</h2>");
        } else {
            out.println("<h2>Error Saving Reservation</h2>");
        }
    }
}
