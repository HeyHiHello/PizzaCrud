package org.example.pizzacrud.servlet.customer_servlet.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.pizzacrud.mapper.AddressMapper;
import org.example.pizzacrud.mapper.CustomerMapper;
import org.example.pizzacrud.database.entity.Address;
import org.example.pizzacrud.database.entity.Customer;
import org.example.pizzacrud.database.repository.exception.InternalDatabaseException;
import org.example.pizzacrud.service.CustomerService;
import org.example.pizzacrud.servlet.ServletAction;
import org.example.pizzacrud.servlet.customer_servlet.AddressDto;
import org.example.pizzacrud.servlet.customer_servlet.CustomerDto;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.stream.Collectors;

public class PostCustomerServletAction implements ServletAction {
    private final CustomerService customerService;
    private final HttpServletResponse response;
    private final HttpServletRequest request;

    public PostCustomerServletAction(HttpServletRequest request, HttpServletResponse response) {
        customerService = new CustomerService();
        this.response = response;
        this.request = request;
    }

    public PostCustomerServletAction(CustomerService customerService, HttpServletResponse response, HttpServletRequest request) {
        this.customerService = customerService;
        this.response = response;
        this.request = request;
    }

    @Override
    public void execute() {
        try {
            String customerJsonStr = getBodyString();

            JSONObject object = (JSONObject) new JSONParser().parse(customerJsonStr);

            String firstName = (String) object.get("firstName");
            String lastName = (String) object.get("lastName");

            JSONObject addressJsonObject = (JSONObject) object.get("address");
            AddressDto addressDto = new AddressDto(0, (String) addressJsonObject.get("city"), (String) addressJsonObject.get("street"), (String) addressJsonObject.get("building"));
            Address address = AddressMapper.addressDtoToAddress(addressDto);
            CustomerDto customerDto = new CustomerDto(0, firstName, lastName, address);
            Customer customer = CustomerMapper.customerDtoToCustomer(customerDto);

            customerService.save(customer);

            CustomerDto outDto = CustomerMapper.customerToCustomerDto(customer);

            response.setStatus(HttpServletResponse.SC_CREATED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(outDto.toJsonObject().toJSONString());
            response.getWriter().close();
        } catch (IOException | InternalDatabaseException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (ParseException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

    }

    private String getBodyString() throws IOException {
        return request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
    }
}
