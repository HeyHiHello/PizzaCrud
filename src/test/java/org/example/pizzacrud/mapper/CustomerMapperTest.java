package org.example.pizzacrud.mapper;

import org.example.pizzacrud.database.entity.Address;
import org.example.pizzacrud.database.entity.Customer;
import org.example.pizzacrud.servlet.customer_servlet.CustomerDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CustomerMapperTest {
    @Test
    void toDtoTest() {
        Customer customer = new Customer(1, "testName", "testLastName", new Address("test", "test", "test"));
        CustomerDto customerDto = CustomerMapper.customerToCustomerDto(customer);

        Assertions.assertNotNull(customerDto);
        Assertions.assertEquals(customer.getId(), customerDto.id());
        Assertions.assertEquals(customer.getFirstname(), customerDto.firstName());
    }

    @Test
    void toEntityTest() {
        CustomerDto dto = new CustomerDto(1, "testName", "testLastName", new Address("test", "test", "test"));
        Customer customer = CustomerMapper.customerDtoToCustomer(dto);

        Assertions.assertNotNull(customer);
        Assertions.assertEquals(dto.id(), customer.getId());
        Assertions.assertEquals(dto.firstName(), customer.getFirstname());
    }
}
