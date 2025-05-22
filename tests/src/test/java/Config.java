import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class Config {
    private static final Properties PROPS = new Properties();

    static {
        try (InputStream in = Config.class.getClassLoader()
                                         .getResourceAsStream("config.properties")) {
            if (in == null) {
                throw new IllegalStateException("config.properties not found on classpath");
            }
            PROPS.load(in);
        } catch (IOException e) {
            throw new ExceptionInInitializerError("Failed to load configuration: " + e);
        }
    }

    private Config() {}

    private static String get(String key) {
        String val = PROPS.getProperty(key);
        if (val == null) {
            throw new IllegalArgumentException("Missing config key: " + key);
        }
        return val;
    }
    private static int getInt(String key) {
        String val = get(key);
        try {
            return Integer.parseInt(val);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                "Config value for '" + key + "' is not a valid integer: " + val, e);
        }
    }

    // URLs
    public static String characterAhUrl() {
        return get("CHARACTER_AH_URL");
    }
    public static String loginUrl() {
        return get("LOGIN_URL");
    }
    public static String logoutUrl() {
        return get("LOGOUT_URL");
    }

    public static String mainUrl() {
        return get("MAIN_URL");
    }
    // Page titles
    public static String expectedTitle() {
        return get("EXPECTED_TITLE");
    }

    // Credentials
    public static String validUsername() {
        return get("VALID_USERNAME");
    }
    public static String validPassword() {
        return get("VALID_PASSWORD");
    }
    // Character Filter data
        public static Integer minLevel() {
        return getInt("MIN_LEVEL");
    }
    public static Integer minAuctionPrice() {
        return getInt("MIN_AUCTION_PRICE");
    }
}