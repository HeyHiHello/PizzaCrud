package org.example.pizzacrud.mapper;

import org.example.pizzacrud.database.entity.Customer;
import org.example.pizzacrud.servlet.customer_servlet.CustomerDto;

public class CustomerMapper {

    private CustomerMapper() {
    }

    public static Customer customerDtoToCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setId(customerDto.id());
        customer.setFirstname(customerDto.firstName());
        customer.setLastname(customerDto.lastName());
        customer.setAddress(customerDto.address());
        return customer;
    }

    public static CustomerDto customerToCustomerDto(Customer customer) {
        return new CustomerDto(customer.getId(), customer.getFirstname(), customer.getLastname(), customer.getAddress());
    }
}
