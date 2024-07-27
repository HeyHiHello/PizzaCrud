package org.example.pizzacrud.servlet.customer_servlet.action;

import jakarta.servlet.http.HttpServletResponse;
import org.example.pizzacrud.database.repository.exception.InternalDatabaseException;
import org.example.pizzacrud.database.repository.exception.NoChangesMadeException;
import org.example.pizzacrud.service.CustomerService;
import org.example.pizzacrud.service.ServiceBuilder;
import org.example.pizzacrud.servlet.ServletAction;

public class DeleteCustomerServletAction implements ServletAction {
    private final int id;
    private final HttpServletResponse response;
    private final CustomerService customerService;

    public DeleteCustomerServletAction(int id, HttpServletResponse response) {
        this.id = id;
        this.response = response;
        customerService = ServiceBuilder.buildCustomerService();
    }

    public DeleteCustomerServletAction(CustomerService customerService, int id, HttpServletResponse response) {
        this.id = id;
        this.response = response;
        this.customerService = customerService;
    }

    @Override
    public void execute() {
        try {
            customerService.delete(id);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (InternalDatabaseException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (NoChangesMadeException e) {
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
        }
    }
}
