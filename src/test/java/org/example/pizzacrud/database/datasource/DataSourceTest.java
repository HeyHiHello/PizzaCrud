package org.example.pizzacrud.database.datasource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

class DataSourceTest {
    @Test
    void dataSourceInitTest() {
        Assertions.assertDoesNotThrow(() -> {
            try (Connection conn = DataSource.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("select * from Pizzas")) {

            }
        });
    }
}
