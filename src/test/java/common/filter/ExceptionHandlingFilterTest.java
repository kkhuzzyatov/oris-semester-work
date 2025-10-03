package common.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.junit.jupiter.api.*;
import ru.orissemesterwork.common.filter.ExceptionHandlingFilter;

import java.io.IOException;

import static org.mockito.Mockito.*;

class ExceptionHandlingFilterTest {

    private ExceptionHandlingFilter filter;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain chain;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() {
        filter = new ExceptionHandlingFilter();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        chain = mock(FilterChain.class);
        dispatcher = mock(RequestDispatcher.class);
    }

    @Test
    void testIllegalArgumentException_forwardToError() throws IOException, ServletException {
        when(request.getRequestDispatcher("/WEB-INF/view/error/error.jsp")).thenReturn(dispatcher);
        doThrow(new IllegalArgumentException("test error")).when(chain).doFilter(request, response);

        filter.doFilter(request, response, chain);

        verify(request).setAttribute("errorCode", 400);
        verify(request).setAttribute("errorMessage", "test error");
        verify(dispatcher).forward(request, response);
    }

    @Test
    void testRuntimeException_forwardToError() throws IOException, ServletException {
        when(request.getRequestDispatcher("/WEB-INF/view/error/error.jsp")).thenReturn(dispatcher);
        doThrow(new RuntimeException("runtime issue")).when(chain).doFilter(request, response);

        filter.doFilter(request, response, chain);

        verify(request).setAttribute("errorCode", 500);
        verify(request).setAttribute("errorMessage", "Внутренняя ошибка сервера: runtime issue");
        verify(dispatcher).forward(request, response);
    }

    @Test
    void testNoException_chainCalled() throws IOException, ServletException {
        filter.doFilter(request, response, chain);

        verify(chain, times(1)).doFilter(request, response);
        verifyNoMoreInteractions(request, response);
    }
}
