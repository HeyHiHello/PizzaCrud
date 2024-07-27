package org.example.pizzacrud.servlet.pizza_servlet.action;

import jakarta.servlet.http.HttpServletResponse;
import org.example.pizzacrud.mapper.PizzaMapper;
import org.example.pizzacrud.database.entity.Pizza;
import org.example.pizzacrud.database.repository.exception.InternalDatabaseException;
import org.example.pizzacrud.service.PizzaService;
import org.example.pizzacrud.service.ServiceBuilder;
import org.example.pizzacrud.servlet.ServletAction;
import org.example.pizzacrud.servlet.pizza_servlet.PizzaDto;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class GetAllPizzasServletAction implements ServletAction {
    private final PizzaService pizzaService;
    private final HttpServletResponse response;

    public GetAllPizzasServletAction(HttpServletResponse httpServletResponse) {
        pizzaService = ServiceBuilder.buildPizzaService();
        response = httpServletResponse;
    }

    public GetAllPizzasServletAction(PizzaService pizzaService, HttpServletResponse response) {
        this.pizzaService = pizzaService;
        this.response = response;
    }

    @Override
    public void execute() {
        try {
            List<Pizza> pizzas = pizzaService.getAll();

            JSONArray jsonArray = new JSONArray();
            for (Pizza pizza : pizzas) {
                PizzaDto dto = PizzaMapper.pizzaToPizzaDto(pizza);
                jsonArray.add(dto.toJsonObject());
            }

            sendSuccess(jsonArray);

        } catch (InternalDatabaseException | IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void sendSuccess(JSONArray jsonArray) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = response.getWriter();
        out.write(jsonArray.toJSONString());
        out.close();
    }
}
