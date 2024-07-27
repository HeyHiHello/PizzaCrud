package org.example.pizzacrud.mapper;

import org.example.pizzacrud.database.entity.Ingredient;
import org.example.pizzacrud.servlet.ingredient_servlet.IngredientDto;

public class IngredientMapper {

    private IngredientMapper() {
    }

    public static IngredientDto ingredientToIngredientDto(Ingredient ingredient) {
        return new IngredientDto(ingredient.getId(), ingredient.getName());
    }

    public static Ingredient ingredientDtoToIngredient(IngredientDto ingredientDto) {
        return new Ingredient(ingredientDto.id(), ingredientDto.name());
    }
}
