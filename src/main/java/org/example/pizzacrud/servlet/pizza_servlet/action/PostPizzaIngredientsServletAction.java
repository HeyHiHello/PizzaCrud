package org.example.pizzacrud.servlet.pizza_servlet.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.pizzacrud.database.entity.Pizza;
import org.example.pizzacrud.database.repository.exception.InternalDatabaseException;
import org.example.pizzacrud.service.PizzaService;
import org.example.pizzacrud.service.ServiceBuilder;
import org.example.pizzacrud.servlet.ServletAction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PostPizzaIngredientsServletAction implements ServletAction {
    private final PizzaService service;
    private final int id;
    private final HttpServletRequest request;
    private final HttpServletResponse response;


    public PostPizzaIngredientsServletAction(int id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        this.service = ServiceBuilder.buildPizzaService();
        this.id = id;
        this.request = httpServletRequest;
        this.response = httpServletResponse;
    }

    public PostPizzaIngredientsServletAction(PizzaService service, int id, HttpServletRequest request, HttpServletResponse response) {
        this.service = service;
        this.id = id;
        this.request = request;
        this.response = response;
    }

    @Override
    public void execute() {
        try {
            String jsonRequestBody = getBodyString();

            List<Integer> ingredientsIds = parseIngredientsIds(jsonRequestBody);

            Pizza pizza = service.getById(id);

            service.setIngredientsByIds(pizza, ingredientsIds);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (IOException | InternalDatabaseException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (ParseException e) {
            response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }
    }

    private String getBodyString() throws IOException {
        return request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
    }

    private List<Integer> parseIngredientsIds(String jsonRequestBody) throws ParseException {
        JSONArray jsonArray = (JSONArray) new JSONParser().parse(jsonRequestBody);
        List<Integer> ingredients = new ArrayList<>();
        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) object;
            int ingredientId = ((Long) jsonObject.get("id")).intValue();
            ingredients.add(ingredientId);
        }
        return ingredients;
    }
}
