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
                System.out.println("No config.properties found. Using default settings:");
                System.out.println("  - Host: localhost:5432");
                System.out.println("  - Database: finansprojektet");
                System.out.println("  - User: postgres");
                System.out.println("To customize, create src/main/resources/config.properties");
                System.out.println();

                props.setProperty("db.url", "jdbc:postgresql://localhost:5432/finansprojektet");
                props.setProperty("db.user", "postgres");
                props.setProperty("db.password", "lösenord");

                return props;
            }

            props.load(input);
            return props;

        } catch (IOException e) {
            throw new RuntimeException("Error loading config" + e.getMessage(), e);
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
            System.out.println("Please check!");
            System.out.println("1. PostgreSQL is running");
            System.out.println("2. Database 'finansprojektet' exists ");
            System.out.println("3. Credentials in config.properties ");
            e.printStackTrace();
            System.exit(1);
        }
    }
}