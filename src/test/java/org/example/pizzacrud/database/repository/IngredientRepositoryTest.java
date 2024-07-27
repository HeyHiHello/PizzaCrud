package org.example.pizzacrud.database.repository;

import org.example.pizzacrud.database.entity.Ingredient;
import org.example.pizzacrud.database.repository.exception.NoChangesMadeException;
import org.example.pizzacrud.database.repository.exception.NoObjectException;
import org.example.pizzacrud.database.repository.exception.WrongKeyException;
import org.example.pizzacrud.database.TestDbInitializer;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Testcontainers
public class IngredientRepositoryTest {
    private final Repository<Ingredient, Integer> ingredientRepository = RepositoryBuilder.buildIngredientRepository();
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
        List<Ingredient> ingredients = null;
        ingredients = ingredientRepository.findAll();

        Assertions.assertNotNull(ingredients);
        Assertions.assertEquals(6, ingredients.size());
    }

    @Test
    void getAllEmptyDBTest() throws SQLException {
        TestDbInitializer.dropAll();
        TestDbInitializer.createAll();
        List<Ingredient> ingredients = ingredientRepository.findAll();
        Assertions.assertEquals(0, ingredients.size());
    }

    @Test
    void getByIdSuccessTest() throws SQLException {
        Optional<Ingredient> ingredientOpt = ingredientRepository.findById(1);

        Assertions.assertTrue(ingredientOpt.isPresent());
    }

    @Test
    void getByIdFailTest() throws SQLException {
        Optional<Ingredient> ingredientOpt = ingredientRepository.findById(1000);
        Assertions.assertFalse(ingredientOpt.isPresent());
    }

    @Test
    void findByIdWrongKeyTest() {
        Assertions.assertThrows(WrongKeyException.class, () -> ingredientRepository.findById(-1));
    }

    @Test
    void saveCreateTest() throws SQLException {
        Ingredient ingredient = new Ingredient();
        ingredient.setName("test");

        Assertions.assertDoesNotThrow(() -> ingredientRepository.save(ingredient));

        Ingredient ingredient1 = ingredientRepository.findById(ingredient.getId()).orElse(null);
        Assertions.assertNotNull(ingredient1);
        Assertions.assertEquals(ingredient, ingredient1);
    }

    @Test
    void saveWithNullTest() {
        Assertions.assertThrows(NoObjectException.class, () -> ingredientRepository.save(null));
    }

    @Test
    void createWithNullTest() {
        Assertions.assertThrows(NoObjectException.class, () -> ingredientRepository.create(null));
    }

    @Test
    void createNotFullObjectTest() {
        Ingredient ingredient = new Ingredient();

        Assertions.assertThrows(SQLException.class, () -> ingredientRepository.create(ingredient));
    }

    @Test
    void saveUpdateTest() throws SQLException {
        final Ingredient ingredient = ingredientRepository.findById(1).orElse(null);

        assert ingredient != null;
        ingredient.setName("Test");
        Assertions.assertDoesNotThrow(() -> ingredientRepository.save(ingredient));

        final Ingredient ingredient1 = ingredientRepository.findById(1).orElse(null);
        Assertions.assertNotNull(ingredient1);
        Assertions.assertEquals("Test", ingredient1.getName());
    }

    @Test
    void saveUpdateFailTest() throws SQLException {
        final Ingredient ingredient = ingredientRepository.findById(1).orElse(null);

        assert ingredient != null;
        ingredient.setName("Test");
        ingredient.setId(1000);
        Assertions.assertThrows(NoChangesMadeException.class, () -> ingredientRepository.save(ingredient));
    }

    @Test
    void updateWithNullTest() {
        Assertions.assertThrows(NoObjectException.class, () -> ingredientRepository.update(null));
    }

    @Test
    void deleteSuccessTest() throws SQLException {
        Assertions.assertDoesNotThrow(() -> ingredientRepository.delete(1));
        Optional<Ingredient> ingredientOpt = ingredientRepository.findById(1);
        Assertions.assertTrue(ingredientOpt.isEmpty());
    }

    @Test
    void deleteFailTest() {
        Assertions.assertThrows(NoChangesMadeException.class, () -> ingredientRepository.delete(1000));
    }

    @Test
    void deleteWrongKeyTest() {
        Assertions.assertThrows(WrongKeyException.class, () -> ingredientRepository.delete(-1));
    }

    @Test
    void existsSuccessTest() throws SQLException {
        Assertions.assertTrue(ingredientRepository.exists(1));
    }

    @Test
    void existsFailTest() throws SQLException {
        Assertions.assertFalse(ingredientRepository.exists(1000));
    }

    @Test
    void findByPizzaIdTest() throws SQLException {
        IngredientRepository ingredientRepository1 = RepositoryBuilder.buildIngredientRepository();
        List<Ingredient> ingredients = ingredientRepository1.findByPizzaId(1);
        Assertions.assertNotNull(ingredients);
        Assertions.assertEquals(4, ingredients.size());
    }

    @Test
    void findPizzaByIdWrongKeyTest() {
        IngredientRepository ingredientRepository1 = RepositoryBuilder.buildIngredientRepository();
        Assertions.assertThrows(WrongKeyException.class, () -> ingredientRepository1.findByPizzaId(-1));
    }

    @Test
    void saveAllTest() throws SQLException {
        IngredientRepository ingredientRepository1 = RepositoryBuilder.buildIngredientRepository();
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("test1"));
        ingredients.add(new Ingredient("test2"));
        ingredients.add(new Ingredient("test3"));
        ingredients.add(new Ingredient("test4"));

        ingredientRepository1.saveAll(ingredients);

        List<Ingredient> ingredients1 = ingredientRepository1.findAll();
        Assertions.assertNotNull(ingredients1);
        Assertions.assertEquals(10, ingredients1.size());
    }
}
