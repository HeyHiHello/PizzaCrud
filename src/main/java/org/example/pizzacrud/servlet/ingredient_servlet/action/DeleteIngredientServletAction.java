package org.example.pizzacrud.servlet.ingredient_servlet.action;

import jakarta.servlet.http.HttpServletResponse;
import org.example.pizzacrud.database.repository.exception.InternalDatabaseException;
import org.example.pizzacrud.database.repository.exception.NoChangesMadeException;
import org.example.pizzacrud.service.IngredientService;
import org.example.pizzacrud.service.ServiceBuilder;
import org.example.pizzacrud.servlet.ServletAction;

public class DeleteIngredientServletAction implements ServletAction {
    private final IngredientService ingredientService;
    private final int id;
    private final HttpServletResponse response;

    public DeleteIngredientServletAction(int id, HttpServletResponse response) {
        this.id = id;
        this.response = response;
        ingredientService = ServiceBuilder.buildIngredientService();
    }

    public DeleteIngredientServletAction(IngredientService ingredientService, int id, HttpServletResponse response) {
        this.ingredientService = ingredientService;
        this.id = id;
        this.response = response;
    }

    @Override
    public void execute() {
        try {
            ingredientService.delete(id);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (InternalDatabaseException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (NoChangesMadeException e) {
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
        }
    }
}
