package org.example.pizzacrud.servlet.pizza_servlet.action;

import jakarta.servlet.http.HttpServletResponse;
import org.example.pizzacrud.database.entity.Pizza;
import org.example.pizzacrud.database.repository.exception.InternalDatabaseException;
import org.example.pizzacrud.service.PizzaService;
import org.example.pizzacrud.servlet.ServletAction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.List;

class GetAllPizzaServletActionTest {
    private static PizzaService pizzaService;
    private static HttpServletResponse response;
    private static StringWriter stringWriter;
    private static List<Pizza> pizzaList;
    private static ServletAction action;
    private static String pizzaListJsonStr;

    @BeforeEach
    public void setUp() throws SQLException, IOException {
        pizzaList = List.of(new Pizza(1, "test1", 123),
                new Pizza(2, "test2", 123));
        pizzaListJsonStr = "[{\"price\":123.0,\"name\":\"test1\",\"id\":1},{\"price\":123.0,\"name\":\"test2\",\"id\":2}]";
        stringWriter = new StringWriter();
        pizzaService = Mockito.mock(PizzaService.class);
        Mockito.when(pizzaService.getAll()).thenReturn(pizzaList);
        response = Mockito.mock(HttpServletResponse.class);
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        action = new GetAllPizzasServletAction(pizzaService, response);
    }

    @Test
    void constructorTest() {
        ServletAction action1 = new GetAllPizzasServletAction(response);
        Assertions.assertNotNull(action1);
    }

    @Test
    void executeTest() {
        action.execute();
        Assertions.assertEquals(pizzaListJsonStr, stringWriter.toString());
    }

    @Test
    void executeInternalDatabaseExceptionTest() throws InternalDatabaseException {
        Mockito.when(pizzaService.getAll()).thenThrow(InternalDatabaseException.class);
        action.execute();
        Mockito.verify(response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
}
