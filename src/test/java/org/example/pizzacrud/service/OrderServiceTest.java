package org.example.pizzacrud.service;

import org.example.pizzacrud.database.entity.Address;
import org.example.pizzacrud.database.entity.Customer;
import org.example.pizzacrud.database.entity.Order;
import org.example.pizzacrud.database.entity.Pizza;
import org.example.pizzacrud.database.repository.OrderRepository;
import org.example.pizzacrud.database.repository.exception.InternalDatabaseException;
import org.example.pizzacrud.database.repository.exception.NoObjectException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

class OrderServiceTest {
    private static OrderRepository repository = Mockito.mock(OrderRepository.class);
    private static OrderService service = new OrderService();
    private final Address testAddress = new Address(1, "test_city", "test_street", "test_building");
    private final Customer testCustomer = new Customer(1, "test_firstname", "test_lastname", testAddress);
    private final Order testOrder = new Order(1, testCustomer, List.of(new Pizza(), new Pizza()));

    @BeforeAll
    public static void setUp() {
        service.setOrderRepository(repository);
    }

    @Test
    void getAllTest() throws SQLException {
        Mockito.when(repository.findAll()).thenReturn(List.of(testOrder, testOrder));

        List<Order> orders = service.getAll();
        Assertions.assertEquals(2, orders.size());
    }

    @Test
    void getAllFailTest() throws SQLException {
        Mockito.when(repository.findAll()).thenThrow(SQLException.class);
        Assertions.assertThrows(InternalDatabaseException.class, () -> service.getAll());
    }

    @Test
    void getByIdTest() {
        Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(Optional.of(testOrder));
        Order order = service.getById(1);
        Assertions.assertNotNull(order);
        Assertions.assertEquals(testOrder, order);
    }

    @Test
    void getByIdFailTest() {
        Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        Assertions.assertThrows(NoObjectException.class, () -> service.getById(1));
    }

    @Test
    void saveTest() throws SQLException {
        Mockito.when(repository.save(testOrder)).thenReturn(testOrder);
        Assertions.assertDoesNotThrow(() -> service.save(testOrder));
    }

    @Test
    void saveFailTest() throws SQLException {
        Mockito.when(repository.create(Mockito.any())).thenThrow(SQLException.class);
        Assertions.assertThrows(InternalDatabaseException.class, () -> service.save(testOrder));
    }

    @Test
    void deleteTest() throws SQLException {
        Mockito.doNothing().when(repository).delete(1);
        Assertions.assertDoesNotThrow(() -> service.delete(1));
    }

    @Test
    void deleteFailTest() throws SQLException {
        Mockito.doThrow(SQLException.class).when(repository).delete(1);
        Assertions.assertThrows(InternalDatabaseException.class, () -> service.delete(1));
    }
}
