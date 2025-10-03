package ru.orissemesterwork.common.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebFilter("/*")
public class ExceptionHandlingFilter extends HttpFilter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        try {
            chain.doFilter(request, response);
        } catch (IllegalArgumentException e) {
            forwardToError(req, resp, 400, e.getMessage());
        } catch (RuntimeException e) {
            forwardToError(req, resp, 500, "Внутренняя ошибка сервера: " + e.getMessage());
        }
    }

    private void forwardToError(HttpServletRequest req, HttpServletResponse resp, int statusCode, String message) throws ServletException, IOException {
        if (!resp.isCommitted()) {
            req.setAttribute("errorCode", statusCode);
            req.setAttribute("errorMessage", message);
            req.getRequestDispatcher("/WEB-INF/view/error/error.jsp").forward(req, resp);
        }
    }
}
