package org.example.pizzacrud.database.repository;

import org.example.pizzacrud.database.TestDbInitializer;
import org.example.pizzacrud.database.entity.Pizza;
import org.example.pizzacrud.database.repository.exception.InternalDatabaseException;
import org.example.pizzacrud.database.repository.exception.NoChangesMadeException;
import org.example.pizzacrud.database.repository.exception.NoObjectException;
import org.example.pizzacrud.database.repository.exception.WrongKeyException;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PizzaRepositoryTest {
    private static PizzaRepository pizzaRepository = RepositoryBuilder.buildPizzaRepository();
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
    void getAllTest() throws SQLException {
        List<Pizza> pizzas = pizzaRepository.findAll();
        Assertions.assertNotNull(pizzas);
        Assertions.assertEquals(2, pizzas.size());
    }

    @Test
    void getAllEmptyDbTest() throws SQLException {
        TestDbInitializer.dropAll();
        TestDbInitializer.createAll();
        List<Pizza> pizzas = pizzaRepository.findAll();
        Assertions.assertNotNull(pizzas);
        Assertions.assertEquals(0, pizzas.size());
    }

    @Test
    void getByIdSuccessTest() throws SQLException {
        Optional<Pizza> pizzaOpt = pizzaRepository.findById(1);
        Assertions.assertTrue(pizzaOpt.isPresent());
    }

    @Test
    void getByIdFailTest() throws SQLException {
        Optional<Pizza> pizzaOpt = pizzaRepository.findById(1000);
        Assertions.assertFalse(pizzaOpt.isPresent());
    }

    @Test
    void findByIdWrongKeyTest() {
        Assertions.assertThrows(WrongKeyException.class, () -> pizzaRepository.findById(-1));
    }

    @Test
    void saveCreateTest() throws SQLException {
        Pizza pizza = new Pizza();
        pizza.setName("Test");

        Assertions.assertDoesNotThrow(() -> pizzaRepository.save(pizza));

        Pizza pizza1 = pizzaRepository.findById(pizza.getId()).orElse(null);

        Assertions.assertNotNull(pizza1);
        Assertions.assertEquals(pizza, pizza1);
    }

    @Test
    void saveWithNullTest() {
        Assertions.assertThrows(NoObjectException.class, () -> pizzaRepository.save(null));
    }

    @Test
    void createWithNullTest() {
        Assertions.assertThrows(NoObjectException.class, () -> pizzaRepository.save(null));
    }

    @Test
    void saveUpdateTest() throws SQLException {
        final Pizza pizza = pizzaRepository.findById(1).orElse(null);

        assert pizza != null;
        pizza.setName("Test");
        Assertions.assertDoesNotThrow(() -> pizzaRepository.save(pizza));

        Pizza pizza1 = pizzaRepository.findById(pizza.getId()).orElse(null);

        Assertions.assertNotNull(pizza1);
        Assertions.assertEquals(pizza, pizza1);
    }

    @Test
    void saveUpdateFailTest() throws SQLException {
        final Pizza pizza = pizzaRepository.findById(1).orElse(null);

        assert pizza != null;
        pizza.setName("Test");
        pizza.setId(1000);

        Assertions.assertThrows(NoChangesMadeException.class, () -> pizzaRepository.save(pizza));
    }

    @Test
    void updateWithNullTest() {
        Assertions.assertThrows(NoObjectException.class, () -> pizzaRepository.save(null));
    }

    @Test
    void deleteSuccessTest() throws SQLException {
        Assertions.assertDoesNotThrow(() -> pizzaRepository.delete(1));
        Optional<Pizza> pizzaOpt = pizzaRepository.findById(1);
        Assertions.assertTrue(pizzaOpt.isEmpty());
    }

    @Test
    void deleteFailTest() {
        Assertions.assertThrows(NoChangesMadeException.class, () -> pizzaRepository.delete(1000));
    }

    @Test
    void deleteWrongKeyTest() {
        Assertions.assertThrows(WrongKeyException.class, () -> pizzaRepository.delete(-1));
    }

    @Test
    void existsSuccessTest() throws SQLException {
        Assertions.assertTrue(pizzaRepository.exists(1));
    }

    @Test
    void existsFailTest() throws SQLException {
        Assertions.assertFalse(pizzaRepository.exists(1000));
    }

    @Test
    void existsWrongKeyTest() {
        Assertions.assertThrows(WrongKeyException.class, () -> pizzaRepository.exists(-1));
    }

    @Test
    void pizzasIngredientsTest() throws SQLException {
        Pizza pizza = pizzaRepository.findById(1).orElse(null);
        Assertions.assertNotNull(pizza);
        Assertions.assertEquals(4, pizza.getIngredients().size());

    }

    @Test
    void savePizzaIngredientsTest() throws InternalDatabaseException {
        Pizza pizza = pizzaRepository.findById(1).orElse(null);

        Assertions.assertNotNull(pizza);

        Assertions.assertDoesNotThrow(() -> pizzaRepository.savePizzasIngredients(pizza));

    }
}
