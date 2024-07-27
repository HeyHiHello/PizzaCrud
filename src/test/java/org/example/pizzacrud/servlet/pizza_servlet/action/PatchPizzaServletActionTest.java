package org.example.pizzacrud.servlet.pizza_servlet.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.pizzacrud.database.repository.exception.InternalDatabaseException;
import org.example.pizzacrud.database.repository.exception.NoChangesMadeException;
import org.example.pizzacrud.service.PizzaService;
import org.example.pizzacrud.servlet.ServletAction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.*;

class PatchPizzaServletActionTest {
    private static PizzaService service;
    private static HttpServletRequest request;
    private static HttpServletResponse response;
    private static PatchPizzaServletAction servletAction;
    private static String pizzaJsonStr = "{\"price\":123.0,\"name\":\"test1\",\"id\":1}\n";
    private static StringWriter stringWriter = new StringWriter();

    @BeforeEach
    void setUp() throws IOException {
        service = Mockito.mock(PizzaService.class);
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        Mockito.when(request.getReader()).thenReturn(new BufferedReader(new StringReader(pizzaJsonStr)));
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));
        servletAction = new PatchPizzaServletAction(service, 1, request, response);
    }

    @Test
    void constructorTest() {
        ServletAction servletAction = new PatchPizzaServletAction(1, request, response);
        Assertions.assertNotNull(servletAction);
    }

    @Test
    void executeTest() {
        servletAction.execute();

        Mockito.verify(response).setStatus(HttpServletResponse.SC_CREATED);
        Assertions.assertEquals(pizzaJsonStr, stringWriter.toString());
    }

    @Test
    void executeInternalDatabaseExceptionTest() throws NoChangesMadeException, InternalDatabaseException {
        Mockito.when(service.save(Mockito.any())).thenThrow(InternalDatabaseException.class);

        servletAction.execute();

        Mockito.verify(response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    void executeNoChangesMadeExceptionTest() throws NoChangesMadeException, InternalDatabaseException {
        Mockito.when(service.save(Mockito.any())).thenThrow(NoChangesMadeException.class);
        servletAction.execute();
        Mockito.verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}
