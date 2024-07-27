package org.example.pizzacrud.service;

public class ServiceBuilder {
    private ServiceBuilder() {
    }

    public static IngredientService buildIngredientService() {
        return new IngredientService();
    }

    public static PizzaService buildPizzaService() {
        return new PizzaService();
    }

    public static CustomerService buildCustomerService() {
        return new CustomerService();
    }

    public static OrderService buildOrderService() {
        return new OrderService();
    }
}
