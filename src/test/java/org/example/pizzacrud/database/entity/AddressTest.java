package org.example.pizzacrud.database.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AddressTest {

    Address address1 = new Address(1, "test_city", "test_street", "test_building");
    Address address2 = new Address(1, "test_city", "test_street", "test_building");

    @Test
    void getIdTest() {
        Assertions.assertEquals(1, address1.getId());
    }

    @Test
    void setIdTest() {
        address1.setId(2);
        Assertions.assertEquals(2, address1.getId());
    }

    @Test
    void getCityTest() {
        Assertions.assertEquals("test_city", address1.getCity());
    }

    @Test
    void setCityTest() {
        address1.setCity("new_city");
        Assertions.assertEquals("new_city", address1.getCity());
    }

    @Test
    void getStreetTest() {
        Assertions.assertEquals("test_street", address1.getStreet());
    }

    @Test
    void setStreetTest() {
        address1.setStreet("new_street");
        Assertions.assertEquals("new_street", address1.getStreet());
    }

    @Test
    void equalsTest() {
        Assertions.assertEquals(address1, address2);
    }

    @Test
    void equalsNullTest() {
        Assertions.assertNotEquals(address1, null);
    }

    @Test
    void equalsSameTest() {
        Assertions.assertEquals(address1, address1);
    }

    @Test
    void equalsFieldsTest() {
        address2.setId(2);
        Assertions.assertNotEquals(address1, address2);

        address2.setId(1);
        address2.setCity("new_city");
        Assertions.assertNotEquals(address1, address2);

        address2.setCity("test_city");
        address2.setStreet("new_street");
        Assertions.assertNotEquals(address1, address2);

        address2.setStreet("test_street");
        address2.setBuilding("new_building");
        Assertions.assertNotEquals(address1, address2);
    }
}
