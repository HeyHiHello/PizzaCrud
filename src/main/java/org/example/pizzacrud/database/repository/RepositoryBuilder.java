package org.example.pizzacrud.database.repository;

public class RepositoryBuilder {
    private RepositoryBuilder() {
    }

    public static IngredientRepository buildIngredientRepository() {
        return new IngredientRepository();
    }

    public static PizzaRepository buildPizzaRepository() {
        return new PizzaRepository();
    }

    public static OrderRepository buildOrderRepository() {
        return new OrderRepository();
    }

    public static CustomerRepository buildCustomerRepository() {
        return new CustomerRepository();
    }
}
