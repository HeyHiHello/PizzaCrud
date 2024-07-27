package org.example.pizzacrud.database.entity;

import org.example.pizzacrud.database.entity.exception.LazyInitializationException;
import org.example.pizzacrud.database.repository.CustomerRepository;
import org.example.pizzacrud.database.repository.RepositoryBuilder;

import java.util.List;

public class Order implements Entity {
    private int id;
    private Customer customer;
    private List<Pizza> pizzas;

    public Order(int id, Customer customer, List<Pizza> pizzas) {
        this.id = id;
        this.customer = customer;
        this.pizzas = pizzas;
    }

    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        if (customer != null && customer.getId() > 0 && customer.getFirstname() == null) {
            CustomerRepository customerRepository = RepositoryBuilder.buildCustomerRepository();

            customer = customerRepository.findById(customer.getId()).orElseThrow(LazyInitializationException::new);
        } else if (customer != null && customer.getId() == 0) {
            customer = null;
        }
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }
}
