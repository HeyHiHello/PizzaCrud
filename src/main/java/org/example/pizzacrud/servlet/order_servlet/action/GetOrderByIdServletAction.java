package org.example.pizzacrud.servlet.order_servlet.action;

import jakarta.servlet.http.HttpServletResponse;
import org.example.pizzacrud.mapper.OrderMapper;
import org.example.pizzacrud.database.entity.Order;
import org.example.pizzacrud.database.repository.exception.NoObjectException;
import org.example.pizzacrud.service.OrderService;
import org.example.pizzacrud.service.ServiceBuilder;
import org.example.pizzacrud.servlet.ServletAction;
import org.example.pizzacrud.servlet.order_servlet.OrderDto;

import java.io.IOException;

public class GetOrderByIdServletAction implements ServletAction {
    private final OrderService orderService;
    private final int id;
    private final HttpServletResponse response;

    public GetOrderByIdServletAction(int id, HttpServletResponse response) {
        orderService = ServiceBuilder.buildOrderService();
        this.id = id;
        this.response = response;
    }

    public GetOrderByIdServletAction(OrderService orderService, int id, HttpServletResponse response) {
        this.orderService = orderService;
        this.id = id;
        this.response = response;
    }

    @Override
    public void execute() {
        try {
            Order order = orderService.getById(id);
            OrderDto dto = OrderMapper.orderToOrderDto(order);

            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            response.getWriter().println(dto.toJsonObject().toJSONString());
        } catch (NoObjectException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
