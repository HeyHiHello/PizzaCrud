package org.example.pizzacrud.database.entity;

import org.example.pizzacrud.database.entity.exception.LazyInitializationException;
import org.example.pizzacrud.database.repository.OrderRepository;
import org.example.pizzacrud.database.repository.RepositoryBuilder;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class Customer implements Entity {
    private int id;
    private String firstname;
    private String lastname;
    private Address address;
    private List<Order> orders;

    public Customer(int id, String firstname, String lastname, Address address) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
    }

    public Customer(int id, String firstname, String lastname) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Customer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
        this.address.setCustomer(this);
    }

    public List<Order> getOrders() {
        try {
            if (orders == null) {
                OrderRepository orderRepository = RepositoryBuilder.buildOrderRepository();
                orders = orderRepository.findAllByCustomerId(id);
            }
            return orders;
        } catch (SQLException e) {
            throw new LazyInitializationException(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id && Objects.equals(firstname, customer.firstname) && Objects.equals(lastname, customer.lastname);
    }
}
