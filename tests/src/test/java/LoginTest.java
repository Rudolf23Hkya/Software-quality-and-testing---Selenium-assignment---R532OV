import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

public class LoginTest extends BaseTest{
    private static final String LOGOUT_URL = "https://login.tauriwow.com/?ref=https://tauriwow.com/";

    @Before
    public void setup() throws Exception {
        ChromeOptions options = new ChromeOptions();
        // adjust the Selenium Hub URL as needed
        driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        driver.manage().window().maximize();
        // Selenium 3 style constructor: timeout in seconds
        wait = new WebDriverWait(driver, 10L);
        loginPage = new LoginPage(driver, wait);
    }

    @Test
    public void testSuccessfulLogin() {
        driver.get(LOGIN_URL);
        DashboardPage dashboard = loginPage.submitValidCredentials(VALID_EMAIL, VALID_PASSWORD);
        // verify redirection to main site after login
        wait.until(ExpectedConditions.urlToBe(MAIN_URL));
        assertEquals("Should redirect to homepage after login", MAIN_URL, driver.getCurrentUrl());
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