package org.example.pizzacrud.servlet.ingredient_servlet.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.pizzacrud.servlet.BadRequestServletAction;
import org.example.pizzacrud.servlet.ServletAction;
import org.example.pizzacrud.servlet.exception.InvalidEndPointException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class IngredientServletActionBuilderTest {
    private static HttpServletResponse response;
    private static HttpServletRequest request;
    private static IngredientServletActionBuilder builder;


    @BeforeAll
    public static void setUp() {
        response = Mockito.mock(HttpServletResponse.class);
        request = Mockito.mock(HttpServletRequest.class);
        builder = new IngredientServletActionBuilder(request, response);
    }

    @Test
    void buildGetAllIngredientActionTest() throws InvalidEndPointException {
        Mockito.when(request.getPathInfo()).thenReturn(null);

        ServletAction action = builder.buildGetAction();

        Assertions.assertNotNull(action);
        Assertions.assertEquals(action.getClass(), GetAllIngredientsServletAction.class);
    }

    @Test
    void buildGetIngredientByIdActionTest() throws InvalidEndPointException {
        Mockito.when(request.getPathInfo()).thenReturn("/1");

        ServletAction action = builder.buildGetAction();

        Assertions.assertNotNull(action);
        Assertions.assertEquals(action.getClass(), GetIngredientByIdServletAction.class);
    }

    @Test
    void buildGetIngredientActionInvalidEndPointTest() throws InvalidEndPointException {
        Mockito.when(request.getPathInfo()).thenReturn("/invalid");

        Assertions.assertThrows(InvalidEndPointException.class, () -> builder.buildGetAction());
    }

    @Test
    void buildPostIngredientActionTest() throws InvalidEndPointException {
        Mockito.when(request.getPathInfo()).thenReturn(null);

        ServletAction action = builder.buildPostAction();

        Assertions.assertNotNull(action);
        Assertions.assertEquals(action.getClass(), PostIngredientServletAction.class);
    }

    @Test
    void buildPostIngredientActionInvalidEndPointTest() {
        Mockito.when(request.getPathInfo()).thenReturn("any");

        Assertions.assertThrows(InvalidEndPointException.class, () -> builder.buildPostAction());
    }

    @Test
    void buildPatchIngredientActionTest() throws InvalidEndPointException {
        Mockito.when(request.getPathInfo()).thenReturn("/1");
        ServletAction action = builder.buildPatchAction();
        Assertions.assertNotNull(action);
        Assertions.assertEquals(action.getClass(), PatchIngredientServletAction.class);
    }

    @Test
    void buildPatchIngredientActionInvalidEndPointTest() {
        Mockito.when(request.getPathInfo()).thenReturn("/invalid");
        Assertions.assertThrows(InvalidEndPointException.class, () -> builder.buildPatchAction());
    }

    @Test
    void buildPatchIngredientActionInvalidEndPointNullTest() {
        Mockito.when(request.getPathInfo()).thenReturn(null);
        Assertions.assertThrows(InvalidEndPointException.class, () -> builder.buildPatchAction());
    }

    @Test
    void buildDeleteIngredientActionTest() throws InvalidEndPointException {
        Mockito.when(request.getPathInfo()).thenReturn("/1");
        ServletAction action = builder.buildDeleteAction();
        Assertions.assertNotNull(action);
        Assertions.assertEquals(action.getClass(), DeleteIngredientServletAction.class);
    }

    @Test
    void buildDeleteIngredientActionInvalidEndPointTest() {
        Mockito.when(request.getPathInfo()).thenReturn("/invalid");
        Assertions.assertThrows(InvalidEndPointException.class, () -> builder.buildDeleteAction());
    }

    @Test
    void buildDeleteIngredientActionInvalidEndPointNullTest() {
        Mockito.when(request.getPathInfo()).thenReturn(null);
        Assertions.assertThrows(InvalidEndPointException.class, () -> builder.buildDeleteAction());
    }

    @Test
    void buildErrorActionTest() {
        Mockito.when(request.getPathInfo()).thenReturn("/1");
        ServletAction action = builder.buildErrorAction();
        Assertions.assertNotNull(action);
        Assertions.assertEquals(BadRequestServletAction.class, action.getClass());
    }
}
