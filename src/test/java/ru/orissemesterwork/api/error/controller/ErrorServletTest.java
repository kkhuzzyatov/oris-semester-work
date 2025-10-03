package ru.orissemesterwork.api.error.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ErrorServletTest {

    private ErrorServlet errorServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher requestDispatcher;

    @BeforeEach
    void setup() {
        errorServlet = new ErrorServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        requestDispatcher = mock(RequestDispatcher.class);

        when(request.getRequestDispatcher("/WEB-INF/view/error/error.jsp"))
                .thenReturn(requestDispatcher);
    }

    @Test
    @DisplayName("doGet устанавливает код ошибки и сообщение, когда переданы параметры")
    void testDoGet_WithParameters() throws ServletException, IOException {
        when(request.getParameter("code")).thenReturn("404");
        when(request.getParameter("message")).thenReturn("Страница не найдена");

        errorServlet.doGet(request, response);

        ArgumentCaptor<Integer> codeCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);

        verify(request).setAttribute(eq("errorCode"), codeCaptor.capture());
        verify(request).setAttribute(eq("errorMessage"), messageCaptor.capture());

        assertEquals(404, codeCaptor.getValue());
        assertEquals("Страница не найдена", messageCaptor.getValue());
        verify(requestDispatcher, times(1)).forward(request, response);
    }

    @Test
    @DisplayName("doGet устанавливает код 500 и сообщение по умолчанию, когда параметры отсутствуют")
    void testDoGet_NoParameters() throws ServletException, IOException {
        when(request.getParameter("code")).thenReturn(null);
        when(request.getParameter("message")).thenReturn(null);

        errorServlet.doGet(request, response);

        ArgumentCaptor<Integer> codeCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);

        verify(request).setAttribute(eq("errorCode"), codeCaptor.capture());
        verify(request).setAttribute(eq("errorMessage"), messageCaptor.capture());

        assertEquals(500, codeCaptor.getValue());
        assertEquals("Произошла неизвестная ошибка.", messageCaptor.getValue());
        verify(requestDispatcher, times(1)).forward(request, response);
    }

    @Test
    @DisplayName("doPost вызывает doGet")
    void testDoPost_CallsDoGet() throws ServletException, IOException {
        ErrorServlet spyServlet = spy(errorServlet);
        when(request.getParameter("code")).thenReturn(null);
        when(request.getParameter("message")).thenReturn(null);
        doNothing().when(spyServlet).doGet(request, response);

        spyServlet.doPost(request, response);

        verify(spyServlet, times(1)).doGet(request, response);
    }
}
