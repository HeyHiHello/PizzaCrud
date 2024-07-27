package org.example.pizzacrud.servlet.customer_servlet.action;

import jakarta.servlet.http.HttpServletResponse;
import org.example.pizzacrud.database.entity.Address;
import org.example.pizzacrud.database.entity.Customer;
import org.example.pizzacrud.database.repository.exception.InternalDatabaseException;
import org.example.pizzacrud.service.CustomerService;
import org.example.pizzacrud.servlet.ingredient_servlet.action.GetAllIngredientsServletAction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

class GetAllCustomersServletActionTest {
    private static CustomerService service;
    private static HttpServletResponse response;

    private static GetAllCustomersServletAction action;

    private static StringWriter stringWriter;

    @BeforeEach
    public void setUp() throws IOException {
        stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        response = Mockito.mock(HttpServletResponse.class);
        Mockito.when(response.getWriter()).thenReturn(printWriter);

        service = Mockito.mock(CustomerService.class);
        action = new GetAllCustomersServletAction(service, response);
    }

    @Test
    void constructorTest() {
        GetAllIngredientsServletAction action1 = new GetAllIngredientsServletAction(response);
        Assertions.assertNotNull(action1);
    }

    @Test
    void executeTest() throws InternalDatabaseException {
        Address address = new Address(1, "test", "test", "test");
        Customer customer = new Customer(1, "test", "test", address);
        List<Customer> testCustomers = List.of(customer, customer);
        String testCustomersJsonStr = "[{\"firstName\":\"test\",\"lastName\":\"test\",\"address\":{\"city\":\"test\",\"street\":\"test\",\"id\":1,\"building\":\"test\"},\"id\":1},{\"firstName\":\"test\",\"lastName\":\"test\",\"address\":{\"city\":\"test\",\"street\":\"test\",\"id\":1,\"building\":\"test\"},\"id\":1}]\n";
        Mockito.when(service.getAll()).thenReturn(testCustomers);
        action.execute();

        Mockito.verify(response).setStatus(200);
        Assertions.assertEquals(testCustomersJsonStr, stringWriter.toString());
    }

    @Test
    void executeInternalDatabaseExceptionTest() throws InternalDatabaseException {
        Mockito.when(service.getAll()).thenThrow(InternalDatabaseException.class);
        action.execute();
        Mockito.verify(response).setStatus(500);
    }
}
