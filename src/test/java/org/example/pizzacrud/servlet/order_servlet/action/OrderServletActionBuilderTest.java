package org.example.pizzacrud.servlet.order_servlet.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.pizzacrud.servlet.ServletAction;
import org.example.pizzacrud.servlet.exception.InvalidEndPointException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class OrderServletActionBuilderTest {
    private static HttpServletResponse response;
    private static HttpServletRequest request;
    private OrderServletActionBuilder actionBuilder;

    @BeforeEach
    void setUp() {
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        actionBuilder = new OrderServletActionBuilder(request, response);
    }

    @Test
    void buildGetActionEmptyPath() {
        Mockito.when(request.getPathInfo()).thenReturn(null);

        Assertions.assertThrows(InvalidEndPointException.class, () -> actionBuilder.buildGetAction());
    }

    @Test
    void buildGetActionPathTooLongTest() {
        Mockito.when(request.getPathInfo()).thenReturn("/too/long");
        Assertions.assertThrows(InvalidEndPointException.class, () -> actionBuilder.buildGetAction());
    }

    @Test
    void buildGetActionPathInvalidId() {
        Mockito.when(request.getPathInfo()).thenReturn("/invalid/");
        Assertions.assertThrows(InvalidEndPointException.class, () -> actionBuilder.buildGetAction());
    }

    @Test
    void buildGetServletActionTest() throws InvalidEndPointException {
        Mockito.when(request.getPathInfo()).thenReturn("/1");
        ServletAction action = actionBuilder.buildGetAction();
        Assertions.assertNotNull(action);
        Assertions.assertEquals(GetOrderByIdServletAction.class, action.getClass());
    }

    @Test
    void buildPostActionTest() throws InvalidEndPointException {
        Mockito.when(request.getPathInfo()).thenReturn(null);
        ServletAction action = actionBuilder.buildPostAction();
        Assertions.assertNotNull(action);
        Assertions.assertEquals(PostOrderServletAction.class, action.getClass());
    }

    @Test
    void buildPostActionInvalidEndPointTest() {
        Mockito.when(request.getPathInfo()).thenReturn("/invalid/");
        Assertions.assertThrows(InvalidEndPointException.class, () -> actionBuilder.buildPostAction());
    }

    @Test
    void buildDeleteActionEmptyPathTest() {
        Mockito.when(request.getPathInfo()).thenReturn(null);
        Assertions.assertThrows(InvalidEndPointException.class, () -> actionBuilder.buildDeleteAction());
    }

    @Test
    void buildDeleteActionPathTooLongTest() {
        Mockito.when(request.getPathInfo()).thenReturn("/1/too/long");
        Assertions.assertThrows(InvalidEndPointException.class, () -> actionBuilder.buildDeleteAction());
    }

    @Test
    void buildDeleteActionInvalidId() {
        Mockito.when(request.getPathInfo()).thenReturn("/invalid/");
        Assertions.assertThrows(InvalidEndPointException.class, () -> actionBuilder.buildDeleteAction());
    }

    @Test
    void buildDeleteActionTest() throws InvalidEndPointException {
        Mockito.when(request.getPathInfo()).thenReturn("/1");
        ServletAction action = actionBuilder.buildDeleteAction();
        Assertions.assertNotNull(action);
        Assertions.assertEquals(DeleteOrderServletAction.class, action.getClass());
    }
}
