package Infrastructure;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Database {
    private static final Properties config = loadConfig();

    private static Properties loadConfig() {
        Properties props = new Properties();

        try (InputStream input = Database.class.getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (input == null) {
                throw new RuntimeException(
                        "Unable to find config.properties. " +
                                "Please copy config.properties.example to config.properties " +
                                "and update with your database credentials."
                );
            }

            props.load(input);
            return props;

        } catch (IOException e) {
            throw new RuntimeException("Error loading config.properties: " + e.getMessage(), e);
        }
    }

    public static Connection getConnection() throws SQLException {
        String url = config.getProperty("db.url");
        String user = config.getProperty("db.user");
        String password = config.getProperty("db.password");

        return DriverManager.getConnection(url, user, password);
    }

    public static void initializeDatabase() {
        String sql = """
            CREATE TABLE IF NOT EXISTS transactions (
                id UUID PRIMARY KEY,
                type VARCHAR(10) NOT NULL,
                amount DECIMAL(10, 2) NOT NULL,
                category VARCHAR(50),
                description TEXT,
                date DATE NOT NULL
            );
        """;

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Database initialized successfully.");
        } catch (SQLException e) {
            System.out.println("Could not initialize database: " + e.getMessage());
            System.out.println("1. Please check PostgreSQL is running.");
            System.out.println("2. Database 'finansprojektet' exists ");
            System.out.println("3. Credentials in config.properies ");
            e.printStackTrace();
            System.exit(1);
        }
    }
}