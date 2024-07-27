package org.example.pizzacrud.service;

import org.example.pizzacrud.database.entity.Ingredient;
import org.example.pizzacrud.database.repository.IngredientRepository;
import org.example.pizzacrud.database.repository.exception.NoChangesMadeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class IngredientServiceTest {
    private static IngredientRepository ingredientRepository;
    private static IngredientService service;

    @BeforeAll
    static void setUp() {
        ingredientRepository = Mockito.mock(IngredientRepository.class);
        service = ServiceBuilder.buildIngredientService();
        service.setIngredientRepository(ingredientRepository);
    }

    @Test
    void getAllTest() throws SQLException {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient(1, "test1"));
        ingredients.add(new Ingredient(2, "test2"));
        ingredients.add(new Ingredient(3, "test3"));
        ingredients.add(new Ingredient(4, "test4"));

        Mockito.when(ingredientRepository.findAll()).thenReturn(ingredients);

        Assertions.assertArrayEquals(ingredients.toArray(), service.getAll().toArray());
    }

    @Test
    void getByIdTest() throws SQLException {
        Ingredient ingredient = new Ingredient(1, "test1");

        Mockito.when(ingredientRepository.findById(1)).thenReturn(Optional.of(ingredient));

        Assertions.assertEquals(ingredient, service.getById(1));
    }

    @Test
    void deleteByIdTest() {
        Assertions.assertDoesNotThrow(() -> service.delete(1));
    }

    @Test
    void deleteByIdThrowsTest() throws SQLException, NoChangesMadeException {
        Mockito.doThrow(NoChangesMadeException.class).doNothing().when(ingredientRepository).delete(1);
        Assertions.assertThrows(NoChangesMadeException.class, () -> service.delete(1));
    }

    @Test
    void deleteByIngredientTest() {
        Ingredient ingredient = new Ingredient(1, "test1");
        Assertions.assertDoesNotThrow(() -> service.delete(ingredient));
    }

    @Test
    void deleteByIngredientThrowsTest() throws SQLException, NoChangesMadeException {
        Ingredient ingredient = new Ingredient(1, "test1");
        Mockito.doThrow(NoChangesMadeException.class).doNothing().when(ingredientRepository).delete(1);
        Assertions.assertThrows(NoChangesMadeException.class, () -> service.delete(ingredient));
    }

    @Test
    void saveAllSuccessTest() throws SQLException {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient(1, "test1"));
        ingredients.add(new Ingredient(2, "test2"));

        Mockito.when(ingredientRepository.saveAll(ingredients)).thenReturn(true);

        Assertions.assertDoesNotThrow(() -> service.saveAll(ingredients));
    }

    @Test
    void saveAllFailTest() throws SQLException {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient(1, "test1"));
        ingredients.add(new Ingredient(2, "test2"));

        Mockito.when(ingredientRepository.saveAll(ingredients)).thenReturn(false);

        Assertions.assertThrows(NoChangesMadeException.class, () -> service.saveAll(ingredients));
    }

    @Test
    void saveTest() throws SQLException, NoChangesMadeException {
        Ingredient ingredient = new Ingredient(1, "test1");
        Mockito.when(ingredientRepository.save(ingredient)).thenReturn(ingredient);

        Assertions.assertDoesNotThrow(() -> service.save(ingredient));
    }

    @Test
    void saveThrowsTest() throws SQLException, NoChangesMadeException {
        Ingredient ingredient = new Ingredient(1, "test1");
        Mockito.when(ingredientRepository.save(ingredient)).thenThrow(NoChangesMadeException.class);

        Assertions.assertThrows(NoChangesMadeException.class, () -> service.save(ingredient));
    }
}
