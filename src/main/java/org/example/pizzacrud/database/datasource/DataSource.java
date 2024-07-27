package org.example.pizzacrud.database.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {

    private static final HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {
        init();
    }

    private DataSource() {
    }

    public static void init() {
        config.setJdbcUrl(DataSourcePropertiesProvider.getProperty("db.url"));
        config.setUsername(DataSourcePropertiesProvider.getProperty("db.username"));
        config.setPassword(DataSourcePropertiesProvider.getProperty("db.password"));
        config.setDriverClassName(DataSourcePropertiesProvider.getProperty("db.driver-class-name"));
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
