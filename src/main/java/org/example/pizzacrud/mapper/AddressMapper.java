package org.example.pizzacrud.mapper;

import org.example.pizzacrud.database.entity.Address;
import org.example.pizzacrud.servlet.customer_servlet.AddressDto;

public class AddressMapper {
    private AddressMapper() {
    }

    public static Address addressDtoToAddress(AddressDto addressDto) {
        Address address = new Address();
        address.setId(addressDto.id());
        address.setCity(addressDto.city());
        address.setStreet(addressDto.street());
        address.setBuilding(addressDto.building());
        return address;
    }

    public static AddressDto addressToAddressDto(Address address) {
        return new AddressDto(address.getId(), address.getCity(), address.getStreet(), address.getBuilding());
    }
}
