package org.example.pizzacrud.mapper;

import org.example.pizzacrud.database.entity.Ingredient;
import org.example.pizzacrud.servlet.ingredient_servlet.IngredientDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class IngredientMapperTest {

    @Test
    void toDtoTest() {
        Ingredient ingredient = new Ingredient(1, "test");
        IngredientDto ingredientDto = IngredientMapper.ingredientToIngredientDto(ingredient);

        Assertions.assertNotNull(ingredientDto);
        Assertions.assertEquals(ingredient.getId(), ingredientDto.id());
        Assertions.assertEquals(ingredient.getName(), ingredientDto.name());
    }

    @Test
    void toEntityTest() {
        IngredientDto dto = new IngredientDto(1, "test");
        Ingredient ingredient = IngredientMapper.ingredientDtoToIngredient(dto);

        Assertions.assertNotNull(ingredient);
        Assertions.assertEquals(dto.id(), ingredient.getId());
        Assertions.assertEquals(dto.name(), ingredient.getName());
    }
}
