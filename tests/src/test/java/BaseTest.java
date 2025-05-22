// BaseTest.java
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

    protected static final String LOGIN_URL      = "https://login.tauriwow.com/";
    protected static final String VALID_EMAIL    = "rudolf23Hkya";
    protected static final String VALID_PASSWORD = "ElteTest123";

}
