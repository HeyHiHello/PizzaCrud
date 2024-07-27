package org.example.pizzacrud.servlet.ingredient_servlet.action;

import jakarta.servlet.http.HttpServletResponse;
import org.example.pizzacrud.database.entity.Ingredient;
import org.example.pizzacrud.database.repository.exception.InternalDatabaseException;
import org.example.pizzacrud.service.IngredientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

class GetAllIngredientsServletActionTest {
    private static IngredientService ingredientService;
    private static HttpServletResponse httpServletResponse;

    private static GetAllIngredientsServletAction action;

    private static StringWriter stringWriter;

    @BeforeEach
    public void setUp() throws IOException {
        stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        httpServletResponse = Mockito.mock(HttpServletResponse.class);
        Mockito.when(httpServletResponse.getWriter()).thenReturn(printWriter);

        ingredientService = Mockito.mock(IngredientService.class);
        action = new GetAllIngredientsServletAction(ingredientService, httpServletResponse);
    }

    @Test
    void constructorTest() {
        GetAllIngredientsServletAction action1 = new GetAllIngredientsServletAction(httpServletResponse);
        Assertions.assertNotNull(action1);
    }

    @Test
    void executeTest() throws InternalDatabaseException {
        List<Ingredient> testIngredients = List.of(new Ingredient(1, "test1"), new Ingredient(2, "test2"));
        String testIngredientsJsonStr = "[{\"name\":\"test1\",\"id\":1},{\"name\":\"test2\",\"id\":2}]";
        Mockito.when(ingredientService.getAll()).thenReturn(testIngredients);
        action.execute();

        Mockito.verify(httpServletResponse).setStatus(200);
        Assertions.assertEquals(testIngredientsJsonStr, stringWriter.toString());
    }

    @Test
    void executeInternalDatabaseExceptionTest() throws InternalDatabaseException {
        Mockito.when(ingredientService.getAll()).thenThrow(InternalDatabaseException.class);
        action.execute();
        Mockito.verify(httpServletResponse).setStatus(500);
    }
}
