package ru.cft.focusstart.kolesnikov.repository;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class DataSourceProvider {
    private final static String FILE_NAME = "/application.properties";
    private static final DataSourceProvider instance = new DataSourceProvider();
    private final static Logger log = LoggerFactory.getLogger(DataSourceProvider.class);
    private final DataSource dataSource;

    private DataSourceProvider() {
        Properties properties = new Properties();
        try (InputStream propertiesReader = DataSourceProvider.class.getResourceAsStream(FILE_NAME)) {
            properties.load(propertiesReader);
        } catch (IOException e) {
            log.error("properties file not found", e);
        }
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            log.error("PostgresSQL driver not found");
        }
        dataSource = new HikariDataSource(new HikariConfig(properties));
    }

    public static DataSource getDataSource() {
        return instance.dataSource;
    }
}
