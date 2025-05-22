import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends BasePage {
    private By emailField = By.id("gigya-loginID-120447766921773000");
    private By passwordField = By.id("gigya-password-89694335567495600");
    private By submitButton = By.cssSelector("input.gigya-input-submit[type='submit'][value='Log in']");


    private By errorMessage = By.cssSelector(".login-error");

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void enterEmail(String email) {
        waitVisibility(emailField).clear();
        driver.findElement(emailField).sendKeys(email);
    }

    public void enterPassword(String password) {
        waitVisibility(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
    }

    public DashboardPage submitValidCredentials(String email, String password) {
        enterEmail(email);
        System.out.println("email");
        enterPassword(password);
        System.out.println("pw");
        driver.findElement(submitButton).click();
        System.out.println("submit");
        return new DashboardPage(driver, wait);
    }

    public void submitInvalidCredentials(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        driver.findElement(submitButton).click();
    }

    public boolean isErrorDisplayed() {
        return waitVisibility(errorMessage).isDisplayed();
    }
}