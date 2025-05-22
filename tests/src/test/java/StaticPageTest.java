import static org.junit.Assert.*;

import java.net.URL;
import java.time.Duration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class StaticPageTest extends BaseTest{
    private WebDriver driver;
    private WebDriverWait wait;

    private static final String CHARACTER_AH_URL = "https://tauriwow.com/vippanel#vipdsh/characterah";

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
    public void testCharacterAHStaticPageLoads() {
        // Login 
        driver.get(LOGIN_URL);
        DashboardPage dashboard = loginPage.submitValidCredentials(VALID_EMAIL, VALID_PASSWORD);
        // verify redirection to main site after login
        wait.until(ExpectedConditions.urlToBe("https://tauriwow.com/"));
        // 1) Navigate directly to the “static” URL
        driver.get(CHARACTER_AH_URL);

        // 2) Wait until the URL is exactly what we expect
        wait.until(ExpectedConditions.urlToBe(CHARACTER_AH_URL));
        assertEquals("Browser should be at the Character AH URL",
                     CHARACTER_AH_URL,
                     driver.getCurrentUrl());
    }
    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}