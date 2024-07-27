package org.example.pizzacrud.servlet.order_servlet.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.pizzacrud.mapper.OrderMapper;
import org.example.pizzacrud.database.entity.Address;
import org.example.pizzacrud.database.entity.Customer;
import org.example.pizzacrud.database.entity.Order;
import org.example.pizzacrud.database.entity.Pizza;
import org.example.pizzacrud.database.repository.exception.InternalDatabaseException;
import org.example.pizzacrud.service.OrderService;
import org.example.pizzacrud.servlet.order_servlet.OrderDto;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.*;
import java.util.List;

class PostOrderServletActionTest {
    private static OrderService service;
    private static HttpServletRequest request;
    private static HttpServletResponse response;
    private static Order order;
    private static String orderJson;
    private static StringWriter out;
    private static PostOrderServletAction action;
    private static MockedStatic orderMapperMockedStatic;


    @BeforeAll
    static void setMapper() throws InternalDatabaseException {
        orderMapperMockedStatic = Mockito.mockStatic(OrderMapper.class);
        Mockito.when(OrderMapper.orderToOrderDto(Mockito.any())).thenReturn(new OrderDto(1, List.of(1L, 1L), 1));
        Mockito.when(OrderMapper.orderDtoToOrder(Mockito.any())).thenReturn(order);
    }

    @AfterAll
    static void close() {
        orderMapperMockedStatic.close();
    }

    @BeforeEach
    public void setUp() throws IOException, InternalDatabaseException {
        service = Mockito.mock(OrderService.class);
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        Customer customer = new Customer(1, "test", "test", new Address(1, "test", "test", "test"));
        Pizza pizza = new Pizza(1, "test", 123);
        order = new Order(1, customer, List.of(pizza, pizza));
        orderJson = "{\"pizzasId\":[1,1],\"customerId\":1,\"id\":1}\n";
        StringReader reader = new StringReader(orderJson);
        Mockito.when(request.getReader()).thenReturn(new BufferedReader(reader));
        out = new StringWriter();
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(out));
        action = new PostOrderServletAction(service, request, response);
    }

    @Test
    void execute() {
        Mockito.when(service.getById(Mockito.anyInt())).thenReturn(order);
        action.execute();
        Mockito.verify(response).setStatus(HttpServletResponse.SC_CREATED);
        Assertions.assertEquals(orderJson, out.toString());
    }

    @Test
    void executeInternalDatabaseExceptionTest() throws InternalDatabaseException {
        Mockito.when(service.save(Mockito.any())).thenThrow(InternalDatabaseException.class);
        action.execute();
        Mockito.verify(response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
}
