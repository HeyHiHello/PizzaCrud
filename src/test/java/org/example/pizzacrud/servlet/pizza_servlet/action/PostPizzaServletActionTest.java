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

class PostPizzaServletActionTest {
    private static PostPizzaServletAction action;
    private static PizzaService pizzaService;
    private static HttpServletRequest request;
    private static HttpServletResponse response;
    private static final StringWriter stringWriter = new StringWriter();
    private static final String pizzaJsonStr = "{\"price\":123.0,\"name\":\"test1\",\"id\":0}\n";

    @BeforeEach
    public void setUp() throws IOException {
        request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getReader()).thenReturn(new BufferedReader(new StringReader(pizzaJsonStr)));
        response = Mockito.mock(HttpServletResponse.class);
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));
        pizzaService = Mockito.mock(PizzaService.class);

        action = new PostPizzaServletAction(pizzaService, request, response);
    }

    @Test
    void constructorTest() {
        ServletAction action1 = new PostPizzaServletAction(request, response);
        Assertions.assertNotNull(action1);
    }

    @Test
    void executeTest() {
        action.execute();

        Mockito.verify(response).setStatus(HttpServletResponse.SC_CREATED);

        Assertions.assertEquals(pizzaJsonStr, stringWriter.toString());
    }

    @Test
    void executeNoChangesMadeExceptionTest() throws NoChangesMadeException, InternalDatabaseException {
        Mockito.when(pizzaService.save(Mockito.any())).thenThrow(NoChangesMadeException.class);

        action.execute();

        Mockito.verify(response).setStatus(HttpServletResponse.SC_NOT_MODIFIED);
    }

    @Test
    void executeInternalDatabaseExceptionTest() throws NoChangesMadeException, InternalDatabaseException {
        Mockito.when(pizzaService.save(Mockito.any())).thenThrow(InternalDatabaseException.class);

        action.execute();

        Mockito.verify(response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
}
