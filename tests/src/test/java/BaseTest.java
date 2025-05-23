import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.net.URL;

public abstract class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected LoginPage loginPage;

    protected String LOGIN_URL = Config.loginUrl();
    protected String MAIN_URL = Config.mainUrl();
    protected String VALID_USERNAME    = Config.validUsername();
    protected String VALID_PASSWORD = Config.validPassword();
}
