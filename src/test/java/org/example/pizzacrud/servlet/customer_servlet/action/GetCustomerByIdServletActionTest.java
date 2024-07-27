package org.example.pizzacrud.servlet.customer_servlet.action;

import jakarta.servlet.http.HttpServletResponse;
import org.example.pizzacrud.database.entity.Address;
import org.example.pizzacrud.database.entity.Customer;
import org.example.pizzacrud.database.repository.exception.NoObjectException;
import org.example.pizzacrud.service.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

class GetCustomerByIdServletActionTest {
    private static final int id = 1;
    private static HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
    private static CustomerService service = Mockito.mock(CustomerService.class);

    private static StringWriter out = new StringWriter();
    private static GetCustomerByIdServletAction action;

    Address address = new Address(id, "test", "test", "test");
    Customer customer = new Customer(id, "test", "test", address);
    String customerJson = "{\"firstName\":\"test\",\"lastName\":\"test\",\"address\":{\"city\":\"test\",\"street\":\"test\",\"id\":1,\"building\":\"test\"},\"id\":1}\n";

    @BeforeEach
    public void setUp() throws IOException {
        response = Mockito.mock(HttpServletResponse.class);
        service = Mockito.mock(CustomerService.class);
        action = new GetCustomerByIdServletAction(service, id, response);
    }

    @Test
    void executeTest() throws IOException {
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(out));
        Mockito.when(service.getById(id)).thenReturn(customer);

        action.execute();

        Assertions.assertEquals(customerJson, out.toString());
    }

    @Test
    void executeIOExceptionTest() throws IOException {
        Mockito.when(response.getWriter()).thenThrow(IOException.class);
        Mockito.when(service.getById(id)).thenReturn(customer);

        action.execute();

        Mockito.verify(response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    void executeNoObjectExceptionTest() {
        Mockito.when(service.getById(Mockito.anyInt())).thenThrow(NoObjectException.class);

        action.execute();

        Mockito.verify(response).setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
}
