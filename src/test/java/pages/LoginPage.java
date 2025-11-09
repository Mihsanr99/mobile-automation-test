package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    
    private AppiumDriver driver;
    
    @FindBy(xpath = "//android.widget.EditText[@content-desc='test-Username']")
    private WebElement usernameField;
    
    @FindBy(xpath = "//android.widget.EditText[@content-desc='test-Password']")
    private WebElement passwordField;
    
    @FindBy(xpath = "//android.view.ViewGroup[@content-desc='test-LOGIN']")
    private WebElement loginButton;
    
    public LoginPage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
    
    public void enterUsername(String username) {
        usernameField.sendKeys(username);
    }
    
    public void enterPassword(String password) {
        passwordField.sendKeys(password);
    }
    
    public void clickLogin() {
        loginButton.click();
    }
    
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }
}