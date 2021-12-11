package by.bsuir.lab.servlethotel.filter.security;

import by.bsuir.lab.servlethotel.service.authentication.Authentication;
import by.bsuir.lab.servlethotel.service.iface.IAuthenticationService;
import by.bsuir.lab.servlethotel.util.Attribute;
import by.bsuir.lab.servlethotel.util.ServiceLocator;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class SecurityFilter implements Filter {

    private final IAuthenticationService authenticationService = ServiceLocator.get(IAuthenticationService.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if(refreshAuthentication(httpRequest)) {
            if(isLoginPage(httpRequest) || isRegistrationPage(httpRequest)) {
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/rooms");
                return;
            }
        } else {
            if(!(isLoginPage(httpRequest) || isRegistrationPage(httpRequest))) {
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    private boolean isRegistrationPage(HttpServletRequest request) {
        return isRequestTo(request, "/registration");
    }

    private boolean isLoginPage(HttpServletRequest request) {
        return isRequestTo(request, "/login");
    }

    private boolean isRequestTo(HttpServletRequest request, String url) {
        return request.getRequestURI()
                .substring(request.getContextPath().length())
                .replaceAll("[/]+$", "")
                .equals(url);
    }

    private boolean refreshAuthentication(HttpServletRequest request) {
        Object attribute = request.getSession().getAttribute(Attribute.AUTHENTICATION);
        if (attribute == null) {
            return false;
        }

        Authentication authentication = new Authentication((String) attribute);
        Authentication freshAuthentication = authenticationService.authorize(authentication);
        if (freshAuthentication == null) {
            return false;
        }

        request.getSession().setAttribute(Attribute.AUTHENTICATION, freshAuthentication.toString());
        return true;
    }
}
