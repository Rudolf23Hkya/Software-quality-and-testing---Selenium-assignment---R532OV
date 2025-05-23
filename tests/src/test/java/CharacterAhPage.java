import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;

public class CharacterAhPage extends BasePage {
    private static final String URL = Config.characterAhUrl();
    private By ilevelMinField    = By.id("filter-ilevel_min");
    private By auctPriceMinField = By.id("filter-auctprice_min");
    private By filterButton      = By.name("filter-filter");
    private By auctionRows       = By.cssSelector(".chah-auction");
    private By priceIndicator    = By.cssSelector(".tc-indicator");

    public CharacterAhPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    // Navigate to the Character AH page //
    public CharacterAhPage open() {
        driver.get(URL);
        wait.until(ExpectedConditions.urlToBe(URL));
        return this;
    }

    // Fill minimum item level //
    public CharacterAhPage setMinItemLevel(int level) {
        WebElement fld = waitVisibility(ilevelMinField);
        fld.clear();
        fld.sendKeys(String.valueOf(level));
        return this;
    }

    // Fill minimum auction price //
    public CharacterAhPage setMinAuctionPrice(int price) {
        WebElement fld = waitVisibility(auctPriceMinField);
        fld.clear();
        fld.sendKeys(String.valueOf(price));
        return this;
    }

    // Click the Filter button //
    public CharacterAhPage filter() {
        wait.until(ExpectedConditions.elementToBeClickable(filterButton)).click();
        return this;
    }

    // Return the first auction’s price as integer //
    public int getFirstAuctionPrice() {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(auctionRows));
        WebElement first = driver.findElements(auctionRows).get(0);
        String txt = first.findElement(priceIndicator).getText();
        return Integer.parseInt(txt.replace(".", ""));
    }
}

