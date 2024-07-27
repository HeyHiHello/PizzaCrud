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

public class CustomerTest {
    private final CustomerRepository customerRepository = RepositoryBuilder.buildCustomerRepository();
    private static MockedStatic datasourcePropertiesProviderMockedStatic;
    @Container
    public static MySQLContainer mySQLContainer = TestDbInitializer.buildMySQLContainer();

    Customer customer;

    @AfterAll
    public static void closeDataSource() {
    }

    @BeforeEach
    public void setUp() {
        customer = new Customer(1, "testFirstName", "testLastName");
        Address address = new Address("testCity", "testStreet", "testBuilding");
        customer.setAddress(address);
    }

    @Test
    void getIdTest() {
        Assertions.assertEquals(1, customer.getId());
    }

    @Test
    void setIdTest() {
        customer.setId(2);
        Assertions.assertEquals(2, customer.getId());
    }

    @Test
    void getFirstNameTest() {
        Assertions.assertEquals("testFirstName", customer.getFirstname());
    }

    @Test
    void setFirstNameTest() {
        customer.setFirstname("newFirstName");
        Assertions.assertEquals("newFirstName", customer.getFirstname());
    }

    @Test
    void getLastNameTest() {
        Assertions.assertEquals("testLastName", customer.getLastname());
    }

    @Test
    void setLastNameTest() {
        customer.setLastname("newLastName");
        Assertions.assertEquals("newLastName", customer.getLastname());
    }

    @Test
    void getAddressTest() {
        Assertions.assertEquals("testCity", customer.getAddress().getCity());
        Assertions.assertEquals("testStreet", customer.getAddress().getStreet());
        Assertions.assertEquals("testBuilding", customer.getAddress().getBuilding());
    }

    @Test
    void setAddressTest() {
        customer.setAddress(new Address("testCity2", "testStreet2", "testBuilding2"));
        Assertions.assertEquals("testCity2", customer.getAddress().getCity());
        Assertions.assertEquals("testStreet2", customer.getAddress().getStreet());
        Assertions.assertEquals("testBuilding2", customer.getAddress().getBuilding());
    }

    @Test
    void getOrdersLazyTest() throws SQLException {
        mySQLContainer.start();
        datasourcePropertiesProviderMockedStatic =
                TestDbInitializer.mockDataSourcePropertiesWithMySQLContainer(mySQLContainer);


        TestDbInitializer.dropAll();
        TestDbInitializer.createAll();
        TestDbInitializer.insertTestData();

        Customer customer1 = RepositoryBuilder.buildCustomerRepository().findById(1).get();
        List<Order> orders = customer1.getOrders();
        Assertions.assertEquals(2, orders.size());

        mySQLContainer.stop();
        datasourcePropertiesProviderMockedStatic.close();
    }

    @Test
    void equalsSameTest() {
        Assertions.assertTrue(customer.equals(customer));
    }

    @Test
    void equalsNullTest() {
        Assertions.assertFalse(customer.equals(null));
    }

    @Test
    void equalsDifferentClassTest() {
        Assertions.assertFalse(customer.equals(new Object()));
    }

    @Test
    void equalsFieldsTest() {
        Customer customer1 = new Customer(1, "testFirstName", "testLastName");
        Customer customer2 = new Customer(1, "testFirstName", "testLastName");

        Assertions.assertTrue(customer1.equals(customer2));
        customer2.setId(2);
        Assertions.assertFalse(customer1.equals(customer2));

        customer2.setId(1);
        customer2.setFirstname("newFirstName");
        Assertions.assertFalse(customer1.equals(customer2));

        customer2.setFirstname("testFirstName");
        customer2.setLastname("newLastName");
        Assertions.assertFalse(customer1.equals(customer2));
    }

}
