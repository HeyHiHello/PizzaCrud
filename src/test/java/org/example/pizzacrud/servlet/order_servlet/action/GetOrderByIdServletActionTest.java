package org.example.pizzacrud.servlet.order_servlet.action;

import jakarta.servlet.http.HttpServletResponse;
import org.example.pizzacrud.database.entity.Address;
import org.example.pizzacrud.database.entity.Customer;
import org.example.pizzacrud.database.entity.Order;
import org.example.pizzacrud.database.entity.Pizza;
import org.example.pizzacrud.database.repository.exception.NoObjectException;
import org.example.pizzacrud.mapper.OrderMapper;
import org.example.pizzacrud.service.OrderService;
import org.example.pizzacrud.servlet.order_servlet.OrderDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

class GetOrderByIdServletActionTest {
    private static int id;
    private static OrderService service;
    private static HttpServletResponse response;
    private static Order order;
    private static String orderJson;
    private static StringWriter stringWriter;
    private static GetOrderByIdServletAction action;
    private static MockedStatic<OrderMapper> mockedOrderMapper;

    @BeforeEach
    public void setUp() throws Exception {
        id = 1;
        service = Mockito.mock(OrderService.class);
        response = Mockito.mock(HttpServletResponse.class);
        stringWriter = new StringWriter();
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));
        Customer customer = new Customer(1, "test", "test", new Address(1, "test", "test", "test"));
        Pizza pizza = new Pizza(1, "test", 123);
        order = new Order(1, customer, List.of(pizza, pizza));
        orderJson = "{\"pizzasId\":[1,1],\"customerId\":1,\"id\":1}\n";
        action = new GetOrderByIdServletAction(service, id, response);

//        mockedOrderMapper.when(OrderMapper::orderToOrderDto).thenReturn()
    }

    @Test
    void executeTest() {
        Mockito.when(service.getById(id)).thenReturn(order);

        action.execute();

        Mockito.verify(response).setStatus(HttpServletResponse.SC_OK);
        Assertions.assertEquals(orderJson, stringWriter.toString());
    }

    @Test
    void executeNoObjectExceptionTest() {
        Mockito.when(service.getById(id)).thenThrow(NoObjectException.class);

        action.execute();

        Mockito.verify(response).setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    void executeIOExceptionTest() throws IOException {
        Mockito.when(service.getById(id)).thenReturn(order);
        Mockito.when(response.getWriter()).thenThrow(IOException.class);
        action.execute();
        Mockito.verify(response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
}
