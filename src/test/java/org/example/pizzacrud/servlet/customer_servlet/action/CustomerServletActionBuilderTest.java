package org.example.pizzacrud.servlet.customer_servlet.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.pizzacrud.servlet.BadRequestServletAction;
import org.example.pizzacrud.servlet.ServletAction;
import org.example.pizzacrud.servlet.exception.InvalidEndPointException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class CustomerServletActionBuilderTest {
    private static HttpServletResponse response;
    private static HttpServletRequest request;
    private static CustomerServletActionBuilder builder;


    @BeforeAll
    public static void setUp() {
        response = Mockito.mock(HttpServletResponse.class);
        request = Mockito.mock(HttpServletRequest.class);
        builder = new CustomerServletActionBuilder(request, response);
    }

    @Test
    void buildGetAllCustomerActionTest() throws InvalidEndPointException {
        Mockito.when(request.getPathInfo()).thenReturn(null);

        ServletAction action = builder.buildGetAction();

        Assertions.assertNotNull(action);
        Assertions.assertEquals(action.getClass(), GetAllCustomersServletAction.class);
    }

    @Test
    void buildGetCustomerByIdActionTest() throws InvalidEndPointException {
        Mockito.when(request.getPathInfo()).thenReturn("/1");

        ServletAction action = builder.buildGetAction();

        Assertions.assertNotNull(action);
        Assertions.assertEquals(GetCustomerByIdServletAction.class, action.getClass());
    }

    @Test
    void buildGetCustomerAddressTest() throws InvalidEndPointException {
        Mockito.when(request.getPathInfo()).thenReturn("/1/address");

        ServletAction action = builder.buildGetAction();

        Assertions.assertNotNull(action);
        Assertions.assertEquals(action.getClass(), GetCustomerAddressServletAction.class);
    }

    @Test
    void buildGetCustomerOrderTest() throws InvalidEndPointException {
        Mockito.when(request.getPathInfo()).thenReturn("/1/orders");

        ServletAction action = builder.buildGetAction();

        Assertions.assertNotNull(action);
        Assertions.assertEquals(action.getClass(), GetCustomerOrdersServletAction.class);
    }

    @Test
    void buildGetCustomerActionInvalidInnerTest() throws InvalidEndPointException {
        Mockito.when(request.getPathInfo()).thenReturn("/1/invalid");

        Assertions.assertThrows(InvalidEndPointException.class, () -> builder.buildGetAction());
    }

    @Test
    void buildGetCustomerActionInvalidEndPointTest() {
        Mockito.when(request.getPathInfo()).thenReturn("/invalid");

        Assertions.assertThrows(InvalidEndPointException.class, () -> builder.buildGetAction());
    }

    @Test
    void buildGetCustomerActionLongEndPointTest() {
        Mockito.when(request.getPathInfo()).thenReturn("/1/test/test");

        Assertions.assertThrows(InvalidEndPointException.class, () -> builder.buildGetAction());
    }

    @Test
    void buildPostCustomerActionTest() throws InvalidEndPointException {
        Mockito.when(request.getPathInfo()).thenReturn(null);

        ServletAction action = builder.buildPostAction();

        Assertions.assertNotNull(action);
        Assertions.assertEquals(action.getClass(), PostCustomerServletAction.class);
    }

    @Test
    void buildPostCustomerActionInvalidEndPointTest() {
        Mockito.when(request.getPathInfo()).thenReturn("any");

        Assertions.assertThrows(InvalidEndPointException.class, () -> builder.buildPostAction());
    }

    @Test
    void buildPatchCustomerActionTest() throws InvalidEndPointException {
        Mockito.when(request.getPathInfo()).thenReturn("/1");
        ServletAction action = builder.buildPatchAction();
        Assertions.assertNotNull(action);
        Assertions.assertEquals(action.getClass(), PatchCustomerServletAction.class);
    }

    @Test
    void buildPatchCustomerActionInvalidEndPointTest() {
        Mockito.when(request.getPathInfo()).thenReturn("/invalid");
        Assertions.assertThrows(InvalidEndPointException.class, () -> builder.buildPatchAction());
    }

    @Test
    void buildPatchCustomerActionInvalidEndPointNullTest() {
        Mockito.when(request.getPathInfo()).thenReturn(null);
        Assertions.assertThrows(InvalidEndPointException.class, () -> builder.buildPatchAction());
    }

    @Test
    void buildDeleteCustomerActionTest() throws InvalidEndPointException {
        Mockito.when(request.getPathInfo()).thenReturn("/1");
        ServletAction action = builder.buildDeleteAction();
        Assertions.assertNotNull(action);
        Assertions.assertEquals(action.getClass(), DeleteCustomerServletAction.class);
    }

    @Test
    void buildDeleteCustomerActionInvalidEndPointTest() {
        Mockito.when(request.getPathInfo()).thenReturn("/invalid");
        Assertions.assertThrows(InvalidEndPointException.class, () -> builder.buildDeleteAction());
    }

    @Test
    void buildDeleteCustomerActionInvalidEndPointNullTest() {
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
