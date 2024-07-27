package org.example.pizzacrud.service;

import org.example.pizzacrud.database.entity.Customer;
import org.example.pizzacrud.database.repository.CustomerRepository;
import org.example.pizzacrud.database.repository.RepositoryBuilder;
import org.example.pizzacrud.database.repository.exception.InternalDatabaseException;
import org.example.pizzacrud.database.repository.exception.NoChangesMadeException;
import org.example.pizzacrud.database.repository.exception.NoObjectException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CustomerService {
    private CustomerRepository customerRepository = RepositoryBuilder.buildCustomerRepository();

    /**
     * Get Customer by id
     *
     * @param id id of a customer
     * @return Found Customer
     */
    public Customer getById(int id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        return customerOptional.orElseThrow(() -> new NoObjectException("Customer with id " + id + " not found"));
    }

    /**
     * Get all Customers
     *
     * @return List of Customers
     * @throws InternalDatabaseException Internal database exception
     */
    public List<Customer> getAll() throws InternalDatabaseException {
        return customerRepository.findAll();
    }

    /**
     * Create or update customer
     *
     * @param customer Customer to be saved
     * @return Saved Customer
     * @throws InternalDatabaseException Internal Database Exception
     */
    public Customer save(Customer customer) throws InternalDatabaseException {
        try {
            return customerRepository.save(customer);
        } catch (SQLException e) {
            throw new InternalDatabaseException(e);
        }
    }

    /**
     * Delete Customer
     *
     * @param id id of a Customer to be deleted
     * @throws InternalDatabaseException Internal Database Exception
     * @throws NoChangesMadeException    Database returned 0 affected rows
     */
    public void delete(int id) throws InternalDatabaseException, NoChangesMadeException {
        try {
            customerRepository.delete(id);
        } catch (NoChangesMadeException e) {
            throw e;
        } catch (SQLException e) {
            throw new InternalDatabaseException(e);
        }
    }

    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
}
