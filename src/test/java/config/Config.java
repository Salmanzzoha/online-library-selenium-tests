package config;

/**
 * Central place for environment settings.
 * Change BASE_URL here if your XAMPP folder name is different
 * from "online-library" (e.g. "online-library-system").
 */
public class Config {

    // Must match the BASE_PATH defined in config/database.php of the PHP app
    public static final String BASE_URL = "http://localhost/online-library/";

    // Seeded default admin account (from l_db.sql)
    public static final String ADMIN_EMAIL = "admin@library.com";
    public static final String ADMIN_PASSWORD = "admin123";

    // How long (seconds) to wait for elements before failing a test
    public static final int EXPLICIT_WAIT_SECONDS = 10;
}
