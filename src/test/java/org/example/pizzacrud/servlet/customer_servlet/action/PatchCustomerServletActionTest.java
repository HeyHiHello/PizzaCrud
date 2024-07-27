package org.example.pizzacrud.servlet.customer_servlet.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.pizzacrud.database.entity.Address;
import org.example.pizzacrud.database.entity.Customer;
import org.example.pizzacrud.database.repository.exception.InternalDatabaseException;
import org.example.pizzacrud.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.*;

class PatchCustomerServletActionTest {
    private static CustomerService service;
    private static int id;
    private static HttpServletRequest request;
    private static HttpServletResponse response;
    private static StringReader reader;
    private static StringWriter writer;
    private static Address address;
    private static Customer customer;
    private static String customerJson;
    private static PatchCustomerServletAction action;

    @BeforeEach
    void setUp() throws IOException {
        service = Mockito.mock(CustomerService.class);
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        customerJson = "{\"firstName\":\"test\",\"lastName\":\"test\",\"address\":{\"city\":\"test\",\"street\":\"test\",\"id\":1,\"building\":\"test\"},\"id\":1}\n";
        reader = new StringReader(customerJson);
        writer = new StringWriter();
        Mockito.when(request.getReader()).thenReturn(new BufferedReader(reader));
        Mockito.when(response.getWriter()).thenReturn(new PrintWriter(writer));
        id = 1;
        address = new Address(id, "test", "test", "test");
        customer = new Customer(id, "test", "test", address);
        action = new PatchCustomerServletAction(service, id, request, response);
    }

    @Test
    void executeTest() throws InternalDatabaseException {
        Mockito.when(service.save(customer)).thenReturn(customer);

        action.execute();

        Mockito.verify(response).setStatus(HttpServletResponse.SC_OK);

    }

    @Test
    void executeInternalDatabaseErrorTest() throws InternalDatabaseException {
        Mockito.when(service.save(customer)).thenThrow(InternalDatabaseException.class);
        action.execute();
        Mockito.verify(response).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
}
