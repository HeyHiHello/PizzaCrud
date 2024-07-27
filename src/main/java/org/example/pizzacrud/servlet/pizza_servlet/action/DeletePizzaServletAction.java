package org.example.pizzacrud.servlet.pizza_servlet.action;

import jakarta.servlet.http.HttpServletResponse;
import org.example.pizzacrud.database.repository.exception.NoChangesMadeException;
import org.example.pizzacrud.service.PizzaService;
import org.example.pizzacrud.service.ServiceBuilder;
import org.example.pizzacrud.servlet.ServletAction;

import java.sql.SQLException;

public class DeletePizzaServletAction implements ServletAction {
    private final PizzaService service;
    private final int id;
    private final HttpServletResponse response;

    public DeletePizzaServletAction(int id, HttpServletResponse httpServletResponse) {
        service = ServiceBuilder.buildPizzaService();
        this.id = id;
        this.response = httpServletResponse;
    }

    public DeletePizzaServletAction(PizzaService service, int id, HttpServletResponse response) {
        this.service = service;
        this.id = id;
        this.response = response;
    }

    @Override
    public void execute() {
        try {
            service.delete(id);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (NoChangesMadeException e) {
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
