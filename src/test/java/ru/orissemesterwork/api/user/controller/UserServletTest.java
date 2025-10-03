package ru.orissemesterwork.api.user.controller;

import org.junit.jupiter.api.*;
import org.mockito.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import ru.orissemesterwork.api.user.model.service.UserService;

import java.io.IOException;

import static org.mockito.Mockito.*;

class UserServletTest {
    @InjectMocks
    private UserServlet userServlet;

    @Mock
    private UserService userService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("GET /user?action=register forwards to register.jsp")
    void testDoGetRegisterForwardsToRegisterJsp() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("register");
        when(request.getRequestDispatcher(UserServlet.REGISTER_JSP)).thenReturn(requestDispatcher);

        userServlet.doGet(request, response);

        verify(requestDispatcher, times(1)).forward(request, response);
    }

    @Test
    @DisplayName("GET /user?action=login forwards to login.jsp")
    void testDoGetLoginForwardsToLoginJsp() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("login");
        when(request.getRequestDispatcher(UserServlet.LOGIN_JSP)).thenReturn(requestDispatcher);

        userServlet.doGet(request, response);

        verify(requestDispatcher, times(1)).forward(request, response);
    }

    @Test
    @DisplayName("POST register successfully redirects to login")
    void testRegisterSuccessRedirectsToLogin() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("register");
        when(request.getParameter("name")).thenReturn("John");
        when(request.getParameter("email")).thenReturn("john@example.com");
        when(request.getParameter("phone")).thenReturn("89991234567");
        when(request.getParameter("password")).thenReturn("StrongPass123");

        userServlet.doPost(request, response);

        verify(userService, times(1)).register(Mockito.any());
        verify(response, times(1)).sendRedirect(contains("?action=login"));
    }

    @Test
    @DisplayName("POST register with error forwards back to register.jsp with error")
    void testRegisterFailureForwardsToRegisterJspWithError() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("register");
        when(request.getParameter("name")).thenReturn("John");
        when(request.getParameter("email")).thenReturn("john@example.com");
        when(request.getParameter("phone")).thenReturn("89991234567");
        when(request.getParameter("password")).thenReturn("StrongPass123");
        doThrow(new RuntimeException("User exists")).when(userService).register(Mockito.any());
        when(request.getRequestDispatcher(UserServlet.REGISTER_JSP)).thenReturn(requestDispatcher);

        userServlet.doPost(request, response);

        verify(request, times(1)).setAttribute(eq("error"), eq("User exists"));
        verify(requestDispatcher, times(1)).forward(request, response);
    }

    @Test
    @DisplayName("POST login success redirects to home")
    void testLoginSuccessRedirectsToHome() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("login");
        when(request.getParameter("email")).thenReturn("john@example.com");
        when(request.getParameter("password")).thenReturn("StrongPass123");

        userServlet.doPost(request, response);

        verify(userService, times(1)).login("john@example.com", "StrongPass123");
        verify(response, times(1)).sendRedirect(contains("/home"));
    }

    @Test
    @DisplayName("POST login failure forwards back to login.jsp with error")
    void testLoginFailureForwardsToLoginJspWithError() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("login");
        when(request.getParameter("email")).thenReturn("john@example.com");
        when(request.getParameter("password")).thenReturn("WrongPass");
        doThrow(new RuntimeException("Invalid credentials")).when(userService).login("john@example.com", "WrongPass");
        when(request.getRequestDispatcher(UserServlet.LOGIN_JSP)).thenReturn(requestDispatcher);

        userServlet.doPost(request, response);

        verify(request, times(1)).setAttribute(eq("error"), eq("Invalid credentials"));
        verify(requestDispatcher, times(1)).forward(request, response);
    }
}