package by.bsuir.lab.servlethotel.service.iface;

import by.bsuir.lab.servlethotel.service.authentication.Authentication;
import by.bsuir.lab.servlethotel.service.authentication.exception.AuthenticationException;
import by.bsuir.lab.servlethotel.util.Errors;
import by.bsuir.lab.servlethotel.entity.security.Role;
import by.bsuir.lab.servlethotel.entity.security.User;
import by.bsuir.lab.servlethotel.entity.security.UserRole;

public interface IAuthenticationService {

    Authentication login(String username, String password) throws AuthenticationException;

    Authentication authorize(Authentication authentication);

    Errors register(User user);

    Role getRole(UserRole role);
}
