import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

public class LoginTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String LOGIN_URL = "https://www.pioneerdj.com/en-us/account/login/?relPath=en-us%2F";
    private static final String VALID_EMAIL = "kotrab23@gmail.com";
    private static final String VALID_PASSWORD = "ElteTest123";

    @Before
    public void setup() throws Exception {
        ChromeOptions options = new ChromeOptions();
        driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void testSuccessfulLogin() {
        System.out.println("1");
        driver.get(LOGIN_URL);
        System.out.println("2");
        LoginPage loginPage = new LoginPage(driver, wait);
        System.out.println("3");
        DashboardPage dashboard = loginPage.submitValidCredentials(VALID_EMAIL, VALID_PASSWORD);
        System.out.println("4");
        assertTrue("Logout link should be visible after successful login", dashboard.isLogoutLinkVisible());
    }

    @Test
    public void testFailedLogin() {
        driver.get(LOGIN_URL);
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.submitInvalidCredentials(VALID_EMAIL, "WrongPassword123");
        assertTrue("Error message should display on failed login", loginPage.isErrorDisplayed());
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}