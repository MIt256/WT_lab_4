package by.bsuir.lab.servlethotel.config;

import javax.sql.DataSource;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Configuration {

    static {
        try {
           Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static final String URL = "jdbc:postgresql://localhost:5432/servlet_hotel";
    public static final String USERNAME = "postgres";
    public static final String PASSWORD = "1";
    public static final Integer POOL_SIZE = 10;

    public static final Integer TOKEN_MINUTE_LIFETIME = 10;
}
