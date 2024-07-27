package org.example.pizzacrud.servlet.ingredient_servlet.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.pizzacrud.mapper.IngredientMapper;
import org.example.pizzacrud.database.entity.Ingredient;
import org.example.pizzacrud.database.repository.exception.InternalDatabaseException;
import org.example.pizzacrud.database.repository.exception.NoChangesMadeException;
import org.example.pizzacrud.service.IngredientService;
import org.example.pizzacrud.service.ServiceBuilder;
import org.example.pizzacrud.servlet.Dto;
import org.example.pizzacrud.servlet.ServletAction;
import org.example.pizzacrud.servlet.ingredient_servlet.IngredientDto;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

public class PatchIngredientServletAction implements ServletAction {
    private final IngredientService ingredientService;
    private final int id;
    private final HttpServletRequest httpServletRequest;
    private final HttpServletResponse httpServletResponse;

    public PatchIngredientServletAction(int id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        this.ingredientService = ServiceBuilder.buildIngredientService();
        this.httpServletRequest = httpServletRequest;
        this.httpServletResponse = httpServletResponse;
        this.id = id;
    }

    public PatchIngredientServletAction(IngredientService ingredientService, int id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        this.ingredientService = ingredientService;
        this.id = id;
        this.httpServletRequest = httpServletRequest;
        this.httpServletResponse = httpServletResponse;
    }

    @Override
    public void execute() {
        try {
            String json = getBodyString();

            JSONObject jsonObject = (JSONObject) new JSONParser().parse(json);

            IngredientDto dto = new IngredientDto(id, (String) jsonObject.get("name"));
            Ingredient ingredient = IngredientMapper.ingredientDtoToIngredient(dto);

            ingredientService.save(ingredient);

            IngredientDto dtoOut = IngredientMapper.ingredientToIngredientDto(ingredient);
            sendUpdatedObject(dtoOut);


        } catch (IOException | InternalDatabaseException e) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (ParseException e) {
            httpServletResponse.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        } catch (NoChangesMadeException e) {
            httpServletResponse.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
        }

    }

    private String getBodyString() throws IOException {
        return httpServletRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
    }

    private void sendUpdatedObject(Dto dto) throws IOException {
        httpServletResponse.setStatus(HttpServletResponse.SC_CREATED);
        httpServletResponse.setContentType("application/json");
        PrintWriter out = httpServletResponse.getWriter();
        out.println(dto.toJsonObject().toJSONString());
        out.close();
    }
}
