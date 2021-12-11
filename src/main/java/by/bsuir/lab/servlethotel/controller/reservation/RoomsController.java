package by.bsuir.lab.servlethotel.controller.reservation;

import by.bsuir.lab.servlethotel.service.authentication.Authentication;
import by.bsuir.lab.servlethotel.service.iface.IRoomService;
import by.bsuir.lab.servlethotel.util.Attribute;
import by.bsuir.lab.servlethotel.util.ServiceLocator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RoomsController", value = "/rooms")
public class RoomsController extends HttpServlet {

    private final IRoomService roomService = ServiceLocator.get(IRoomService.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("roomsList", roomService.getAll());
        request.setAttribute("username", extractUsername(request));
        request.getRequestDispatcher("rooms.jsp").forward(request, response);
    }

    private String extractUsername(HttpServletRequest request) {
        return new Authentication((String) request.getSession().getAttribute(Attribute.AUTHENTICATION)).getUsername();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameterMap().containsKey("rent")) {
            roomService.rent(extractUsername(request), Integer.valueOf(request.getParameterMap().get("rent")[0]));
        } else if (request.getParameterMap().containsKey("unrent")) {
            roomService.unrent(extractUsername(request), Integer.valueOf(request.getParameterMap().get("unrent")[0]));
        } else if (request.getParameterMap().containsKey("logout")) {
            request.getSession().invalidate();
        }
    }
}
