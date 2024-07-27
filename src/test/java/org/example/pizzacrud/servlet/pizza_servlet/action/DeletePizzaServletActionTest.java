package org.example.pizzacrud.servlet.pizza_servlet.action;

import jakarta.servlet.http.HttpServletResponse;
import org.example.pizzacrud.database.repository.exception.InternalDatabaseException;
import org.example.pizzacrud.database.repository.exception.NoChangesMadeException;
import org.example.pizzacrud.service.PizzaService;
import org.example.pizzacrud.servlet.ServletAction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;

class DeletePizzaServletActionTest {
    private static HttpServletResponse httpServletResponse;
    private static PizzaService pizzaService;
    private static DeletePizzaServletAction deletePizzaServletAction;

    @BeforeEach
    void setUp() {
        pizzaService = Mockito.mock(PizzaService.class);
        httpServletResponse = Mockito.mock(HttpServletResponse.class);
        deletePizzaServletAction = new DeletePizzaServletAction(pizzaService, 1, httpServletResponse);
    }

    @Test
    void constructorTest() {
        ServletAction action1 = new DeletePizzaServletAction(1, httpServletResponse);
        Assertions.assertNotNull(action1);
    }

    @Test
    void deletePizza() {
        deletePizzaServletAction.execute();
        Mockito.verify(httpServletResponse).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    void deleteNoChangesMadeExceptionTest() throws SQLException {
        Mockito.doThrow(NoChangesMadeException.class).when(pizzaService).delete(Mockito.anyInt());
        deletePizzaServletAction.execute();
        Mockito.verify(httpServletResponse).setStatus(HttpServletResponse.SC_NOT_MODIFIED);
    }

    @Test
    void deletePizzaSQLExceptionTest() throws SQLException {
        Mockito.doThrow(InternalDatabaseException.class).when(pizzaService).delete(Mockito.anyInt());
        deletePizzaServletAction.execute();
        Mockito.verify(httpServletResponse).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
}
