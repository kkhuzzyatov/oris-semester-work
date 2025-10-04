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
    @DisplayName("GET /user?action=register пересылается на register.jsp")
    void testDoGetRegisterForwardsToRegisterJsp() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("register");
        when(request.getRequestDispatcher(UserServlet.REGISTER_JSP)).thenReturn(requestDispatcher);

        userServlet.doGet(request, response);

        verify(requestDispatcher, times(1)).forward(request, response);
    }

    @Test
    @DisplayName("GET /user?action=login пересылается на login.jsp")
    void testDoGetLoginForwardsToLoginJsp() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("login");
        when(request.getRequestDispatcher(UserServlet.LOGIN_JSP)).thenReturn(requestDispatcher);

        userServlet.doGet(request, response);

        verify(requestDispatcher, times(1)).forward(request, response);
    }

    @Test
    @DisplayName("POST register успешно перенаправляет на страницу login")
    void testRegisterSuccessRedirectsToLogin() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("register");
        when(request.getParameter("name")).thenReturn("Камиль");
        when(request.getParameter("email")).thenReturn("kkhuzzyatov@gmail.com");
        when(request.getParameter("phone")).thenReturn("89874146494");
        when(request.getParameter("password")).thenReturn("StrongPass123");

        userServlet.doPost(request, response);

        verify(userService, times(1)).register(Mockito.any());
        verify(response, times(1)).sendRedirect(contains("?action=login"));
    }

    @Test
    @DisplayName("POST login успех перенаправляет на /")
    void testLoginSuccessRedirectsToHome() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("login");
        when(request.getParameter("email")).thenReturn("kkhuzzyatov@gmail.com");
        when(request.getParameter("password")).thenReturn("StrongPass123");

        userServlet.doPost(request, response);

        verify(userService, times(1)).login("kkhuzzyatov@gmail.com", "StrongPass123");
        verify(response, times(1)).sendRedirect(contains("/"));
    }
}