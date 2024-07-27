package org.example.pizzacrud.mapper;

import org.example.pizzacrud.database.entity.Pizza;
import org.example.pizzacrud.servlet.pizza_servlet.PizzaDto;

public class PizzaMapper {
    private PizzaMapper() {
    }

    public static Pizza pizzaDtoToPizza(PizzaDto pizzaDto) {
        return new Pizza(pizzaDto.id(), pizzaDto.name(), pizzaDto.price());
    }

    public static PizzaDto pizzaToPizzaDto(Pizza pizza) {
        return new PizzaDto(pizza.getId(), pizza.getName(), pizza.getPrice());
    }
}
