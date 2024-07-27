package org.example.pizzacrud.database.datasource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DataSourcePropertiesProviderTest {
    @Test
    void dataSourcePropertiesProviderTest() {
        DataSourcePropertiesProvider.setFilename("test.properties");
        Assertions.assertEquals("Hello", DataSourcePropertiesProvider.getProperty("test.Hello"));
        Assertions.assertEquals("world", DataSourcePropertiesProvider.getProperty("test.world"));
    }

    @Test
    void dataSourcePropertiesProviderFailTest() {
        Assertions.assertThrows(IllegalStateException.class, () -> DataSourcePropertiesProvider.setFilename("wrong.properties"));

    }
}
