package org.example.pizzacrud.mapper;

import org.example.pizzacrud.database.entity.Customer;
import org.example.pizzacrud.database.entity.Order;
import org.example.pizzacrud.database.entity.Pizza;
import org.example.pizzacrud.database.repository.exception.InternalDatabaseException;
import org.example.pizzacrud.service.CustomerService;
import org.example.pizzacrud.service.PizzaService;
import org.example.pizzacrud.service.ServiceBuilder;
import org.example.pizzacrud.servlet.order_servlet.OrderDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.List;

class OrderMapperTest {

    @Test
    void toDtoTest() {
        Order order = Mockito.mock(Order.class);
        Mockito.when(order.getId()).thenReturn(1);
        Mockito.when(order.getCustomer()).thenReturn(new Customer());
        Mockito.when(order.getPizzas()).thenReturn(List.of(new Pizza(), new Pizza()));
        OrderDto orderDto = OrderMapper.orderToOrderDto(order);

        Assertions.assertNotNull(orderDto);
    }

    @Test
    void toEntityTest() throws InternalDatabaseException {
        PizzaService pizzaService = Mockito.mock(PizzaService.class);
        CustomerService customerService = Mockito.mock(CustomerService.class);
        Mockito.when(pizzaService.getById(Mockito.anyInt())).thenReturn(new Pizza());
        Mockito.when(customerService.getById(Mockito.anyInt())).thenReturn(new Customer());

        MockedStatic<ServiceBuilder> serviceBuilderMockedStatic = Mockito.mockStatic(ServiceBuilder.class);
        serviceBuilderMockedStatic.when(ServiceBuilder::buildPizzaService).thenReturn(pizzaService);
        serviceBuilderMockedStatic.when(ServiceBuilder::buildCustomerService).thenReturn(customerService);

        OrderDto dto = new OrderDto(1, List.of(1L, 2L), 1);
        Order ingredient = OrderMapper.orderDtoToOrder(dto);

        Assertions.assertNotNull(ingredient);
        Assertions.assertEquals(dto.id(), ingredient.getId());

        serviceBuilderMockedStatic.close();
    }
}
