import static org.junit.Assert.*;

import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CharacterAhTests extends BaseTest{
    private String CHARACTER_AH_URL = Config.characterAhUrl();
    private String EXPECTED_TITLE = Config.expectedTitle();

    private Integer MIN_LEVEL=Config.minLevel();
    private Integer MIN_AUCTION_PRICE =Config.minAuctionPrice();

    @Before
    public void setup() throws Exception {
        ChromeOptions options = new ChromeOptions();
        driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 10L);
        loginPage = new LoginPage(driver, wait);
    }

    @Test
    public void testCharacterAHStaticPageLoads() {
        // Login 
        driver.get(LOGIN_URL);
        DashboardPage dashboard = loginPage.submitValidCredentials(VALID_USERNAME, VALID_PASSWORD);
        wait.until(ExpectedConditions.urlToBe(MAIN_URL));
        // Navigate directly to the static URL
        driver.get(CHARACTER_AH_URL);

        // Wait until the URL is CHARACTER_AH_URL
        wait.until(ExpectedConditions.urlToBe(CHARACTER_AH_URL));
        assertEquals("Browser should be at the Character AH URL",
                     CHARACTER_AH_URL,
                     driver.getCurrentUrl());
    }

    @Test
    public void testPageTitle() {
        driver.get(LOGIN_URL);
        DashboardPage dashboard = loginPage.submitValidCredentials(VALID_USERNAME, VALID_PASSWORD);
        wait.until(ExpectedConditions.urlToBe(MAIN_URL));
        driver.get(CHARACTER_AH_URL);

        wait.until(ExpectedConditions.urlToBe(CHARACTER_AH_URL));

        wait.until(ExpectedConditions.titleIs(EXPECTED_TITLE));

        // Assert the browser’s title matches
        assertEquals("Page title should match the <title> element",
                     EXPECTED_TITLE,
                     driver.getTitle());
    }

    @Test
    public void testCharacterAHForm() throws InterruptedException {
        // Navigate to the Character AH page + login
        driver.get(LOGIN_URL);
        DashboardPage dashboard = loginPage.submitValidCredentials(VALID_USERNAME, VALID_PASSWORD);
        wait.until(ExpectedConditions.urlToBe(MAIN_URL));
        driver.get(CHARACTER_AH_URL);

        wait.until(ExpectedConditions.urlToBe(CHARACTER_AH_URL));

        // Fill in minimum item level = MIN_LEVEL
        By ilevelMin = By.id("filter-ilevel_min");
        WebElement minInput = wait.until(ExpectedConditions.visibilityOfElementLocated(ilevelMin));
        minInput.clear();
        minInput.sendKeys(MIN_LEVEL.toString());

        // Fill in minimum auction price = MIN_AUCTION_PRICE
        WebElement minPrice = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.id("filter-auctprice_min")));
        minPrice.clear();
        minPrice.sendKeys(MIN_AUCTION_PRICE.toString());

        // Click the Filter button
        By filterBtn  = By.name("filter-filter");
        wait.until(ExpectedConditions.elementToBeClickable(filterBtn)).click();

        // The page is slow, so a bit of waiting.
        Thread.sleep(2000);

        // Wait for at least one auction row to appear
        By auctionRows = By.cssSelector(".chah-auction");
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(auctionRows));

        // Grab the first auction’s price indicator
        WebElement firstRow = driver.findElements(auctionRows).get(0);
        String priceText = firstRow
            .findElement(By.cssSelector(".tc-indicator"))
            .getText();

        // Parsing to integer
        int price = Integer.parseInt(priceText.replace(".", ""));
        // The first auction is at least MIN_AUCTION_PRICE
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