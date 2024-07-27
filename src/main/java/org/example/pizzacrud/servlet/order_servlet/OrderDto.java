package org.example.pizzacrud.servlet.order_servlet;

import org.example.pizzacrud.servlet.Dto;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

public record OrderDto(int id, List<Long> pizzasId, int customerId) implements Dto {
    @Override
    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        JSONArray pizzasJsonArray = new JSONArray();

        for (Long pizzaId : pizzasId) {
            pizzasJsonArray.add(pizzaId);
        }
        jsonObject.put("pizzasId", pizzasJsonArray);
        jsonObject.put("customerId", customerId);
        return jsonObject;
    }
}
