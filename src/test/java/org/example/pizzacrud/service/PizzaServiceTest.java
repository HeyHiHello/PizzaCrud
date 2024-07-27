package org.example.pizzacrud.service;

import org.example.pizzacrud.database.entity.Ingredient;
import org.example.pizzacrud.database.entity.Pizza;
import org.example.pizzacrud.database.repository.PizzaRepository;
import org.example.pizzacrud.database.repository.exception.InternalDatabaseException;
import org.example.pizzacrud.database.repository.exception.NoChangesMadeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class PizzaServiceTest {
    private static PizzaRepository pizzaRepository;

    private static PizzaService pizzaService;

    @BeforeEach
    void setMock() {
        pizzaRepository = Mockito.mock(PizzaRepository.class);

        pizzaService = ServiceBuilder.buildPizzaService();
        pizzaService.setPizzaRepository(pizzaRepository);
    }

    @Test
    void getByIdTest() throws SQLException {
        Pizza pizza = new Pizza(1, "test", 123);
        pizza.addIngredient(new Ingredient("testIngredient1"));
        pizza.addIngredient(new Ingredient("testIngredient2"));
        pizza.addIngredient(new Ingredient("testIngredient3"));
        Mockito.when(pizzaRepository.findById(1)).thenReturn(Optional.of(pizza));

        Pizza pizza1 = pizzaService.getById(1);

        Assertions.assertEquals(pizza, pizza1);
    }

    @Test
    void getAllTest() throws SQLException {
        List<Pizza> pizzaList = new ArrayList<>();
        pizzaList.add(new Pizza(1, "test", 123));
        pizzaList.add(new Pizza(2, "test2", 123));
        pizzaList.add(new Pizza(3, "test3", 123));

        Mockito.when(pizzaRepository.findAll()).thenReturn(pizzaList);
        List<Pizza> pizzaList1 = pizzaService.getAll();
        Assertions.assertEquals(pizzaList, pizzaList1);

    }

    @Test
    void deleteByIdTest() {
        Assertions.assertDoesNotThrow(() -> pizzaService.delete(1));
    }

    @Test
    void deleteByIdFailTest() throws SQLException {
        Mockito.doThrow(NoChangesMadeException.class).doNothing().when(pizzaRepository).delete(1000);
        Assertions.assertThrows(NoChangesMadeException.class, () -> pizzaService.delete(1000));
    }

    @Test
    void deleteByPizzaTest() {
        Assertions.assertDoesNotThrow(() -> pizzaService.delete(1));
    }

    @Test
    void deleteByPizzaFailtTest() throws SQLException {
        Pizza pizza = new Pizza(1, "test", 123);
        Mockito.doThrow(NoChangesMadeException.class).doNothing().when(pizzaRepository).delete(1);
        Assertions.assertThrows(NoChangesMadeException.class, () -> pizzaService.delete(pizza));
    }


    @Test
    void saveTest() throws SQLException {
        Pizza pizza = new Pizza(1, "test", 123);
        Mockito.when(pizzaRepository.save(pizza)).thenReturn(pizza);

        Assertions.assertDoesNotThrow(() -> pizzaService.save(pizza));
    }

    @Test
    void saveFailTest() throws SQLException {
        Pizza pizza = new Pizza(1, "test", 123);
        Mockito.when(pizzaRepository.save(pizza)).thenThrow(NoChangesMadeException.class);

        Assertions.assertThrows(NoChangesMadeException.class, () -> pizzaService.save(pizza));

    }

    @Test
    void savePizzaIngredientTest() throws InternalDatabaseException {

        IngredientService ingredientService = Mockito.mock(IngredientService.class);

        Mockito.when(ingredientService.getById(1)).thenReturn(new Ingredient(1, "testIngredient1"));
        Mockito.when(ingredientService.getById(2)).thenReturn(new Ingredient(2, "testIngredient2"));

        pizzaService.setIngredientService(ingredientService);

        List<Integer> ingredientIds = List.of(1, 2);

        Pizza pizza = new Pizza(1, "test", 123);
        Assertions.assertDoesNotThrow(() -> pizzaService.setIngredientsByIds(pizza, ingredientIds));


    }
}
