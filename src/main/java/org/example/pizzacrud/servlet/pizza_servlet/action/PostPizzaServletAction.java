package org.example.pizzacrud.servlet.pizza_servlet.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.pizzacrud.mapper.PizzaMapper;
import org.example.pizzacrud.database.entity.Pizza;
import org.example.pizzacrud.database.repository.exception.InternalDatabaseException;
import org.example.pizzacrud.database.repository.exception.NoChangesMadeException;
import org.example.pizzacrud.service.PizzaService;
import org.example.pizzacrud.service.ServiceBuilder;
import org.example.pizzacrud.servlet.Dto;
import org.example.pizzacrud.servlet.ServletAction;
import org.example.pizzacrud.servlet.pizza_servlet.PizzaDto;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

public class PostPizzaServletAction implements ServletAction {
    private final PizzaService pizzaService;
    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public PostPizzaServletAction(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        this.pizzaService = ServiceBuilder.buildPizzaService();
        this.request = httpServletRequest;
        this.response = httpServletResponse;
    }

    public PostPizzaServletAction(PizzaService pizzaService, HttpServletRequest request, HttpServletResponse response) {
        this.pizzaService = pizzaService;
        this.request = request;
        this.response = response;
    }

    @Override
    public void execute() {
        try {
            String jsonRequestBody = getBodyString();
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(jsonRequestBody);

            PizzaDto dto = new PizzaDto(0,
                    (String) jsonObject.get("name"),
                    (Double) jsonObject.get("price"));

            Pizza pizza = PizzaMapper.pizzaDtoToPizza(dto);
            pizzaService.save(pizza);

            PizzaDto dtoOut = PizzaMapper.pizzaToPizzaDto(pizza);
            sendCreatedObject(dtoOut);
        } catch (NoChangesMadeException e) {
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
        } catch (IOException | InternalDatabaseException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (ParseException e) {
            response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }
    }

    private String getBodyString() throws IOException {
        return request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
    }

    private void sendCreatedObject(Dto dto) throws IOException {
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.println(dto.toJsonObject().toJSONString());
        out.close();
    }
}
