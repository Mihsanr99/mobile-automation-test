package stepdefinitions;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.openqa.selenium.remote.DesiredCapabilities;
import pages.CartPage;
import pages.LoginPage;
import pages.ProductPage;

import java.net.URL;

import static org.junit.Assert.assertTrue;

public class PurchaseSteps {
    
    private AppiumDriver driver;
    private LoginPage loginPage;
    private ProductPage productPage;
    private CartPage cartPage;
    
    @Before
    public void setup() throws Exception {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("deviceName", "Android Device");
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("app", System.getProperty("user.dir") + "/apps/Android.SauceLabs.Mobile.Sample.app.2.7.1.apk");
        caps.setCapability("appPackage", "com.swaglabsmobileapp");
        caps.setCapability("appActivity", ".MainActivity");
        
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), caps);
        
        loginPage = new LoginPage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
    }
    
    @Given("I open the Sauce Labs app")
    public void iOpenTheSauceLabsApp() {
        // App already opened in setup
    }
    
    @When("I login with valid credentials")
    public void iLoginWithValidCredentials() {
        loginPage.login("standard_user", "secret_sauce");
    }
    
    @And("I add {string} to cart")
    public void iAddToCart(String productName) {
        productPage.addProductToCart(productName);
    }
    
    @And("I open the cart")
    public void iOpenTheCart() {
        productPage.openCart();
    }
    
    @And("I proceed to checkout")
    public void iProceedToCheckout() {
        cartPage.completeCheckout("John", "Doe", "12345");
    }
    
    @Then("I should see order confirmation")
    public void iShouldSeeOrderConfirmation() {
        assertTrue("Order confirmation not displayed", cartPage.isOrderConfirmed());
    }
    
    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}