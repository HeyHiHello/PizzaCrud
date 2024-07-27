package org.example.pizzacrud.servlet.customer_servlet;

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

class CustomerServletTest {
    private static HttpServletRequest request;
    private static HttpServletResponse response;

    private static CustomerServlet servlet;

    private static ServletActionBuilder actionBuilder;

    private static ServletAction servletAction;

    @BeforeEach
    void setUp() {
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        servletAction = Mockito.mock(ServletAction.class);
        actionBuilder = Mockito.mock(ServletActionBuilder.class);
        servlet = Mockito.spy(CustomerServlet.class);
        Mockito.when(servlet.getActionBuilder(request, response)).thenReturn(actionBuilder);
        Mockito.when(actionBuilder.buildErrorAction()).thenReturn(servletAction);
    }

    @Test
    void serviceTest() throws ServletException, IOException {
        Mockito.when(request.getMethod()).thenReturn("PATCH");
        Mockito.doNothing().when(servlet).doPatch(Mockito.any(), Mockito.any());
        servlet.service(request, response);
        Mockito.verify(servlet).doPatch(Mockito.any(), Mockito.any());
    }

    @Test
    void serviceNotPatchTest() throws ServletException, IOException {
        Mockito.when(request.getMethod()).thenReturn("GET");
        Mockito.doNothing().when(servlet).doGet(Mockito.any(), Mockito.any());
        servlet.service(request, response);
        Mockito.verify(servlet).doGet(Mockito.any(), Mockito.any());
    }

    @Test
    void doGetTest() throws InvalidEndPointException, ServletException, IOException {
        Mockito.when(servlet.getActionBuilder(request, response)).thenReturn(actionBuilder);
        Mockito.when(actionBuilder.buildGetAction()).thenReturn(servletAction);
        servlet.doGet(request, response);

        Mockito.verify(actionBuilder).buildGetAction();
    }

    @Test
    void doGetInvalidTest() throws ServletException, IOException, InvalidEndPointException {
        Mockito.when(actionBuilder.buildErrorAction()).thenReturn(servletAction);
        Mockito.when(actionBuilder.buildGetAction()).thenThrow(InvalidEndPointException.class);

        servlet.doGet(request, response);
        Mockito.verify(actionBuilder).buildErrorAction();
    }

    @Test
    void doPostTest() throws ServletException, IOException, InvalidEndPointException {
        Mockito.when(actionBuilder.buildPostAction()).thenReturn(servletAction);

        servlet.doPost(request, response);

        Mockito.verify(actionBuilder).buildPostAction();
    }

    @Test
    void doPostInvalidTest() throws ServletException, IOException, InvalidEndPointException {
        Mockito.when(actionBuilder.buildPostAction()).thenThrow(InvalidEndPointException.class);

        servlet.doPost(request, response);

        Mockito.verify(actionBuilder).buildErrorAction();
    }

    @Test
    void doDeleteTest() throws ServletException, IOException, InvalidEndPointException {
        Mockito.when(actionBuilder.buildDeleteAction()).thenReturn(servletAction);

        servlet.doDelete(request, response);

        Mockito.verify(actionBuilder).buildDeleteAction();
    }

    @Test
    void doDeleteInvalidTest() throws ServletException, IOException, InvalidEndPointException {
        Mockito.when(actionBuilder.buildDeleteAction()).thenThrow(InvalidEndPointException.class);

        servlet.doDelete(request, response);

        Mockito.verify(actionBuilder).buildErrorAction();
    }

    @Test
    void doPatchTest() throws InvalidEndPointException {
        Mockito.when(actionBuilder.buildPatchAction()).thenReturn(servletAction);

        servlet.doPatch(request, response);

        Mockito.verify(actionBuilder).buildPatchAction();
    }

    @Test
    void doPatchInvalidTest() throws InvalidEndPointException {
        Mockito.when(actionBuilder.buildPatchAction()).thenThrow(InvalidEndPointException.class);

        servlet.doPatch(request, response);

        Mockito.verify(actionBuilder).buildErrorAction();
    }
}
