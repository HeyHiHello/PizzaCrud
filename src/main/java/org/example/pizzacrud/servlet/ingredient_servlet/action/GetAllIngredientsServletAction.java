package org.example.pizzacrud.servlet.ingredient_servlet.action;

import jakarta.servlet.http.HttpServletResponse;
import org.example.pizzacrud.mapper.IngredientMapper;
import org.example.pizzacrud.database.entity.Ingredient;
import org.example.pizzacrud.database.repository.exception.InternalDatabaseException;
import org.example.pizzacrud.service.IngredientService;
import org.example.pizzacrud.service.ServiceBuilder;
import org.example.pizzacrud.servlet.ServletAction;
import org.example.pizzacrud.servlet.ingredient_servlet.IngredientDto;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class GetAllIngredientsServletAction implements ServletAction {
    private final IngredientService ingredientService;
    private final HttpServletResponse httpServletResponse;

    public GetAllIngredientsServletAction(HttpServletResponse httpServletResponse) {
        ingredientService = ServiceBuilder.buildIngredientService();
        this.httpServletResponse = httpServletResponse;
    }

    public GetAllIngredientsServletAction(IngredientService ingredientService, HttpServletResponse httpServletResponse) {
        this.ingredientService = ingredientService;
        this.httpServletResponse = httpServletResponse;
    }

    @Override
    public void execute() {
        try {
            List<Ingredient> ingredients = ingredientService.getAll();
            httpServletResponse.setContentType("application/json");
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setStatus(200);

            JSONArray jsonArray = new JSONArray();
            for (Ingredient ingredient : ingredients) {
                IngredientDto dto = IngredientMapper.ingredientToIngredientDto(ingredient);
                jsonArray.add(dto.toJsonObject());
            }

            PrintWriter out = httpServletResponse.getWriter();
            out.write(jsonArray.toJSONString());
            out.close();

        } catch (InternalDatabaseException | IOException e) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
