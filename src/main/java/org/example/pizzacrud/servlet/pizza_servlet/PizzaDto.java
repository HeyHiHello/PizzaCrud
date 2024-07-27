package org.example.pizzacrud.servlet.pizza_servlet;

import org.example.pizzacrud.servlet.Dto;
import org.json.simple.JSONObject;

public record PizzaDto(int id, String name, double price) implements Dto {

    @Override
    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("name", name);
        jsonObject.put("price", price);
        return jsonObject;
    }

}
