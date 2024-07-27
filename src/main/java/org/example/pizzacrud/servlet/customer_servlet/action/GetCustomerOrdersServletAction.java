package org.example.pizzacrud.servlet.customer_servlet.action;

import jakarta.servlet.http.HttpServletResponse;
import org.example.pizzacrud.mapper.OrderMapper;
import org.example.pizzacrud.database.entity.Customer;
import org.example.pizzacrud.database.entity.Order;
import org.example.pizzacrud.database.repository.exception.NoObjectException;
import org.example.pizzacrud.service.CustomerService;
import org.example.pizzacrud.service.ServiceBuilder;
import org.example.pizzacrud.servlet.ServletAction;
import org.example.pizzacrud.servlet.order_servlet.OrderDto;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.io.PrintWriter;

public class GetCustomerOrdersServletAction implements ServletAction {
    private final CustomerService customerService;
    private final int id;
    private final HttpServletResponse response;

    public GetCustomerOrdersServletAction(int id, HttpServletResponse response) {
        customerService = ServiceBuilder.buildCustomerService();
        this.id = id;
        this.response = response;
    }

    public GetCustomerOrdersServletAction(CustomerService customerService, int id, HttpServletResponse response) {
        this.customerService = customerService;
        this.id = id;
        this.response = response;
    }

    @Override
    public void execute() {
        try {
            Customer customer = customerService.getById(id);

            JSONArray orderJsonArray = new JSONArray();

            for (Order order : customer.getOrders()) {
                OrderDto dto = OrderMapper.orderToOrderDto(order);
                orderJsonArray.add(dto.toJsonObject());
            }

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = response.getWriter();
            out.println(orderJsonArray.toJSONString());
            out.close();
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (NoObjectException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
