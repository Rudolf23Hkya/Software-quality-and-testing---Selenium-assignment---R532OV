import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class DashboardPage extends BasePage {
    private By userMenuToggle = By.id("navbar-user");
    private By logoutButton   = By.xpath("//button[normalize-space(.)='Logout']");
    private By logoutLink = By.cssSelector("a[href='/account/logout']");
    public DashboardPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    private void openUserMenu() {
        waitVisibility(userMenuToggle).click();
    }

    public boolean isLogoutButtonVisible() {
        openUserMenu();
        return waitVisibility(logoutButton).isDisplayed();
    }

    public LoginPage clickLogout() {
        wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).click();
        return new LoginPage(driver, wait);
    }
}