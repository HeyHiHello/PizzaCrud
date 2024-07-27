package org.example.pizzacrud.database.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IngredientTest {
    private Ingredient ingredient;

    @BeforeEach
    void setUp() {
        ingredient = new Ingredient(1, "test");
    }

    @Test
    void getIdTest() {
        Assertions.assertEquals(1, ingredient.getId());
    }

    @Test
    void getNameTest() {
        Assertions.assertEquals("test", ingredient.getName());
    }

    @Test
    void setNameTest() {
        ingredient.setName("test2");
        Assertions.assertEquals("test2", ingredient.getName());
    }

    @Test
    void setIdTest() {
        ingredient.setId(2);
        Assertions.assertEquals(2, ingredient.getId());
    }

    @Test
    void defaultConstructor() {
        Ingredient ingredient1 = new Ingredient();
        Assertions.assertNotNull(ingredient1);
        Assertions.assertEquals(0, ingredient1.getId());
        Assertions.assertNull(ingredient1.getName());
    }

    @Test
    void nameConstructor() {
        Ingredient ingredient1 = new Ingredient("test");
        Assertions.assertNotNull(ingredient1);
        Assertions.assertEquals(0, ingredient1.getId());
        Assertions.assertEquals("test", ingredient1.getName());
    }

    @Test
    void equalsTest() {
        Ingredient ingredient1 = new Ingredient(1, "test");

        Assertions.assertEquals(ingredient, ingredient1);
    }

    @Test
    void equalsNullTest() {
        Assertions.assertFalse(ingredient.equals(null));
    }

    @Test
    void equalsDifferentClassTest() {
        Assertions.assertFalse(ingredient.equals("test"));
    }

    @Test
    void equalsSameTest() {
        Assertions.assertEquals(ingredient, ingredient);
    }

    @Test
    void equalsDifferentTest() {
        Ingredient ingredient1 = new Ingredient(2, "test");
        Assertions.assertNotEquals(ingredient, ingredient1);
        ingredient1 = new Ingredient(1, "test1");
        Assertions.assertNotEquals(ingredient, ingredient1);
    }
}
