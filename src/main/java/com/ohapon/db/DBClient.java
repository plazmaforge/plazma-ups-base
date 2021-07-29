package com.ohapon.db;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBClient {

    public static void main(String[] args) {
        DBClient client = new DBClient();
        Settings settings = client.loadSettings();

        System.out.println(settings);
    }

    protected Settings loadSettings() {
        Properties properties = loadProperties();

        Settings settings = new Settings();
        settings.setUrl(System.getenv(Settings.PROPERTY_URL) == null ? properties.getProperty(Settings.PROPERTY_URL) : System.getenv(Settings.PROPERTY_URL));
        settings.setUser(System.getenv(Settings.PROPERTY_USER) == null ? properties.getProperty(Settings.PROPERTY_USER) : System.getenv(Settings.PROPERTY_USER));
        settings.setPassword(System.getenv(Settings.PROPERTY_PASSWORD)  == null ? properties.getProperty(Settings.PROPERTY_PASSWORD) : System.getenv(Settings.PROPERTY_PASSWORD));
        settings.setReportsPath(System.getenv(Settings.PROPERTY_REPORTS_PATH)  == null ? properties.getProperty(Settings.PROPERTY_REPORTS_PATH) : System.getenv(Settings.PROPERTY_REPORTS_PATH));

        return settings;
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
