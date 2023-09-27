package plazma.ups.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class DBClient {

    public static void main(String[] args) {
        DBClient client = new DBClient();
        Settings settings = client.loadSettings();

        System.out.println(settings);

        client.start(settings);
    }

    protected void start(Settings settings) {
        try (Scanner in = new Scanner(System.in)) {
            Connection connection = getConnection(settings);
            QueryExecutor executor = new QueryExecutor();
            while (true) {
                if (in.hasNextLine()) {
                    String query = in.nextLine();
                    if (query == null || query.isEmpty()) {
                        return;
                    }
                    executor.executeQuery(query);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected Connection getConnection(Settings settings) throws SQLException {
        // TODO
        //return DriverManager.getConnection(settings.getUrl(), settings.getUser(), settings.getPassword());
        return null;
    }

    protected Settings loadSettings() {
        Properties properties = loadProperties();

        Settings settings = new Settings();
        settings.setUrl(getProperty(properties, Settings.PROPERTY_URL));
        settings.setUser(getProperty(properties, Settings.PROPERTY_USER));
        settings.setPassword(getProperty(properties, Settings.PROPERTY_PASSWORD));
        settings.setReportsPath(getProperty(properties, Settings.PROPERTY_REPORTS_PATH));

        return settings;
    }

    protected String getProperty(Properties properties, String name) {
        String value = System.getenv(name);
        if (value != null) {
            return value;
        }
        return properties.getProperty(name);
    }

    protected Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream is = Settings.class.getClassLoader().getResourceAsStream(Settings.DEFAULT_FILE_NAME)) {
            if (is == null) {
                return properties;
            }
            properties.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Can't load properties file: " + Settings.DEFAULT_FILE_NAME, e);
        }
        return properties;
    }

}
