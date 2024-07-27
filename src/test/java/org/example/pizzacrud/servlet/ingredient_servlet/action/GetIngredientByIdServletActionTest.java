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

class GetIngredientByIdServletActionTest {
    private static IngredientService ingredientService;
    private static HttpServletResponse httpServletResponse;

    private static GetIngredientByIdServletAction action;

    private static StringWriter stringWriter;

    @BeforeEach
    public void setUp() throws IOException {
        stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        httpServletResponse = Mockito.mock(HttpServletResponse.class);
        Mockito.when(httpServletResponse.getWriter()).thenReturn(printWriter);

        ingredientService = Mockito.mock(IngredientService.class);
        action = new GetIngredientByIdServletAction(ingredientService, 1, httpServletResponse);
    }

    @Test
    void constructorTest() {
        GetIngredientByIdServletAction action1 = new GetIngredientByIdServletAction(1, httpServletResponse);
        Assertions.assertNotNull(action1);
    }

    @Test
    void executeTest() throws InternalDatabaseException {
        Ingredient testIngredient = new Ingredient(1, "test");
        String testIngredientJsonStr = "{\"name\":\"test\",\"id\":1}\n";
        Mockito.when(ingredientService.getById(1)).thenReturn(testIngredient);

        action.execute();

        Assertions.assertEquals(testIngredientJsonStr, stringWriter.toString());
        Mockito.verify(httpServletResponse).setStatus(200);
    }

    @Test
    void executeInternalDatabaseExceptionTest() throws InternalDatabaseException {
        Mockito.when(ingredientService.getById(1)).thenThrow(InternalDatabaseException.class);

        action.execute();

        Mockito.verify(httpServletResponse).setStatus(500);
    }
}
