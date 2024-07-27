package org.example.pizzacrud.service;

import org.example.pizzacrud.database.entity.Order;
import org.example.pizzacrud.database.repository.OrderRepository;
import org.example.pizzacrud.database.repository.RepositoryBuilder;
import org.example.pizzacrud.database.repository.exception.InternalDatabaseException;
import org.example.pizzacrud.database.repository.exception.NoObjectException;

import java.sql.SQLException;
import java.util.List;

public class OrderService {
    OrderRepository orderRepository = RepositoryBuilder.buildOrderRepository();

    /**
     * Get All Orders
     *
     * @return List of Orders
     * @throws InternalDatabaseException Internal database exception
     */
    public List<Order> getAll() throws InternalDatabaseException {
        try {
            return orderRepository.findAll();
        } catch (SQLException e) {
            throw new InternalDatabaseException(e);
        }
    }

    /**
     * Get Order by id
     *
     * @param id of an Order to be found
     * @return Found Order
     * @throws NoObjectException No Order found for given id
     */
    public Order getById(int id) throws NoObjectException {
        return orderRepository.findById(id).orElseThrow(() -> new NoObjectException("Order with id " + id + " not found"));
    }

    /**
     * Create new order
     *
     * @param order To be created. It id is ignored and will be replaced by database
     * @return Created Order
     * @throws InternalDatabaseException Internal database exception
     */
    public Order save(Order order) throws InternalDatabaseException {
        try {
            return orderRepository.create(order);
        } catch (SQLException e) {
            throw new InternalDatabaseException(e);
        }
    }

    /**
     * Delete Order
     *
     * @param id id of an order to be deleted
     * @throws InternalDatabaseException Internal database exception
     */
    public void delete(int id) throws InternalDatabaseException {
        try {
            orderRepository.delete(id);
        } catch (SQLException e) {
            throw new InternalDatabaseException(e);
        }
    }

    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
}
