package org.example.pizzacrud.servlet.pizza_servlet;

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

class PizzaServletTest {
    private static HttpServletRequest request;
    private static HttpServletResponse response;
    private static ServletAction action;
    private static PizzaServlet pizzaServlet;
    private static ServletActionBuilder actionBuilder;

    @BeforeEach
    void setUp() {
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        action = Mockito.mock(ServletAction.class);
        pizzaServlet = Mockito.spy(PizzaServlet.class);
        actionBuilder = Mockito.mock(ServletActionBuilder.class);
        Mockito.when(actionBuilder.buildErrorAction()).thenReturn(action);
        Mockito.when(pizzaServlet.getBuilder(Mockito.any(), Mockito.any())).thenReturn(actionBuilder);
    }

    @Test
    void serviceTest() throws ServletException, IOException {
        Mockito.when(request.getMethod()).thenReturn("PATCH");

        Mockito.doNothing().when(pizzaServlet).doPatch(Mockito.any(), Mockito.any());

        pizzaServlet.service(request, response);

        Mockito.verify(pizzaServlet).doPatch(Mockito.any(), Mockito.any());
    }

    @Test
    void serviceGetTest() throws ServletException, IOException {
        Mockito.when(request.getMethod()).thenReturn("GET");

        Mockito.doNothing().when(pizzaServlet).doGet(Mockito.any(), Mockito.any());

        pizzaServlet.service(request, response);

        Mockito.verify(pizzaServlet).doGet(Mockito.any(), Mockito.any());
    }

    @Test
    void doGetTest() throws InvalidEndPointException, ServletException, IOException {
        Mockito.when(actionBuilder.buildGetAction()).thenReturn(action);

        pizzaServlet.doGet(request, response);

        Mockito.verify(actionBuilder).buildGetAction();
    }

    @Test
    void doGetInvalidEndPointExceptionTest() throws InvalidEndPointException, ServletException, IOException {
        Mockito.when(actionBuilder.buildGetAction()).thenThrow(InvalidEndPointException.class);

        pizzaServlet.doGet(request, response);

        Mockito.verify(actionBuilder).buildErrorAction();
    }

    @Test
    void doPostTest() throws InvalidEndPointException, ServletException, IOException {
        Mockito.when(actionBuilder.buildPostAction()).thenReturn(action);

        pizzaServlet.doPost(request, response);

        Mockito.verify(actionBuilder).buildPostAction();
    }

    @Test
    void doPostInvalidEndPointExceptionTest() throws InvalidEndPointException, ServletException, IOException {
        Mockito.when(actionBuilder.buildPostAction()).thenThrow(InvalidEndPointException.class);

        pizzaServlet.doPost(request, response);

        Mockito.verify(actionBuilder).buildErrorAction();
    }

    @Test
    void doPatchTest() throws InvalidEndPointException {
        Mockito.when(actionBuilder.buildPatchAction()).thenReturn(action);

        pizzaServlet.doPatch(request, response);

        Mockito.verify(actionBuilder).buildPatchAction();
    }

    @Test
    void doPatchInvalidEndPointExceptionTest() throws InvalidEndPointException, ServletException, IOException {
        Mockito.when(actionBuilder.buildPatchAction()).thenThrow(InvalidEndPointException.class);

        pizzaServlet.doPatch(request, response);

        Mockito.verify(actionBuilder).buildErrorAction();
    }

    @Test
    void doDeleteTest() throws InvalidEndPointException, ServletException, IOException {
        Mockito.when(actionBuilder.buildDeleteAction()).thenReturn(action);

        pizzaServlet.doDelete(request, response);

        Mockito.verify(actionBuilder).buildDeleteAction();
    }

    @Test
    void doDeleteInvalidEndPointExceptionTest() throws InvalidEndPointException, ServletException, IOException {
        Mockito.when(actionBuilder.buildDeleteAction()).thenThrow(InvalidEndPointException.class);

        pizzaServlet.doDelete(request, response);

        Mockito.verify(actionBuilder).buildErrorAction();
    }
}
