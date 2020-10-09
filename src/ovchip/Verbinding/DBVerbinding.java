package ovchip.Verbinding;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBVerbinding {
    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost/ovchip", "postgres", "welkom123");
        return connection;
    }
}
