package org.example.pizzacrud.mapper;

import org.example.pizzacrud.database.entity.Customer;
import org.example.pizzacrud.database.entity.Order;
import org.example.pizzacrud.database.entity.Pizza;
import org.example.pizzacrud.database.repository.exception.InternalDatabaseException;
import org.example.pizzacrud.service.CustomerService;
import org.example.pizzacrud.service.PizzaService;
import org.example.pizzacrud.service.ServiceBuilder;
import org.example.pizzacrud.servlet.order_servlet.OrderDto;

import java.util.ArrayList;
import java.util.List;

public class OrderMapper {
    private OrderMapper() {
    }

    public static OrderDto orderToOrderDto(Order order) {
        List<Long> pizzasIds = new ArrayList<>();
        for (Pizza pizza : order.getPizzas()) {
            pizzasIds.add((long) pizza.getId());
        }
        return new OrderDto(order.getId(), pizzasIds, order.getCustomer().getId());

    }

    public static Order orderDtoToOrder(OrderDto orderDto) throws InternalDatabaseException {
        PizzaService pizzaService = ServiceBuilder.buildPizzaService();
        CustomerService customerService = ServiceBuilder.buildCustomerService();

        Order order = new Order();
        order.setId(orderDto.id());

        List<Pizza> pizzas = new ArrayList<>();
        for (int i = 0; i < orderDto.pizzasId().size(); i++) {
            long id = orderDto.pizzasId().get(i);
            pizzas.add(pizzaService.getById((int) id));
        }
        order.setPizzas(pizzas);

        Customer customer = customerService.getById(orderDto.customerId());
        order.setCustomer(customer);
        return order;
    }
}
