import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DashboardPage extends BasePage {
    private By logoutLink = By.linkText("Logout");

    public DashboardPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public boolean isLogoutLinkVisible() {
        return waitVisibility(logoutLink).isDisplayed();
    }
}
