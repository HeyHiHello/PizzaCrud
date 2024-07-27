package org.example.pizzacrud.mapper;

import org.example.pizzacrud.database.entity.Address;
import org.example.pizzacrud.servlet.customer_servlet.AddressDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AddressMapperTest {
    @Test
    void toDtoTest() {
        Address address = new Address(1, "test", "test", "test");
        AddressDto addressDto = AddressMapper.addressToAddressDto(address);

        Assertions.assertNotNull(addressDto);
        Assertions.assertEquals(address.getId(), addressDto.id());
    }

    @Test
    void toEntityTest() {
        AddressDto addressDto = new AddressDto(1, "test", "test", "test");
        Address address = AddressMapper.addressDtoToAddress(addressDto);

        Assertions.assertNotNull(address);
        Assertions.assertEquals(addressDto.id(), address.getId());
    }
}
