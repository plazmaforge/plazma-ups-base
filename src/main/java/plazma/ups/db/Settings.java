package plazma.ups.db;

public class Settings {

    public static final String PROPERTY_URL = "url";
    public static final String PROPERTY_USER = "user";
    public static final String PROPERTY_PASSWORD = "password";
    public static final String PROPERTY_REPORTS_PATH = "reportsPath";

    public static final String DEFAULT_FILE_NAME = "application.properties";

    private String url;
    private String user;
    private String password;
    private String reportsPath;

    public Settings() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getReportsPath() {
        return reportsPath;
    }

    public void setReportsPath(String reportsPath) {
        this.reportsPath = reportsPath;
    }

    @Override
    public String toString() {
        return "Settings{" +
                "url='" + url + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", reportsPath='" + reportsPath + '\'' +
                '}';
    }
}
