package ru.orissemesterwork.api.error.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/api/error")
public class ErrorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String codeParam = req.getParameter("code");
        String messageParam = req.getParameter("message");

        int statusCode = (codeParam != null) ? Integer.parseInt(codeParam) : 500;
        String message = (messageParam != null) ? messageParam : "Произошла неизвестная ошибка.";

        req.setAttribute("errorCode", statusCode);
        req.setAttribute("errorMessage", message);

        req.getRequestDispatcher("/WEB-INF/view/error/error.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
