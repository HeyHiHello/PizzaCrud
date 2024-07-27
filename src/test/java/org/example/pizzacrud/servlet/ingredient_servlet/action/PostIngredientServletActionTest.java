package org.example.pizzacrud.servlet.ingredient_servlet.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.pizzacrud.database.entity.Ingredient;
import org.example.pizzacrud.database.repository.exception.InternalDatabaseException;
import org.example.pizzacrud.database.repository.exception.NoChangesMadeException;
import org.example.pizzacrud.service.IngredientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.*;

class PostIngredientServletActionTest {

    private static IngredientService ingredientService;
    private static HttpServletResponse httpServletResponse;
    private static HttpServletRequest httpServletRequest;

    private static PostIngredientServletAction action;

    private static StringWriter stringWriter;

    private static String testIngredientJsonStr;
    private static Ingredient testIngredient;

    @BeforeEach
    public void setUp() throws IOException {
        stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        httpServletResponse = Mockito.mock(HttpServletResponse.class);
        Mockito.when(httpServletResponse.getWriter()).thenReturn(printWriter);

        testIngredientJsonStr = "{\"name\":\"test\",\"id\":1}\n";
        testIngredient = new Ingredient(1, "test");
        StringReader stringReader = new StringReader(testIngredientJsonStr);
        httpServletRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.when(httpServletRequest.getReader()).thenReturn(new BufferedReader(stringReader));

        ingredientService = Mockito.mock(IngredientService.class);
        action = new PostIngredientServletAction(ingredientService, httpServletRequest, httpServletResponse);
    }

    @Test
    void constructorTest() {
        PostIngredientServletAction action1 = new PostIngredientServletAction(httpServletRequest, httpServletResponse);
        Assertions.assertNotNull(action1);
    }

    @Test
    void executeTest() throws NoChangesMadeException, InternalDatabaseException {
        Mockito.when(ingredientService.save(new Ingredient(0, "test"))).thenReturn(testIngredient);
        action.execute();

        Assertions.assertEquals(testIngredientJsonStr, stringWriter.toString());
    }

    @Test
    void executeInternalDatabaseExceptionTest() throws NoChangesMadeException, InternalDatabaseException {
        Mockito.when(ingredientService.save(new Ingredient(0, "test"))).thenThrow(InternalDatabaseException.class);

        action.execute();

        Mockito.verify(httpServletResponse).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    void executeNoChangesMadeExceptionTest() throws NoChangesMadeException, InternalDatabaseException {
        Mockito.when(ingredientService.save(new Ingredient(0, "test"))).thenThrow(NoChangesMadeException.class);

        action.execute();

        Mockito.verify(httpServletResponse).setStatus(HttpServletResponse.SC_NOT_MODIFIED);
    }
}
