package org.example.pizzacrud.servlet.order_servlet.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.pizzacrud.mapper.OrderMapper;
import org.example.pizzacrud.database.entity.Order;
import org.example.pizzacrud.database.repository.exception.InternalDatabaseException;
import org.example.pizzacrud.service.OrderService;
import org.example.pizzacrud.service.ServiceBuilder;
import org.example.pizzacrud.servlet.ServletAction;
import org.example.pizzacrud.servlet.order_servlet.OrderDto;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

public class PostOrderServletAction implements ServletAction {
    private final OrderService orderService;
    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public PostOrderServletAction(HttpServletRequest request, HttpServletResponse response) {
        orderService = ServiceBuilder.buildOrderService();
        this.request = request;
        this.response = response;
    }

    public PostOrderServletAction(OrderService orderService, HttpServletRequest request, HttpServletResponse response) {
        this.orderService = orderService;
        this.request = request;
        this.response = response;
    }

    @Override
    public void execute() {
        try {
            String orderJsonStr = getBodyString();
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(orderJsonStr);

            OrderDto dto = new OrderDto(0, (List<Long>) jsonObject.get("pizzasId"), (int) (long) jsonObject.get("customerId"));

            Order order = OrderMapper.orderDtoToOrder(dto);
            orderService.save(order);

            OrderDto outDto = OrderMapper.orderToOrderDto(order);

            response.setStatus(HttpServletResponse.SC_CREATED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            writer.println(outDto.toJsonObject());
            writer.close();
        } catch (IOException | InternalDatabaseException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (ParseException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private String getBodyString() throws IOException {
        return request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
    }
}
