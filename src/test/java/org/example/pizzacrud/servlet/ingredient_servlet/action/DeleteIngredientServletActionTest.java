package org.example.pizzacrud.servlet.ingredient_servlet.action;

import jakarta.servlet.http.HttpServletResponse;
import org.example.pizzacrud.database.repository.exception.InternalDatabaseException;
import org.example.pizzacrud.database.repository.exception.NoChangesMadeException;
import org.example.pizzacrud.service.IngredientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DeleteIngredientServletActionTest {
    private static HttpServletResponse httpServletResponse;
    private static IngredientService ingredientService;
    private static DeleteIngredientServletAction deleteIngredientServletAction;


    @BeforeEach
    public void setUp() {
        ingredientService = Mockito.mock(IngredientService.class);
        httpServletResponse = Mockito.mock(HttpServletResponse.class);
        deleteIngredientServletAction = new DeleteIngredientServletAction(ingredientService, 1, httpServletResponse);
    }

    @Test
    void constructorTest() {
        DeleteIngredientServletAction deleteIngredientServletAction1 = new DeleteIngredientServletAction(2, httpServletResponse);
        Assertions.assertNotNull(deleteIngredientServletAction1);
    }

    @Test
    void executeTest() throws NoChangesMadeException, InternalDatabaseException {
        Mockito.doNothing().when(ingredientService).delete(1);
        Assertions.assertDoesNotThrow(() -> deleteIngredientServletAction.execute());
        Mockito.verify(httpServletResponse).setStatus(200);
    }

    @Test
    void executeInternalDatabaseErrorTest() throws NoChangesMadeException, InternalDatabaseException {
        Mockito.doThrow(InternalDatabaseException.class).when(ingredientService).delete(1);
        Assertions.assertDoesNotThrow(() -> deleteIngredientServletAction.execute());
        Mockito.verify(httpServletResponse).setStatus(500);
    }

    @Test
    void executeNoChangesMadeExceptionTest() throws NoChangesMadeException, InternalDatabaseException {
        Mockito.doThrow(NoChangesMadeException.class).when(ingredientService).delete(1);
        Assertions.assertDoesNotThrow(() -> deleteIngredientServletAction.execute());
        Mockito.verify(httpServletResponse).setStatus(304);
    }
}
