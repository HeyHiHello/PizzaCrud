package org.example.pizzacrud.database.repository;

import org.example.pizzacrud.database.TestDbInitializer;
import org.example.pizzacrud.database.datasource.DataSourcePropertiesProvider;
import org.example.pizzacrud.database.entity.Customer;
import org.example.pizzacrud.database.entity.Order;
import org.example.pizzacrud.database.entity.Pizza;
import org.example.pizzacrud.database.repository.exception.InternalDatabaseException;
import org.example.pizzacrud.database.repository.exception.NoChangesMadeException;
import org.example.pizzacrud.database.repository.exception.WrongKeyException;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

class OrderRepositoryTest {
    private final OrderRepository orderRepository = RepositoryBuilder.buildOrderRepository();
    private static MockedStatic<DataSourcePropertiesProvider> datasourcePropertiesProviderMockedStatic;
    @Container
    public static MySQLContainer<?> mySQLContainer = TestDbInitializer.buildMySQLContainer();


    @BeforeAll
    public static void initDataSource() {
        mySQLContainer.start();
        datasourcePropertiesProviderMockedStatic =
                TestDbInitializer.mockDataSourcePropertiesWithMySQLContainer(mySQLContainer);

    }

    @AfterAll
    public static void closeDataSource() {
        mySQLContainer.stop();
        datasourcePropertiesProviderMockedStatic.close();
    }

    @BeforeEach
    public void setUp() {
        TestDbInitializer.dropAll();
        TestDbInitializer.createAll();
        TestDbInitializer.insertTestData();
    }

    @Test
    void findAllTest() throws SQLException {
        List<Order> orders = orderRepository.findAll();
        Assertions.assertEquals(4, orders.size());
    }

    @Test
    void findByIdTest() {
        Optional<Order> order = orderRepository.findById(1);
        Assertions.assertTrue(order.isPresent());
        Assertions.assertEquals(2, order.get().getPizzas().size());
        Customer customer = order.get().getCustomer();
        Assertions.assertEquals(1, customer.getId());
    }

    @Test
    void findByIdFail() {
        Optional<Order> order = orderRepository.findById(1000);
        Assertions.assertFalse(order.isPresent());
    }

    @Test
    void findByInvalidIdTest() {
        Assertions.assertThrows(WrongKeyException.class, () -> orderRepository.findById(-1));
    }

    @Test
    void createTest() throws SQLException {
        Order order = new Order();
        Customer customer = RepositoryBuilder.buildCustomerRepository().findAll().get(0);
        order.setCustomer(customer);
        List<Pizza> pizzas = RepositoryBuilder.buildPizzaRepository().findAll();
        order.setPizzas(pizzas);

        orderRepository.create(order);

        Assertions.assertNotEquals(0, order.getId());
    }

    @Test
    void createFailTest() throws SQLException {
        Order order = new Order();
        Customer customer = RepositoryBuilder.buildCustomerRepository().findAll().get(0);
        customer.setId(1000);
        order.setCustomer(customer);
        List<Pizza> pizzas = RepositoryBuilder.buildPizzaRepository().findAll();
        order.setPizzas(pizzas);

        Assertions.assertThrows(InternalDatabaseException.class, () -> orderRepository.create(order));
    }

    @Test
    void deleteTest() throws SQLException {
        orderRepository.delete(1);
        Optional<Order> order = orderRepository.findById(1);
        Assertions.assertFalse(order.isPresent());
    }

    @Test
    void deleteFailTest() {
        Assertions.assertThrows(NoChangesMadeException.class, () -> orderRepository.delete(1000));
    }

    @Test
    void existsTest() throws SQLException {
        Assertions.assertTrue(orderRepository.exists(1));
    }

    @Test
    void doesntExistTest() throws SQLException {
        Assertions.assertFalse(orderRepository.exists(1000));
    }

    @Test
    void findByCustomerIdTest() throws SQLException {
        List<Order> orders = orderRepository.findAllByCustomerId(2);
        Assertions.assertEquals(2, orders.size());
    }
}
