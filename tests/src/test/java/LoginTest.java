import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

public class LoginTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private LoginPage loginPage;

    private static final String LOGIN_URL      = "https://login.tauriwow.com/";
    private static final String LOGOUT_URL      = "https://login.tauriwow.com/?ref=https://tauriwow.com/";
    private static final String VALID_EMAIL    = "rudolf23Hkya";
    private static final String VALID_PASSWORD = "ElteTest123";

    @Before
    public void setup() throws Exception {
        ChromeOptions options = new ChromeOptions();
        driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 10);
        loginPage = new LoginPage(driver, wait);
    }

    @Test
    public void testSuccessfulLogin() {
        driver.get(LOGIN_URL);
        DashboardPage dashboard = loginPage.submitValidCredentials(VALID_EMAIL, VALID_PASSWORD);
        // verify redirection to main site after login
        wait.until(ExpectedConditions.urlToBe("https://tauriwow.com/"));
        assertEquals("Should redirect to homepage after login", "https://tauriwow.com/", driver.getCurrentUrl());
    }

    @Test
    public void testLogout() {
        loginPage.open();
        DashboardPage dashboard = loginPage.submitValidCredentials(VALID_EMAIL, VALID_PASSWORD);
        assertTrue("Logout button should be visible after login",
                dashboard.isLogoutButtonVisible());

        loginPage = dashboard.clickLogout();
        wait.until(ExpectedConditions.urlToBe(LOGOUT_URL));
        assertEquals("Should redirect to login page after logout",
                    LOGOUT_URL,
                    driver.getCurrentUrl());
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}