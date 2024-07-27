package org.example.pizzacrud.database.entity;

import org.example.pizzacrud.database.TestDbInitializer;
import org.example.pizzacrud.database.repository.CustomerRepository;
import org.example.pizzacrud.database.repository.RepositoryBuilder;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.sql.SQLException;
import java.util.List;

public class OrderTest {
    private final CustomerRepository customerRepository = RepositoryBuilder.buildCustomerRepository();
    private static MockedStatic datasourcePropertiesProviderMockedStatic;
    @Container
    public static MySQLContainer mySQLContainer = TestDbInitializer.buildMySQLContainer();

    Order order;
    Customer customer;

    @BeforeAll
    public static void startContainer() {
        mySQLContainer.start();
        datasourcePropertiesProviderMockedStatic =
                TestDbInitializer.mockDataSourcePropertiesWithMySQLContainer(mySQLContainer);
    }

    @AfterAll
    public static void stopContainer() {
        mySQLContainer.stop();
        datasourcePropertiesProviderMockedStatic.close();
    }

    @BeforeEach
    public void setUp() {
        customer = new Customer(1, "test", "test");
        order = new Order(1, customer, List.of(new Pizza(1, "test", 123)));
    }

    @Test
    void constructorTest() {
        Order order1 = new Order();
        Assertions.assertNotNull(order1);

    }

    @Test
    void getIdTest() {
        Assertions.assertEquals(1, order.getId());
    }

    @Test
    void setIdTest() {
        order.setId(2);
        Assertions.assertEquals(2, order.getId());
    }

    @Test
    void getCustomerLazyTest() throws SQLException {
        TestDbInitializer.dropAll();
        TestDbInitializer.createAll();
        TestDbInitializer.insertTestData();
        Order order1 = RepositoryBuilder.buildOrderRepository().findById(1).get();
        Assertions.assertNotNull(order1.getCustomer());
    }

    @Test
    void getCustomerLazyZeroIdTest() throws SQLException {
        TestDbInitializer.dropAll();
        TestDbInitializer.createAll();
        TestDbInitializer.insertTestData();
        Order order1 = RepositoryBuilder.buildOrderRepository().findById(1).get();
        order1.getCustomer().setId(0);
        Assertions.assertNull(order1.getCustomer());
    }

    @Test
    void setCustomerTest() {
        order.setCustomer(null);
        Assertions.assertNull(order.getCustomer());
    }

    @Test
    void getPizzaTest() {
        Assertions.assertEquals(1, order.getPizzas().size());
    }

    @Test
    void setPizzaTest() {
        order.setPizzas(null);
        Assertions.assertNull(order.getPizzas());
    }
}
