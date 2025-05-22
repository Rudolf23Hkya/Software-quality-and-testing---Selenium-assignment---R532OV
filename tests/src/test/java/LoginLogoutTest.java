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

public class LoginLogoutTest extends BaseTest{
    private static final String LOGOUT_URL = Config.logoutUrl();

    @Before
    public void setup() throws Exception {
        ChromeOptions options = new ChromeOptions();
        driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 10L);
        loginPage = new LoginPage(driver, wait);
    }

    @Test
    public void testSuccessfulLogin() {
        driver.get(LOGIN_URL);
        DashboardPage dashboard = loginPage.submitValidCredentials(VALID_USERNAME, VALID_PASSWORD);
        // Verify redirection to main site after login
        wait.until(ExpectedConditions.urlToBe(MAIN_URL));
        assertEquals("Should redirect to main page after login", MAIN_URL, driver.getCurrentUrl());
    }

    @Test
    public void testLogout() {
        loginPage.open();
        DashboardPage dashboard = loginPage.submitValidCredentials(VALID_USERNAME, VALID_PASSWORD);
        assertTrue("Logout button should be visible after login",
                dashboard.isLogoutButtonVisible());

        loginPage = dashboard.clickLogout();
        // The logout page is similiar to the Login page, but different URL
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