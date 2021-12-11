package by.bsuir.lab.servlethotel.filter.localization;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/*")
public class LocalizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        httpRequest.setAttribute("language", httpRequest.getParameter("language"));
        chain.doFilter(request, response);
    }
}
