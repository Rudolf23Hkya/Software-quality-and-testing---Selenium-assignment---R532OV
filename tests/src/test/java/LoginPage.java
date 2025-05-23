import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends BasePage {

    private static final String URL = Config.loginUrl();

    private By enLangButton  = By.xpath("//img[@alt='EN']");
    private By usernameField = By.id("account");
    private By passwordField = By.id("pass");
    private By loginButton   = By.id("login");
    private By errorMessage  = By.cssSelector(".error, .login-error");

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    // Navigate to the login page //
    public LoginPage open() {
        navigateTo(URL);
        return this;
    }

    // Switch language to English //
    public LoginPage switchToEnglish() {
        wait.until(ExpectedConditions.elementToBeClickable(enLangButton)).click();
        return this;
    }

    // Enter username //
    public LoginPage enterUsername(String username) {
        WebElement user = waitVisibility(usernameField);
        user.clear();
        user.sendKeys(username);
        return this;
    }

    // Enter password //
    public LoginPage enterPassword(String password) {
        WebElement pass = waitVisibility(passwordField);
        pass.clear();
        pass.sendKeys(password);
        return this;
    }

    // Submit credentials and navigate to dashboard //
    public DashboardPage submit() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        return new DashboardPage(driver, wait);
    }

    // Convenience: open page, switch to EN, fill in creds, and submit //
    public DashboardPage submitValidCredentials(String username, String password) {
        return open()
               .switchToEnglish()
               .enterUsername(username)
               .enterPassword(password)
               .submit();
    }

    // Check if an error message is displayed //
    public boolean isErrorDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).isDisplayed();
    }
}