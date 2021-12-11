package by.bsuir.lab.servlethotel.controller.authentication;

import by.bsuir.lab.servlethotel.service.authentication.Authentication;
import by.bsuir.lab.servlethotel.service.authentication.exception.AuthenticationException;
import by.bsuir.lab.servlethotel.service.iface.IAuthenticationService;
import by.bsuir.lab.servlethotel.util.Attribute;
import by.bsuir.lab.servlethotel.util.ServiceLocator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Login", value = "/login")
public class LoginController extends HttpServlet {

    private final IAuthenticationService authenticationService = ServiceLocator.get(IAuthenticationService.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Authentication authentication = authenticationService.login(request.getParameter("username"), request.getParameter("password"));
            request.getSession().setAttribute(Attribute.AUTHENTICATION, authentication.toString());
            response.sendRedirect("rooms");
        } catch (AuthenticationException e) {
            response.setContentType("text/html");
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            rd.include(request, response);

            PrintWriter pw = response.getWriter();
            pw.println("<p style='color: red'>" + e.getMessage() + "</p>");
        }
    }
}
