package org.example.pizzacrud.database;

import org.example.pizzacrud.database.datasource.DataSource;
import org.example.pizzacrud.database.datasource.DataSourcePropertiesProvider;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.testcontainers.containers.MySQLContainer;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestDbInitializer {
    public static MySQLContainer buildMySQLContainer() {
        return new MySQLContainer("mysql:8.0")
                .withDatabaseName("PizzaAppDB")
                .withUsername("root")
                .withPassword("root");
    }

    public static MockedStatic<DataSourcePropertiesProvider> mockDataSourcePropertiesWithMySQLContainer(MySQLContainer mySQLContainer) {
        MockedStatic<DataSourcePropertiesProvider> datasourcePropertiesProviderMockedStatic = Mockito.mockStatic(DataSourcePropertiesProvider.class);
        Mockito.when(DataSourcePropertiesProvider.getProperty("db.url")).thenReturn(mySQLContainer.getJdbcUrl());
        Mockito.when(DataSourcePropertiesProvider.getProperty("db.username")).thenReturn(mySQLContainer.getUsername());
        Mockito.when(DataSourcePropertiesProvider.getProperty("db.password")).thenReturn(mySQLContainer.getPassword());
        Mockito.when(DataSourcePropertiesProvider.getProperty("db.driver-class-name")).thenReturn("com.mysql.cj.jdbc.Driver");
        DataSource.init();
        return datasourcePropertiesProviderMockedStatic;
    }

    public static void dropAll() {
        try (BufferedReader reader = buildSqlFileReader("sql/drop.sql");
             Connection conn = DataSource.getConnection()) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }
                PreparedStatement stmt = conn.prepareStatement(line);
                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createAll() {

        try (BufferedReader reader = buildSqlFileReader("sql/createSchema.sql");
             Connection conn = DataSource.getConnection()) {

            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }
                builder.append(line);
                if (line.endsWith(";")) {
                    PreparedStatement stmt = conn.prepareStatement(builder.toString());
                    stmt.executeUpdate();
                    builder = new StringBuilder();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertTestData() {

        try (BufferedReader reader = buildSqlFileReader("sql/insertTestData.sql");
             Connection conn = DataSource.getConnection()) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }
                PreparedStatement stmt = conn.prepareStatement(line);
                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static BufferedReader buildSqlFileReader(String fileName) {
        return new BufferedReader(new InputStreamReader(
                TestDbInitializer.class
                        .getClassLoader()
                        .getResourceAsStream(fileName)));
    }
}
