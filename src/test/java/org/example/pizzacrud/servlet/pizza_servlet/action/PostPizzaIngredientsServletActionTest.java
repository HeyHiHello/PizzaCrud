package org.example.pizzacrud.servlet.pizza_servlet.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.pizzacrud.database.entity.Ingredient;
import org.example.pizzacrud.database.entity.Pizza;
import org.example.pizzacrud.database.repository.exception.InternalDatabaseException;
import org.example.pizzacrud.service.PizzaService;
import org.example.pizzacrud.servlet.ServletAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.*;
import java.util.List;

class PostPizzaIngredientsServletActionTest {
    private static PizzaService service = Mockito.mock(PizzaService.class);
    private static int id = 1;
    private static HttpServletRequest request;
    private static HttpServletResponse response;
    private static List<Ingredient> ingredients = List.of(new Ingredient(1, "test1"), new Ingredient(2, "test2"));
    private static String ingredientIdsJsonStr = "[{\"id\":1},{\"id\":2}]";
    private static Pizza pizza = new Pizza(id, "test1", 123, ingredients);
    private static StringWriter stringWriter = new StringWriter();
    private static ServletAction action;

    @BeforeEach
    void setUp() throws IOException {
        service = Mockito.mock(PizzaService.class);
        request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getReader()).thenReturn(new BufferedReader(new StringReader(ingredientIdsJsonStr)));
        response = Mockito.mock(HttpServletResponse.class);
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));
        action = new PostPizzaIngredientsServletAction(service, id, request, response);
    }

    @Test
    void executeTest() throws InternalDatabaseException {
        Mockito.when(service.getById(Mockito.anyInt())).thenReturn(pizza);

        action.execute();

        Mockito.verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    void executeInternalDatabaseExceptionTest() throws InternalDatabaseException {
        Mockito.when(service.getById(Mockito.anyInt())).thenThrow(InternalDatabaseException.class);

        action.execute();

        Mockito.verify(response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
}
