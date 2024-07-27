package org.example.pizzacrud.servlet.customer_servlet;

import org.example.pizzacrud.servlet.Dto;
import org.json.simple.JSONObject;

public record AddressDto(int id, String city, String street, String building) implements Dto {
    @Override
    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("city", city);
        jsonObject.put("street", street);
        jsonObject.put("building", building);
        return jsonObject;
    }
}
