package org.example.pizzacrud.servlet.customer_servlet.action;

import jakarta.servlet.http.HttpServletResponse;
import org.example.pizzacrud.mapper.CustomerMapper;
import org.example.pizzacrud.database.entity.Customer;
import org.example.pizzacrud.database.repository.exception.InternalDatabaseException;
import org.example.pizzacrud.service.CustomerService;
import org.example.pizzacrud.service.ServiceBuilder;
import org.example.pizzacrud.servlet.ServletAction;
import org.example.pizzacrud.servlet.customer_servlet.CustomerDto;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class GetAllCustomersServletAction implements ServletAction {
    private final CustomerService customerService;
    private final HttpServletResponse response;

    public GetAllCustomersServletAction(HttpServletResponse response) {
        customerService = ServiceBuilder.buildCustomerService();
        this.response = response;
    }

    public GetAllCustomersServletAction(CustomerService customerService, HttpServletResponse response) {
        this.customerService = customerService;
        this.response = response;
    }

    @Override
    public void execute() {
        try {
            List<Customer> customers = customerService.getAll();
            JSONArray jsonArray = new JSONArray();
            for (Customer customer : customers) {
                CustomerDto dto = CustomerMapper.customerToCustomerDto(customer);
                jsonArray.add(dto.toJsonObject());
            }

            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.println(jsonArray.toJSONString());
            out.close();
        } catch (InternalDatabaseException | IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
