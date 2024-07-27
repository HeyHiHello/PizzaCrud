package org.example.pizzacrud.servlet.pizza_servlet.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.pizzacrud.servlet.ServletAction;
import org.example.pizzacrud.servlet.exception.InvalidEndPointException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PizzaServletActionBuilderTest {
    private static HttpServletRequest request;
    private static HttpServletResponse response;
    private static PizzaServletActionBuilder builder;

    @BeforeAll
    static void setUp() {
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        builder = new PizzaServletActionBuilder(request, response);
    }

    @Test
    void buildGetAllServletActionTest() throws InvalidEndPointException {
        Mockito.when(request.getPathInfo()).thenReturn(null);
        ServletAction action = builder.buildGetAction();

        Assertions.assertEquals(GetAllPizzasServletAction.class, action.getClass());
    }

    @Test
    void buildGetPizzaByIdServletActionTest() throws InvalidEndPointException {
        Mockito.when(request.getPathInfo()).thenReturn("/1");
        ServletAction action = builder.buildGetAction();

        Assertions.assertEquals(GetPizzaByIdServletAction.class, action.getClass());
    }

    @Test
    void buildGetPizzaIngredientServletActionTest() throws InvalidEndPointException {
        Mockito.when(request.getPathInfo()).thenReturn("/1/ingredients");

        ServletAction action = builder.buildGetAction();
        Assertions.assertEquals(GetPizzaIngredientsServletAction.class, action.getClass());
    }

    @Test
    void buildGetActionInvalidEndPointExceptionTest() {
        Mockito.when(request.getPathInfo()).thenReturn("/invalid");
        Assertions.assertThrows(InvalidEndPointException.class, () -> builder.buildGetAction());
    }

    @Test
    void buildPostAction() throws InvalidEndPointException {
        Mockito.when(request.getPathInfo()).thenReturn(null);

        ServletAction action = builder.buildPostAction();

        Assertions.assertEquals(PostPizzaServletAction.class, action.getClass());
    }

    @Test
    void buildPostPizzaIngredientServletActionTest() throws InvalidEndPointException {
        Mockito.when(request.getPathInfo()).thenReturn("/1/ingredients");

        ServletAction action = builder.buildPostAction();
        Assertions.assertEquals(PostPizzaIngredientsServletAction.class, action.getClass());
    }

    @Test
    void buildPostActionInvalidEndPointExceptionNumberFormatTest() {
        Mockito.when(request.getPathInfo()).thenReturn("/invalid");
        Assertions.assertThrows(InvalidEndPointException.class, () -> builder.buildPostAction());
    }

    @Test
    void buildPostActionInvalidEndPointExceptionTargetTest() {
        Mockito.when(request.getPathInfo()).thenReturn("/1/invalidTarget");
        Assertions.assertThrows(InvalidEndPointException.class, () -> builder.buildPostAction());
    }

    @Test
    void buildDeleteActionTest() throws InvalidEndPointException {
        Mockito.when(request.getPathInfo()).thenReturn("/1");

        ServletAction action = builder.buildDeleteAction();
        Assertions.assertEquals(DeletePizzaServletAction.class, action.getClass());
    }

    @Test
    void buildDeleteActionInvalidEndPointNumberFormatTest() {
        Mockito.when(request.getPathInfo()).thenReturn("/invalid");

        Assertions.assertThrows(InvalidEndPointException.class, () -> builder.buildDeleteAction());
    }

    @Test
    void buildDeleteActionInvalidEndPointNoIdTest() {
        Mockito.when(request.getPathInfo()).thenReturn(null);

        Assertions.assertThrows(InvalidEndPointException.class, () -> builder.buildDeleteAction());
    }

    @Test
    void buildPatchActionTest() throws InvalidEndPointException {
        Mockito.when(request.getPathInfo()).thenReturn("/1");
        ServletAction action = builder.buildPatchAction();
        Assertions.assertEquals(PatchPizzaServletAction.class, action.getClass());
    }

    @Test
    void buildPatchActionInvalidEndPointNumberFormatTest() {
        Mockito.when(request.getPathInfo()).thenReturn("/invalid");
        Assertions.assertThrows(InvalidEndPointException.class, () -> builder.buildPatchAction());
    }

    @Test
    void buildPatchActionInvalidEndPointNoIdTest() {
        Mockito.when(request.getPathInfo()).thenReturn(null);
        Assertions.assertThrows(InvalidEndPointException.class, () -> builder.buildPatchAction());
    }
}
