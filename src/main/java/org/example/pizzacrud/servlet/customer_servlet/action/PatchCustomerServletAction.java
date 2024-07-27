package org.example.pizzacrud.servlet.customer_servlet.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.pizzacrud.mapper.AddressMapper;
import org.example.pizzacrud.mapper.CustomerMapper;
import org.example.pizzacrud.database.entity.Address;
import org.example.pizzacrud.database.entity.Customer;
import org.example.pizzacrud.database.repository.exception.InternalDatabaseException;
import org.example.pizzacrud.service.CustomerService;
import org.example.pizzacrud.service.ServiceBuilder;
import org.example.pizzacrud.servlet.ServletAction;
import org.example.pizzacrud.servlet.customer_servlet.AddressDto;
import org.example.pizzacrud.servlet.customer_servlet.CustomerDto;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.stream.Collectors;

public class PatchCustomerServletAction implements ServletAction {
    private final CustomerService customerService;
    private final int id;
    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public PatchCustomerServletAction(int id, HttpServletRequest request, HttpServletResponse response) {
        customerService = ServiceBuilder.buildCustomerService();
        this.id = id;
        this.request = request;
        this.response = response;
    }

    public PatchCustomerServletAction(CustomerService customerService, int id, HttpServletRequest request, HttpServletResponse response) {
        this.customerService = customerService;
        this.id = id;
        this.request = request;
        this.response = response;
    }

    @Override
    public void execute() {
        try {
            String customerJson = getBodyString();
            JSONObject object = (JSONObject) new JSONParser().parse(customerJson);

            String firstName = (String) object.get("firstName");
            String lastName = (String) object.get("lastName");

            JSONObject addressJsonObject = (JSONObject) object.get("address");
            AddressDto addressDto = new AddressDto(id, (String) addressJsonObject.get("city"), (String) addressJsonObject.get("street"), (String) addressJsonObject.get("building"));
            Address address = AddressMapper.addressDtoToAddress(addressDto);
            CustomerDto dto = new CustomerDto(id, firstName, lastName, address);

            Customer customer = CustomerMapper.customerDtoToCustomer(dto);

            customerService.save(customer);

            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            CustomerDto outDto = CustomerMapper.customerToCustomerDto(customer);
            response.getWriter().print(outDto.toJsonObject().toJSONString());
            response.getWriter().close();

        } catch (ParseException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (IOException | InternalDatabaseException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }


    }

    private String getBodyString() throws IOException {
        return request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
    }
}
