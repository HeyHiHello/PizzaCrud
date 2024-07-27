package org.example.pizzacrud.database.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class PizzaTest {
    private Pizza pizza;
    private List<Ingredient> ingredients;

    @BeforeEach
    void setUp() {
        pizza = new Pizza(1, "test1", 123);

        ingredients = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Ingredient ingredient = new Ingredient(i + 1, "test" + (i + 1));
            ingredients.add(ingredient);
        }
        pizza.setIngredients(ingredients);
    }

    @Test
    void getId() {
        Assertions.assertEquals(1, pizza.getId());
    }

    @Test
    void setId() {
        pizza.setId(2);
        Assertions.assertEquals(2, pizza.getId());
    }

    @Test
    void getName() {
        Assertions.assertEquals("test1", pizza.getName());
    }

    @Test
    void setName() {
        pizza.setName("test2");
        Assertions.assertEquals("test2", pizza.getName());
    }

    @Test
    void getIngredients() {
        Assertions.assertEquals(ingredients, pizza.getIngredients());
    }

    @Test
    void setIngredients() {
        List<Ingredient> ingredients1 = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Ingredient ingredient = new Ingredient(i + 1, "test1" + (i + 1));
            ingredients1.add(ingredient);
        }
        pizza.setIngredients(ingredients1);
        Assertions.assertEquals(ingredients1, pizza.getIngredients());
    }

    @Test
    void getPriceTest() {
        Assertions.assertEquals(123, pizza.getPrice());
    }

    @Test
    void setPriceTest() {
        pizza.setPrice(321);
        Assertions.assertEquals(321, pizza.getPrice());
    }

    @Test
    void findIngredientByIdTest() {
        Optional<Ingredient> ingredientOpt = pizza.findIngredientById(1);

        Assertions.assertTrue(ingredientOpt.isPresent());
        Assertions.assertEquals(ingredients.get(0), ingredientOpt.get());
    }

    @Test
    void findIngredientByIdFailTest() {
        Optional<Ingredient> ingredientOpt = pizza.findIngredientById(1000);

        Assertions.assertTrue(ingredientOpt.isEmpty());
    }

    @Test
    void addIngredientTest() {
        pizza.addIngredient(new Ingredient(1000, "test1000"));
        Optional<Ingredient> ingredientOpt = pizza.findIngredientById(1000);
        Assertions.assertTrue(ingredientOpt.isPresent());
        Assertions.assertEquals("test1000", ingredientOpt.get().getName());
    }

    @Test
    void removeIngredientTest() {
        pizza.removeIngredient(ingredients.get(0));
        Optional<Ingredient> ingredientOpt = pizza.findIngredientById(1);
        Assertions.assertTrue(ingredientOpt.isEmpty());
    }

    @Test
    void equalsTest() {

        List<Ingredient> ingredients1 = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Ingredient ingredient = new Ingredient(i + 1, "test" + (i + 1));
            ingredients1.add(ingredient);
        }
        Pizza pizza1 = new Pizza(1, "test1", 123, ingredients1);

        Assertions.assertTrue(pizza.equals(pizza1));
    }

    @Test
    void equalsSameTest() {
        Assertions.assertTrue(pizza.equals(pizza));
    }

    @Test
    void equalsNullTest() {
        Assertions.assertFalse(pizza.equals(null));
    }

    @Test
    void equalsDifferentClassTest() {
        Assertions.assertFalse(pizza.equals("Test"));
    }

    @Test
    void equalsDifferentTest() {
        Pizza pizza2 = new Pizza(2, "test1", 123, ingredients);
        Assertions.assertFalse(pizza.equals(pizza2));
        pizza2 = new Pizza(1, "test2", 123, ingredients);
        Assertions.assertFalse(pizza.equals(pizza2));
        pizza2 = new Pizza(1, "test1", 321, ingredients);
        Assertions.assertFalse(pizza.equals(pizza2));
        pizza2 = new Pizza(1, "test1", 123, null);
        Assertions.assertFalse(pizza.equals(pizza2));
    }

    @Test
    void emptyConstructorTest() {
        Pizza pizza1 = new Pizza();
        Assertions.assertEquals(0, pizza1.getId());
        Assertions.assertNull(pizza1.getName());
        Assertions.assertEquals(0, pizza1.getPrice());
        Assertions.assertEquals(0, pizza1.getIngredients().size());
    }
}
