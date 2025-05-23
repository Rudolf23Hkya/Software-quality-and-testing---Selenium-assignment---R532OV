import static org.junit.Assert.*;

import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CharacterAhTests extends BaseTest {
    private CharacterAhPage ahPage;
    private Integer MIN_LEVEL = Config.minLevel();
    private Integer MIN_AUCTION_PRICE = Config.minAuctionPrice();

    @Before
    public void setup() throws Exception {
        ChromeOptions options = new ChromeOptions();
        driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 10L);
        loginPage = new LoginPage(driver, wait);
        ahPage = new CharacterAhPage(driver, wait);
    }

    @Test
    public void testCharacterAHStaticPageLoads() {
        // Login
        loginPage.open();
        DashboardPage dashboard = loginPage.submitValidCredentials(VALID_USERNAME, VALID_PASSWORD);
        wait.until(ExpectedConditions.urlToBe(MAIN_URL));
        // Navigate using the Page Object
        ahPage.open();

        assertEquals("Browser should be at the Character AH URL",
                     Config.characterAhUrl(),
                     driver.getCurrentUrl());
    }

    @Test
    public void testPageTitle() {
        loginPage.open();
        DashboardPage dashboard = loginPage.submitValidCredentials(VALID_USERNAME, VALID_PASSWORD);
        wait.until(ExpectedConditions.urlToBe(MAIN_URL));
        ahPage.open();

        assertEquals("Page title should match the <title> element",
                     Config.expectedTitle(),
                     ahPage.getPageTitle());
    }

    @Test
    public void testCharacterAHForm() throws InterruptedException {
        loginPage.open();
        DashboardPage dashboard = loginPage.submitValidCredentials(VALID_USERNAME, VALID_PASSWORD);
        wait.until(ExpectedConditions.urlToBe(MAIN_URL));
        
        // Use Page Object methods to interact with the filter form
        ahPage.open()
              .setMinItemLevel(MIN_LEVEL)
              .setMinAuctionPrice(MIN_AUCTION_PRICE)
              .filter();

        // Wait for data to load - It s a slow
        Thread.sleep(2000);

        int price = ahPage.getFirstAuctionPrice();
        assertTrue("Expected first auction price ≥ MIN_AUCTION_PRICE but was " + price,
                   price >= MIN_AUCTION_PRICE);
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}