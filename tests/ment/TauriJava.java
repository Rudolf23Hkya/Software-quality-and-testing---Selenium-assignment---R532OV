import static org.junit.Assert.*;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;
import java.net.MalformedURLException;

public class TauriJava {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setup() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 10);
    }

    private WebElement waitVisibilityAndFindElement(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return driver.findElement(locator);
    }

    @Test
    public void testLoginFormLoads() {
        driver.get("https://login.tauriwow.com/");

        // 1) Switch to English
        waitVisibilityAndFindElement(By.xpath("//img[@alt='EN']")).click();

        // 2) Wait for the form fields
        WebElement userField = waitVisibilityAndFindElement(By.id("account"));
        WebElement passField = waitVisibilityAndFindElement(By.id("pass"));
        WebElement submitBtn = waitVisibilityAndFindElement(By.id("login"));

        System.out.println("oke");

        // 3) Assert they’re visible
        assertTrue("Username field should be displayed", userField.isDisplayed());
        assertTrue("Password field should be displayed", passField.isDisplayed());
        assertTrue("Submit button should be displayed", submitBtn.isDisplayed());
    }

    @After
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
