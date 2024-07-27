package org.example.pizzacrud.database.repository;

import org.example.pizzacrud.database.TestDbInitializer;
import org.example.pizzacrud.database.entity.Address;
import org.example.pizzacrud.database.entity.Customer;
import org.example.pizzacrud.database.repository.exception.NoChangesMadeException;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

class CustomerRepositoryTest {
    private final CustomerRepository customerRepository = RepositoryBuilder.buildCustomerRepository();
    private static MockedStatic datasourcePropertiesProviderMockedStatic;
    @Container
    public static MySQLContainer mySQLContainer = TestDbInitializer.buildMySQLContainer();


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
        List<Customer> customers = customerRepository.findAll();
        Assertions.assertEquals(2, customers.size());
    }

    @Test
    void findAllEmptyTest() throws SQLException {
        TestDbInitializer.dropAll();
        TestDbInitializer.createAll();

        List<Customer> customers = customerRepository.findAll();
        Assertions.assertEquals(0, customers.size());
    }

    @Test
    void findByIdTest() throws SQLException {
        Optional<Customer> customer = customerRepository.findById(1);
        Assertions.assertTrue(customer.isPresent());
    }

    @Test
    void findByIdFailTest() throws SQLException {
        Optional<Customer> customer = customerRepository.findById(1000);
        Assertions.assertFalse(customer.isPresent());
    }

    @Test
    void saveUpdateTest() throws SQLException {
        Customer customer = new Customer();
        customer.setId(1);
        customer.setFirstname("test");
        customer.setLastname("test");
        customer.setAddress(new Address("city", "street", "43"));

        customerRepository.save(customer);

        Customer customer1 = customerRepository.findById(1).get();

        Assertions.assertNotNull(customer1);
        Assertions.assertEquals(customer, customer1);
        Assertions.assertEquals(customer.getAddress(), customer1.getAddress());
    }

    @Test
    void updateFailTest() {
        Customer customer = new Customer();
        customer.setId(100);
        customer.setFirstname("test");
        customer.setLastname("test");
        customer.setAddress(new Address("city", "street", "43"));

        Assertions.assertThrows(NoChangesMadeException.class, () -> customerRepository.save(customer));
    }

    @Test
    void saveCreateTest() throws SQLException {
        Customer customer = new Customer();
        customer.setId(0);
        customer.setFirstname("test");
        customer.setLastname("test");
        customer.setAddress(new Address("city", "street", "43"));

        customerRepository.save(customer);

        Customer customer1 = customerRepository.findById(customer.getId()).get();

        Assertions.assertNotNull(customer1);
        Assertions.assertEquals(customer, customer1);
        Assertions.assertEquals(customer.getAddress(), customer1.getAddress());
    }

    @Test
    void createFailTest() throws SQLException {
        Customer customer = new Customer();
        customer.setId(0);
        customer.setFirstname(null);
        customer.setLastname(null);
        customer.setAddress(new Address("city", "street", "43"));
        Assertions.assertThrows(SQLException.class, () -> customerRepository.save(customer));
    }

    @Test
    void deleteTest() throws SQLException {
        customerRepository.delete(1);

        Optional<Customer> customerOpt = customerRepository.findById(1);

        Assertions.assertTrue(customerOpt.isEmpty());
    }

    @Test
    void deleteFailTest() throws SQLException {
        Assertions.assertThrows(NoChangesMadeException.class, () -> customerRepository.delete(1000));

        Optional<Customer> customerOpt = customerRepository.findById(1);

        Assertions.assertTrue(customerOpt.isPresent());
    }

    @Test
    void existsTest() throws SQLException {
        boolean exists = customerRepository.exists(1);
        Assertions.assertTrue(exists);
    }


}
