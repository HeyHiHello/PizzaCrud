package org.example.pizzacrud.servlet.ingredient_servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.pizzacrud.servlet.ServletAction;
import org.example.pizzacrud.servlet.ServletActionBuilder;
import org.example.pizzacrud.servlet.exception.InvalidEndPointException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

class IngredientServletTest {

    private static HttpServletRequest request;
    private static HttpServletResponse response;

    private static IngredientServlet ingredientServlet;

    private static ServletActionBuilder actionBuilder;

    private static ServletAction servletAction;

    @BeforeEach
    void setUp() {
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        servletAction = Mockito.mock(ServletAction.class);
        actionBuilder = Mockito.mock(ServletActionBuilder.class);
        ingredientServlet = Mockito.spy(IngredientServlet.class);
        Mockito.when(actionBuilder.buildErrorAction()).thenReturn(servletAction);
    }

    @Test
    void serviceTest() throws ServletException, IOException {
        Mockito.when(request.getMethod()).thenReturn("PATCH");
        ingredientServlet.service(request, response);
        Mockito.verify(ingredientServlet).doPatch(Mockito.any(), Mockito.any());
    }

    @Test
    void serviceNotPatchTest() throws ServletException, IOException {
        Mockito.when(request.getMethod()).thenReturn("GET");
        Mockito.doNothing().when(ingredientServlet).doGet(Mockito.any(), Mockito.any());
        ingredientServlet.service(request, response);
        Mockito.verify(ingredientServlet).doGet(Mockito.any(), Mockito.any());
    }

    @Test
    void doGetTest() throws InvalidEndPointException {
        Mockito.when(actionBuilder.buildGetAction()).thenReturn(servletAction);
        Mockito.when(ingredientServlet.getActionBuilder(request, response)).thenReturn(actionBuilder);
        Assertions.assertDoesNotThrow(() -> ingredientServlet.doGet(request, response));
        Mockito.verify(actionBuilder).buildGetAction();
    }

    @Test
    void doGetInvalidTest() throws InvalidEndPointException {
        Mockito.when(actionBuilder.buildGetAction()).thenThrow(InvalidEndPointException.class);
        Mockito.when(ingredientServlet.getActionBuilder(request, response)).thenReturn(actionBuilder);
        Assertions.assertDoesNotThrow(() -> ingredientServlet.doGet(request, response));
        Mockito.verify(actionBuilder, Mockito.atLeast(1)).buildErrorAction();
    }

    @Test
    void doPostTest() throws InvalidEndPointException {
        Mockito.when(actionBuilder.buildPostAction()).thenReturn(servletAction);
        Mockito.when(ingredientServlet.getActionBuilder(request, response)).thenReturn(actionBuilder);
        Assertions.assertDoesNotThrow(() -> ingredientServlet.doPost(request, response));
        Mockito.verify(actionBuilder).buildPostAction();
    }

    @Test
    void doPostInvalidTest() throws InvalidEndPointException {
        Mockito.when(actionBuilder.buildPostAction()).thenThrow(InvalidEndPointException.class);
        Mockito.when(ingredientServlet.getActionBuilder(request, response)).thenReturn(actionBuilder);
        Assertions.assertDoesNotThrow(() -> ingredientServlet.doPost(request, response));
        Mockito.verify(actionBuilder).buildErrorAction();
    }

    @Test
    void doPatchTest() throws InvalidEndPointException {
        Mockito.when(actionBuilder.buildPatchAction()).thenReturn(servletAction);
        Mockito.when(ingredientServlet.getActionBuilder(request, response)).thenReturn(actionBuilder);
        Assertions.assertDoesNotThrow(() -> ingredientServlet.doPatch(request, response));
        Mockito.verify(actionBuilder).buildPatchAction();
    }

    @Test
    void doPatchInvalidTest() throws InvalidEndPointException {
        Mockito.when(actionBuilder.buildPatchAction()).thenThrow(InvalidEndPointException.class);
        Mockito.when(ingredientServlet.getActionBuilder(request, response)).thenReturn(actionBuilder);
        Assertions.assertDoesNotThrow(() -> ingredientServlet.doPatch(request, response));
        Mockito.verify(actionBuilder).buildErrorAction();
    }

    @Test
    void doDeleteTest() throws InvalidEndPointException {
        Mockito.when(actionBuilder.buildDeleteAction()).thenReturn(servletAction);
        Mockito.when(ingredientServlet.getActionBuilder(request, response)).thenReturn(actionBuilder);
        Assertions.assertDoesNotThrow(() -> ingredientServlet.doDelete(request, response));
        Mockito.verify(actionBuilder).buildDeleteAction();
    }

    @Test
    void doDeleteInvalidTest() throws InvalidEndPointException {
        Mockito.when(actionBuilder.buildDeleteAction()).thenThrow(InvalidEndPointException.class);
        Mockito.when(ingredientServlet.getActionBuilder(request, response)).thenReturn(actionBuilder);
        Assertions.assertDoesNotThrow(() -> ingredientServlet.doDelete(request, response));
        Mockito.verify(actionBuilder).buildErrorAction();
    }
}
