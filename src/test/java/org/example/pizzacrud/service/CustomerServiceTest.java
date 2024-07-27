package org.example.pizzacrud.service;

import org.example.pizzacrud.database.entity.Address;
import org.example.pizzacrud.database.entity.Customer;
import org.example.pizzacrud.database.repository.CustomerRepository;
import org.example.pizzacrud.database.repository.exception.InternalDatabaseException;
import org.example.pizzacrud.database.repository.exception.NoChangesMadeException;
import org.example.pizzacrud.database.repository.exception.NoObjectException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CustomerServiceTest {
    private final static CustomerRepository repository = Mockito.mock(CustomerRepository.class);
    private final static CustomerService service = new CustomerService();
    private final Address testAddress = new Address(1, "test_city", "test_street", "test_building");
    private final Customer testCustomer = new Customer(1, "test_firstname", "test_lastname", testAddress);

    @BeforeAll
    public static void setUp() {
        service.setCustomerRepository(repository);
    }

    @Test
    void getByIdTest() {
        Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(Optional.of(testCustomer));
        Customer customer = service.getById(1);
        Assertions.assertNotNull(customer);
        Assertions.assertEquals(testCustomer.getFirstname(), customer.getFirstname());
    }

    @Test
    void getByIdFailTest() {
        Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        Assertions.assertThrows(NoObjectException.class, () -> service.getById(1));
    }

    @Test
    void getAllTest() throws InternalDatabaseException {
        Mockito.when(repository.findAll()).thenReturn(List.of(testCustomer, testCustomer));

        List<Customer> customers = service.getAll();
        Assertions.assertNotNull(customers);
        Assertions.assertEquals(2, customers.size());
    }

    @Test
    void saveTest() throws SQLException {
        Mockito.when(repository.save(testCustomer)).thenReturn(testCustomer);

        Customer customer = service.save(testCustomer);
        Assertions.assertNotNull(customer);
        Assertions.assertEquals(testCustomer, customer);
    }

    @Test
    void saveFailTest() throws SQLException {
        Mockito.when(repository.save(testCustomer)).thenThrow(SQLException.class);

        Assertions.assertThrows(InternalDatabaseException.class, () -> service.save(testCustomer));
    }

    @Test
    void deleteTest() throws SQLException {
        Mockito.doNothing().when(repository).delete(Mockito.anyInt());
        Assertions.assertDoesNotThrow(() -> service.delete(1));
    }

    @Test
    void deleteInternalDatabaseExceptionTest() throws SQLException {
        Mockito.doThrow(SQLException.class).when(repository).delete(Mockito.anyInt());
        Assertions.assertThrows(InternalDatabaseException.class, () -> service.delete(1));
    }

    @Test
    void deleteNoChangesMadeExceptionTest() throws SQLException {
        Mockito.doThrow(NoChangesMadeException.class).when(repository).delete(Mockito.anyInt());
        Assertions.assertThrows(NoChangesMadeException.class, () -> service.delete(1));
    }
}
