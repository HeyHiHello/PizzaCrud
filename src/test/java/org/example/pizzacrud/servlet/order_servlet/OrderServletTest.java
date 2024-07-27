package org.example.pizzacrud.servlet.order_servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.pizzacrud.servlet.ServletAction;
import org.example.pizzacrud.servlet.ServletActionBuilder;
import org.example.pizzacrud.servlet.exception.InvalidEndPointException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

class OrderServletTest {

    private static OrderServlet servlet = Mockito.spy(OrderServlet.class);
    private static HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    private static HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
    private static ServletActionBuilder actionBuilder = Mockito.mock(ServletActionBuilder.class);
    private static ServletAction action = Mockito.mock(ServletAction.class);

    @BeforeEach
    public void setUp() {
        servlet = Mockito.spy(OrderServlet.class);
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        actionBuilder = Mockito.mock(ServletActionBuilder.class);
        action = Mockito.mock(ServletAction.class);
        Mockito.when(servlet.getActionBuilder(Mockito.any(), Mockito.any())).thenReturn(actionBuilder);
        Mockito.when(actionBuilder.buildErrorAction()).thenReturn(action);
    }

    @Test
    void doGetTest() throws InvalidEndPointException, ServletException, IOException {
        Mockito.when(actionBuilder.buildGetAction()).thenReturn(action);

        servlet.doGet(request, response);

        Mockito.verify(actionBuilder).buildGetAction();
    }

    @Test
    void doGetInvalidEndPointExceptionTest() throws InvalidEndPointException, ServletException, IOException {
        Mockito.when(servlet.getActionBuilder(Mockito.any(), Mockito.any())).thenReturn(actionBuilder);
        Mockito.when(actionBuilder.buildGetAction()).thenThrow(InvalidEndPointException.class);

        servlet.doGet(request, response);

        Mockito.verify(actionBuilder).buildErrorAction();
    }

    @Test
    void doPostTest() throws InvalidEndPointException {
        Mockito.when(actionBuilder.buildPostAction()).thenReturn(action);
        servlet.doPost(request, response);
        Mockito.verify(actionBuilder).buildPostAction();
    }

    @Test
    void doPostInvalidEndPointExceptionTest() throws InvalidEndPointException {
        Mockito.when(actionBuilder.buildPostAction()).thenThrow(InvalidEndPointException.class);
        servlet.doPost(request, response);
        Mockito.verify(actionBuilder).buildErrorAction();
    }

    @Test
    void doDelete() throws InvalidEndPointException, ServletException, IOException {
        Mockito.when(actionBuilder.buildDeleteAction()).thenReturn(action);
        servlet.doDelete(request, response);
        Mockito.verify(actionBuilder).buildDeleteAction();
    }

    @Test
    void doDeleteInvalidEndPointExceptionTest() throws InvalidEndPointException, ServletException, IOException {
        Mockito.when(actionBuilder.buildDeleteAction()).thenThrow(InvalidEndPointException.class);
        servlet.doDelete(request, response);
        Mockito.verify(actionBuilder).buildErrorAction();
    }

}
