package org.example.pizzacrud.servlet.pizza_servlet.action;

import jakarta.servlet.http.HttpServletResponse;
import org.example.pizzacrud.database.entity.Ingredient;
import org.example.pizzacrud.database.entity.Pizza;
import org.example.pizzacrud.database.repository.exception.InternalDatabaseException;
import org.example.pizzacrud.service.PizzaService;
import org.example.pizzacrud.servlet.ServletAction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

class GetPizzaIngredientsServletActionTest {
    private static PizzaService pizzaService;
    private static HttpServletResponse response;
    private static StringWriter stringWriter = new StringWriter();
    private static List<Ingredient> ingredients = List.of(new Ingredient(1, "test1"), new Ingredient(2, "test2"));
    private static String ingredientsJson = "[{\"name\":\"test1\",\"id\":1},{\"name\":\"test2\",\"id\":2}]\n";
    private static Pizza pizza = new Pizza(1, "testPizza", 123, ingredients);
    private static GetPizzaIngredientsServletAction servletAction;

    @BeforeEach
    public void setUp() throws Exception {
        pizzaService = Mockito.mock(PizzaService.class);
        response = Mockito.mock(HttpServletResponse.class);
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));
        servletAction = new GetPizzaIngredientsServletAction(pizzaService, 1, response);
    }

    @Test
    void constructorTest() {
        ServletAction action1 = new GetPizzaIngredientsServletAction(1, response);
        Assertions.assertNotNull(action1);
    }

    @Test
    void executeTest() throws InternalDatabaseException {
        Mockito.when(pizzaService.getById(Mockito.anyInt())).thenReturn(pizza);
        servletAction.execute();

        Mockito.verify(response).setStatus(HttpServletResponse.SC_OK);
        Assertions.assertEquals(ingredientsJson, stringWriter.toString());
    }

    @Test
    void executeInternalDatabaseExceptionTest() throws InternalDatabaseException {
        Mockito.when(pizzaService.getById(Mockito.anyInt())).thenThrow(InternalDatabaseException.class);

        servletAction.execute();

        Mockito.verify(response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
}
