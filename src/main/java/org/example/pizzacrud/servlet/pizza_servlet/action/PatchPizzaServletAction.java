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

public class PatchPizzaServletAction implements ServletAction {
    private final PizzaService service;
    private final int id;
    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public PatchPizzaServletAction(int id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        service = ServiceBuilder.buildPizzaService();
        this.id = id;
        this.request = httpServletRequest;
        this.response = httpServletResponse;
    }

    public PatchPizzaServletAction(PizzaService service, int id, HttpServletRequest request, HttpServletResponse response) {
        this.service = service;
        this.id = id;
        this.request = request;
        this.response = response;
    }

    @Override
    public void execute() {
        try {
            String jsonRequestBody = getBodyString();
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(jsonRequestBody);

            PizzaDto dto = new PizzaDto(id,
                    (String) jsonObject.get("name"),
                    (Double) jsonObject.get("price"));

            Pizza pizza = PizzaMapper.pizzaDtoToPizza(dto);

            service.save(pizza);

            sendUpdatedObject(PizzaMapper.pizzaToPizzaDto(pizza));

        } catch (IOException | InternalDatabaseException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (ParseException e) {
            response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        } catch (NoChangesMadeException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private String getBodyString() throws IOException {
        return request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
    }

    private void sendUpdatedObject(Dto dto) throws IOException {
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.println(dto.toJsonObject().toJSONString());
        out.close();
    }
}
