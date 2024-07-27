package org.example.pizzacrud.servlet.customer_servlet.action;

import jakarta.servlet.http.HttpServletResponse;
import org.example.pizzacrud.database.repository.exception.InternalDatabaseException;
import org.example.pizzacrud.database.repository.exception.NoChangesMadeException;
import org.example.pizzacrud.service.CustomerService;
import org.example.pizzacrud.servlet.ingredient_servlet.action.DeleteIngredientServletAction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DeleteCustomerServletActionTest {
    private static HttpServletResponse response;
    private static CustomerService service;
    private static DeleteCustomerServletAction action;


    @BeforeEach
    public void setUp() {
        service = Mockito.mock(CustomerService.class);
        response = Mockito.mock(HttpServletResponse.class);
        action = new DeleteCustomerServletAction(service, 1, response);
    }

    @Test
    void constructorTest() {
        DeleteIngredientServletAction deleteIngredientServletAction1 = new DeleteIngredientServletAction(2, response);
        Assertions.assertNotNull(deleteIngredientServletAction1);
    }

    @Test
    void executeTest() throws NoChangesMadeException, InternalDatabaseException {
        Mockito.doNothing().when(service).delete(1);
        Assertions.assertDoesNotThrow(() -> action.execute());
        Mockito.verify(response).setStatus(200);
    }

    @Test
    void executeInternalDatabaseErrorTest() throws NoChangesMadeException, InternalDatabaseException {
        Mockito.doThrow(InternalDatabaseException.class).when(service).delete(1);
        Assertions.assertDoesNotThrow(() -> action.execute());
        Mockito.verify(response).setStatus(500);
    }

    @Test
    void executeNoChangesMadeExceptionTest() throws NoChangesMadeException, InternalDatabaseException {
        Mockito.doThrow(NoChangesMadeException.class).when(service).delete(1);
        Assertions.assertDoesNotThrow(() -> action.execute());
        Mockito.verify(response).setStatus(304);
    }
}
