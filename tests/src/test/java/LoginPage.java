import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private static final String URL = "https://login.tauriwow.com/";
    private By enLangButton    = By.xpath("//img[@alt='EN']");
    private By usernameField   = By.id("account");
    private By passwordField   = By.id("pass");
    private By loginButton     = By.id("login");
    private By errorMessage    = By.cssSelector(".error, .login-error");

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait   = wait;
    }

    public void open() {
        driver.get(URL);
    }

    public void switchToEnglish() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(enLangButton));
        btn.click();
    }

    public DashboardPage submitValidCredentials(String username, String password) {
        switchToEnglish();
        enterUsername(username);
        enterPassword(password);
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        return new DashboardPage(driver, wait);
    }

    private void enterUsername(String username) {
        WebElement user = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        user.clear();
        user.sendKeys(username);
    }

    private void enterPassword(String password) {
        WebElement pass = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        pass.clear();
        pass.sendKeys(password);
    }

    public boolean isErrorDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).isDisplayed();
    }
}