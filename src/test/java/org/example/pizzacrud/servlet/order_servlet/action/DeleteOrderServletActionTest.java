package org.example.pizzacrud.servlet.order_servlet.action;

import jakarta.servlet.http.HttpServletResponse;
import org.example.pizzacrud.database.repository.exception.InternalDatabaseException;
import org.example.pizzacrud.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DeleteOrderServletActionTest {
    private static OrderService service;
    private static int id;
    private static HttpServletResponse response;
    private static DeleteOrderServletAction action;

    @BeforeEach
    void setUp() {
        service = Mockito.mock(OrderService.class);
        id = 1;
        response = Mockito.mock(HttpServletResponse.class);
        action = new DeleteOrderServletAction(service, id, response);
    }

    @Test
    void executeTest() {
        action.execute();
        Mockito.verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    void executeInternalDatabaseExceptionTest() throws InternalDatabaseException {
        Mockito.doThrow(InternalDatabaseException.class).when(service).delete(Mockito.anyInt());
        action.execute();
        Mockito.verify(response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
}
