package org.example.pizzacrud.servlet.order_servlet.action;

import jakarta.servlet.http.HttpServletResponse;
import org.example.pizzacrud.database.repository.exception.InternalDatabaseException;
import org.example.pizzacrud.service.OrderService;
import org.example.pizzacrud.service.ServiceBuilder;
import org.example.pizzacrud.servlet.ServletAction;

public class DeleteOrderServletAction implements ServletAction {
    private final OrderService orderService;
    private final int id;
    private final HttpServletResponse response;

    public DeleteOrderServletAction(OrderService orderService, int id, HttpServletResponse response) {
        this.orderService = orderService;
        this.id = id;
        this.response = response;
    }

    public DeleteOrderServletAction(int id, HttpServletResponse response) {
        this.orderService = ServiceBuilder.buildOrderService();
        this.id = id;
        this.response = response;
    }


    @Override
    public void execute() {
        try {
            orderService.delete(id);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (InternalDatabaseException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
