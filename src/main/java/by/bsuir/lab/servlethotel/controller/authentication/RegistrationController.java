package by.bsuir.lab.servlethotel.controller.authentication;

import by.bsuir.lab.servlethotel.entity.security.User;
import by.bsuir.lab.servlethotel.service.iface.IAuthenticationService;
import by.bsuir.lab.servlethotel.util.Errors;
import by.bsuir.lab.servlethotel.util.ServiceLocator;
import by.bsuir.lab.servlethotel.entity.security.UserRole;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Registration", value = "/registration")
public class RegistrationController extends HttpServlet {

    private final IAuthenticationService authenticationService = ServiceLocator.get(IAuthenticationService.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("registration.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = new User();
        user.setUsername(request.getParameter("username"));
        user.setPassword(request.getParameter("password"));
        user.setRole(authenticationService.getRole(UserRole.USER));
        Errors errors = authenticationService.register(user);

        if (errors.isEmpty()) {
            response.sendRedirect("login");
        } else {
            response.setContentType("text/html");
            RequestDispatcher rd = request.getRequestDispatcher("registration.jsp");
            rd.include(request, response);

            PrintWriter pw = response.getWriter();
            errors.forEach(error -> pw.println("<p style='color: red'>" + error + "</p>"));
        }
    }
}
