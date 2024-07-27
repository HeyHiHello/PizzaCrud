package org.example.pizzacrud.servlet.ingredient_servlet.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.pizzacrud.database.entity.Ingredient;
import org.example.pizzacrud.database.repository.exception.InternalDatabaseException;
import org.example.pizzacrud.database.repository.exception.NoChangesMadeException;
import org.example.pizzacrud.service.IngredientService;
import org.example.pizzacrud.servlet.ServletAction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.*;

class PatchIngredientServletActionTest {
    private static IngredientService ingredientService;
    private static HttpServletResponse httpServletResponse;
    private static HttpServletRequest httpServletRequest;

    private static PatchIngredientServletAction action;

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
        action = new PatchIngredientServletAction(ingredientService, 1, httpServletRequest, httpServletResponse);
    }

    @Test
    void constructorTest() {
        ServletAction action1 = new PatchIngredientServletAction(1, httpServletRequest, httpServletResponse);
        Assertions.assertNotNull(action1);
    }

    @Test
    void executeTest() {
        action.execute();

        Assertions.assertEquals(testIngredientJsonStr, stringWriter.toString());
    }

    @Test
    void executeInternalDatabaseExceptionTest() throws NoChangesMadeException, InternalDatabaseException {
        Mockito.when(ingredientService.save(testIngredient)).thenThrow(InternalDatabaseException.class);

        action.execute();

        Mockito.verify(httpServletResponse).setStatus(500);
    }

    @Test
    void executeIOExceptionTest() throws IOException {
        Mockito.when(httpServletResponse.getWriter()).thenThrow(IOException.class);

        action.execute();

        Mockito.verify(httpServletResponse).setStatus(500);
    }

    @Test
    void executeNoChangesMadeExceptionTest() throws NoChangesMadeException, InternalDatabaseException {
        Mockito.when(ingredientService.save(testIngredient)).thenThrow(NoChangesMadeException.class);

        action.execute();

        Mockito.verify(httpServletResponse).setStatus(304);
    }
}
