import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected LoginPage loginPage;

    protected static final String LOGIN_URL      = "https://login.tauriwow.com/";
    protected static final String VALID_EMAIL    = "rudolf23Hkya";
    protected static final String VALID_PASSWORD = "ElteTest123";

}
