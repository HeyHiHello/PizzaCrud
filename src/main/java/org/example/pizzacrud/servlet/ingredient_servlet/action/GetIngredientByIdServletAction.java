package org.example.pizzacrud.servlet.ingredient_servlet.action;

import jakarta.servlet.http.HttpServletResponse;
import org.example.pizzacrud.mapper.IngredientMapper;
import org.example.pizzacrud.database.entity.Ingredient;
import org.example.pizzacrud.database.repository.exception.InternalDatabaseException;
import org.example.pizzacrud.service.IngredientService;
import org.example.pizzacrud.service.ServiceBuilder;
import org.example.pizzacrud.servlet.ServletAction;
import org.example.pizzacrud.servlet.ingredient_servlet.IngredientDto;

import java.io.IOException;
import java.io.PrintWriter;

public class GetIngredientByIdServletAction implements ServletAction {
    private final IngredientService ingredientService;
    private final int ingredientId;
    private final HttpServletResponse httpServletResponse;

    public GetIngredientByIdServletAction(int ingredientId, HttpServletResponse httpServletResponse) {
        ingredientService = ServiceBuilder.buildIngredientService();
        this.ingredientId = ingredientId;
        this.httpServletResponse = httpServletResponse;
    }

    public GetIngredientByIdServletAction(IngredientService ingredientService, int ingredientId, HttpServletResponse httpServletResponse) {
        this.ingredientService = ingredientService;
        this.ingredientId = ingredientId;
        this.httpServletResponse = httpServletResponse;
    }

    @Override
    public void execute() {
        try {
            Ingredient ingredient = ingredientService.getById(ingredientId);

            IngredientDto dto = IngredientMapper.ingredientToIngredientDto(ingredient);

            httpServletResponse.setContentType("application/json");
            httpServletResponse.setStatus(200);
            httpServletResponse.setCharacterEncoding("UTF-8");

            PrintWriter out = httpServletResponse.getWriter();
            out.println(dto.toJsonObject());
            out.close();

        } catch (InternalDatabaseException | IOException e) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }
}
