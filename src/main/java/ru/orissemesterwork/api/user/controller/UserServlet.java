package ru.orissemesterwork.api.user.controller;

import ru.orissemesterwork.api.user.model.dto.UserDtoRequest;
import ru.orissemesterwork.api.user.model.repository.UserRepository;
import ru.orissemesterwork.api.user.model.repository.impl.UserRepositoryJdbcImpl;
import ru.orissemesterwork.api.user.model.service.UserService;
import ru.orissemesterwork.api.user.model.service.impl.UserServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.orissemesterwork.config.ApplicationConfig;
import ru.orissemesterwork.config.DatabaseConnectionProvider;

import java.io.IOException;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    public static final String REGISTER_JSP = "/WEB-INF/view/user/register.jsp";
    public static final String LOGIN_JSP = "/WEB-INF/view/user/login.jsp";

    private UserService userService;

    @Override
    public void init() throws ServletException {
        DatabaseConnectionProvider databaseConnectionProvider = ApplicationConfig.getMainDatabaseConnectionProvider();
        UserRepository userRepository = new UserRepositoryJdbcImpl(databaseConnectionProvider);
        this.userService = new UserServiceImpl(userRepository);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("register".equalsIgnoreCase(action)) {
            request.getRequestDispatcher(REGISTER_JSP).forward(request, response);
        } else if ("login".equalsIgnoreCase(action)) {
            request.getRequestDispatcher(LOGIN_JSP).forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("register".equalsIgnoreCase(action)) {
            handleRegister(req, resp);
        } else if ("login".equalsIgnoreCase(action)) {
            handleLogin(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void handleRegister(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        UserDtoRequest dto = new UserDtoRequest();
        dto.setName(req.getParameter("name"));
        dto.setEmail(req.getParameter("email"));
        dto.setPhone(req.getParameter("phone"));
        dto.setPassword(req.getParameter("password"));

        try {
            userService.register(dto);
            resp.sendRedirect(req.getContextPath() + "/user?action=login");
        } catch (RuntimeException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher(REGISTER_JSP).forward(req, resp);
        }
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            userService.login(email, password);
            resp.sendRedirect(req.getContextPath() + "/home");
        } catch (RuntimeException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher(LOGIN_JSP).forward(req, resp);
        }
    }
}
