package org.example.pizzacrud.servlet.pizza_servlet.action;

import jakarta.servlet.http.HttpServletResponse;
import org.example.pizzacrud.mapper.PizzaMapper;
import org.example.pizzacrud.database.entity.Pizza;
import org.example.pizzacrud.database.repository.exception.InternalDatabaseException;
import org.example.pizzacrud.service.PizzaService;
import org.example.pizzacrud.service.ServiceBuilder;
import org.example.pizzacrud.servlet.ServletAction;
import org.example.pizzacrud.servlet.pizza_servlet.PizzaDto;

import java.io.IOException;
import java.io.PrintWriter;

public class GetPizzaByIdServletAction implements ServletAction {
    private final PizzaService pizzaService;
    private final int id;
    private final HttpServletResponse response;


    public GetPizzaByIdServletAction(int id, HttpServletResponse httpServletResponse) {
        pizzaService = ServiceBuilder.buildPizzaService();
        this.id = id;
        this.response = httpServletResponse;
    }

    public GetPizzaByIdServletAction(PizzaService pizzaService, int id, HttpServletResponse response) {
        this.pizzaService = pizzaService;
        this.id = id;
        this.response = response;
    }

    @Override
    public void execute() {
        try {
            Pizza pizza = pizzaService.getById(id);

            PizzaDto dto = PizzaMapper.pizzaToPizzaDto(pizza);

            response.setContentType("application/json");
            response.setStatus(200);
            response.setCharacterEncoding("UTF-8");

            PrintWriter out = response.getWriter();
            out.println(dto.toJsonObject());
            out.close();

        } catch (IOException | InternalDatabaseException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
