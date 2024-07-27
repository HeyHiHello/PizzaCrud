package org.example.pizzacrud.mapper;

import org.example.pizzacrud.database.entity.Pizza;
import org.example.pizzacrud.servlet.pizza_servlet.PizzaDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PizzaMapperTest {
    @Test
    void toDtoTest() {
        Pizza pizza = new Pizza(1, "test", 123.4);
        PizzaDto dto = PizzaMapper.pizzaToPizzaDto(pizza);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(pizza.getId(), dto.id());
        Assertions.assertEquals(pizza.getName(), dto.name());
        Assertions.assertEquals(pizza.getPrice(), dto.price());
    }

    @Test
    void toEntityTest() {
        PizzaDto dto = new PizzaDto(1, "test", 123.4);
        Pizza pizza = PizzaMapper.pizzaDtoToPizza(dto);

        Assertions.assertNotNull(pizza);
        Assertions.assertEquals(dto.id(), pizza.getId());
        Assertions.assertEquals(dto.name(), pizza.getName());
        Assertions.assertEquals(dto.price(), pizza.getPrice());
    }
}
