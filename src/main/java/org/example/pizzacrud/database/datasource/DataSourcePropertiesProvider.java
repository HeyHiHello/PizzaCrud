package org.example.pizzacrud.database.datasource;

import java.io.InputStream;
import java.util.Properties;

public class DataSourcePropertiesProvider {
    private static Properties properties;
    private static String filename = "db.properties";

    private DataSourcePropertiesProvider() {
    }

    static {
        loadProperties();
    }

    public static void setFilename(String filename) {
        DataSourcePropertiesProvider.filename = filename;
        loadProperties();
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    private static void loadProperties() {
        properties = new Properties();
        try (InputStream inFile = DataSourcePropertiesProvider.class.getClassLoader().getResourceAsStream(filename)) {
            properties.load(inFile);
        } catch (Exception e) {
            throw new IllegalStateException();
        }
    }
}
