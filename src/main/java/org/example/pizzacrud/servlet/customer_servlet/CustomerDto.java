package org.example.pizzacrud.servlet.customer_servlet;

import org.example.pizzacrud.mapper.AddressMapper;
import org.example.pizzacrud.database.entity.Address;
import org.example.pizzacrud.servlet.Dto;
import org.json.simple.JSONObject;

public record CustomerDto(int id, String firstName, String lastName, Address address) implements Dto {
    @Override
    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("firstName", firstName);
        jsonObject.put("lastName", lastName);
        AddressDto addressDto = AddressMapper.addressToAddressDto(address);
        jsonObject.put("address", addressDto.toJsonObject());
        return jsonObject;
    }
}
