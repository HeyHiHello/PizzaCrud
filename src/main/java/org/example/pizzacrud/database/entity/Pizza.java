package org.example.pizzacrud.database.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Pizza implements Entity {
    private int id;
    private String name;
    private double price;
    private List<Ingredient> ingredients = new ArrayList<>();

    public Pizza(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
        ingredients = new ArrayList<>();
    }

    public Pizza(int id, String name, double price, List<Ingredient> ingredients) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.ingredients = ingredients;
    }

    public Pizza() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Optional<Ingredient> findIngredientById(int id) {
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getId() == id) {
                return Optional.of(ingredient);
            }
        }
        return Optional.empty();
    }

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }

    public void removeIngredient(Ingredient ingredient) {
        this.ingredients.remove(ingredient);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pizza pizza = (Pizza) o;
        return id == pizza.id && Objects.equals(name, pizza.name) && Objects.equals(price, pizza.price) && Objects.equals(ingredients, pizza.ingredients);
    }
}
