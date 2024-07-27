package org.example.pizzacrud.servlet.customer_servlet.action;

import jakarta.servlet.http.HttpServletResponse;
import org.example.pizzacrud.mapper.AddressMapper;
import org.example.pizzacrud.database.entity.Customer;
import org.example.pizzacrud.database.repository.exception.NoObjectException;
import org.example.pizzacrud.service.CustomerService;
import org.example.pizzacrud.service.ServiceBuilder;
import org.example.pizzacrud.servlet.ServletAction;
import org.example.pizzacrud.servlet.customer_servlet.AddressDto;

import java.io.IOException;
import java.io.PrintWriter;

public class GetCustomerAddressServletAction implements ServletAction {
    private final int id;
    private final HttpServletResponse response;
    private final CustomerService customerService;

    public GetCustomerAddressServletAction(int id, HttpServletResponse response) {
        this.id = id;
        this.response = response;
        customerService = ServiceBuilder.buildCustomerService();
    }

    public GetCustomerAddressServletAction(CustomerService customerService, int id, HttpServletResponse response) {
        this.id = id;
        this.response = response;
        this.customerService = customerService;
    }

    @Override
    public void execute() {
        try {
            Customer customer = customerService.getById(id);
            AddressDto dto = AddressMapper.addressToAddressDto(customer.getAddress());
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = response.getWriter();
            out.println(dto.toJsonObject().toJSONString());
            out.close();
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (NoObjectException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
