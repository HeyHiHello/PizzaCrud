package org.example.pizzacrud.servlet.ingredient_servlet;

import org.example.pizzacrud.servlet.Dto;
import org.json.simple.JSONObject;

public record IngredientDto(int id, String name) implements Dto {

    @Override
    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("name", name);
        return jsonObject;
    }
}
