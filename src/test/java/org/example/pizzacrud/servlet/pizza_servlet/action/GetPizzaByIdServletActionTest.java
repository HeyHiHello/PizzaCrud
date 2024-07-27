package org.example.pizzacrud.servlet.pizza_servlet.action;

import jakarta.servlet.http.HttpServletResponse;
import org.example.pizzacrud.database.entity.Pizza;
import org.example.pizzacrud.database.repository.exception.InternalDatabaseException;
import org.example.pizzacrud.service.PizzaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

class GetPizzaByIdServletActionTest {
    private static PizzaService pizzaService;
    private static HttpServletResponse response;
    private static StringWriter stringWriter;
    private static Pizza pizza = new Pizza(1, "test1", 123);
    private static String pizzaJsonStr = "{\"price\":123.0,\"name\":\"test1\",\"id\":1}\n";
    private static GetPizzaByIdServletAction getPizzaByIdServletAction;

    @BeforeEach
    public void setUp() throws IOException {
        pizzaService = Mockito.mock(PizzaService.class);
        response = Mockito.mock(HttpServletResponse.class);
        stringWriter = new StringWriter();
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));
        getPizzaByIdServletAction = new GetPizzaByIdServletAction(pizzaService, 1, response);
    }

    @Test
    void constructorTest() {
        GetPizzaByIdServletAction action = new GetPizzaByIdServletAction(1, response);
        Assertions.assertNotNull(action);
    }

    @Test
    void executeTest() throws InternalDatabaseException {
        Mockito.when(pizzaService.getById(Mockito.anyInt())).thenReturn(pizza);

        getPizzaByIdServletAction.execute();

        Mockito.verify(response).setStatus(HttpServletResponse.SC_OK);
        Assertions.assertEquals(pizzaJsonStr, stringWriter.toString());
    }

    @Test
    void executeInternalDatabaseExceptionTest() throws InternalDatabaseException {
        Mockito.when(pizzaService.getById(Mockito.anyInt())).thenThrow(InternalDatabaseException.class);
        getPizzaByIdServletAction.execute();
        Mockito.verify(response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
}
