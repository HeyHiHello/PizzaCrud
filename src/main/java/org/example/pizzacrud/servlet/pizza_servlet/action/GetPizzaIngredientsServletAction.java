package org.example.pizzacrud.servlet.pizza_servlet.action;

import jakarta.servlet.http.HttpServletResponse;
import org.example.pizzacrud.mapper.IngredientMapper;
import org.example.pizzacrud.database.entity.Ingredient;
import org.example.pizzacrud.database.entity.Pizza;
import org.example.pizzacrud.database.repository.exception.InternalDatabaseException;
import org.example.pizzacrud.service.PizzaService;
import org.example.pizzacrud.service.ServiceBuilder;
import org.example.pizzacrud.servlet.ServletAction;
import org.example.pizzacrud.servlet.ingredient_servlet.IngredientDto;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class GetPizzaIngredientsServletAction implements ServletAction {
    private final PizzaService pizzaService;
    private final int id;
    private final HttpServletResponse response;

    public GetPizzaIngredientsServletAction(int id, HttpServletResponse httpServletResponse) {
        pizzaService = ServiceBuilder.buildPizzaService();
        this.id = id;
        this.response = httpServletResponse;
    }

    public GetPizzaIngredientsServletAction(PizzaService pizzaService, int id, HttpServletResponse response) {
        this.pizzaService = pizzaService;
        this.id = id;
        this.response = response;
    }

    @Override
    public void execute() {
        try {
            Pizza pizza = pizzaService.getById(id);
            List<Ingredient> ingredients = pizza.getIngredients();

            JSONArray jsonArray = new JSONArray();
            for (Ingredient ingredient : ingredients) {
                IngredientDto dto = IngredientMapper.ingredientToIngredientDto(ingredient);
                jsonArray.add(dto.toJsonObject());
            }

            sendSuccess(jsonArray);
        } catch (InternalDatabaseException | IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void sendSuccess(JSONArray jsonArray) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println(jsonArray.toJSONString());
    }
}
