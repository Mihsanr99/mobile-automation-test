package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductPage {
    
    private AppiumDriver driver;
    private WebDriverWait wait;
    
    @FindBy(xpath = "//android.view.ViewGroup[@content-desc='test-Cart drop zone']/android.view.ViewGroup/android.widget.TextView")
    private WebElement cartBadge;
    
    @FindBy(xpath = "//android.view.ViewGroup[@content-desc='test-Cart']")
    private WebElement cartIcon;
    
    public ProductPage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
    
    public void addProductToCart(String productName) {
        try {
            // Wait for products to load
            Thread.sleep(2000);
            
            // Scroll to product if needed
            scrollToProduct(productName);
            
            // Try multiple strategies to find and click ADD TO CART button
            boolean clicked = false;
            
            // Strategy 1: Using content-desc with product name
            try {
                String contentDesc = "test-ADD TO CART";
                WebElement addButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("(//android.view.ViewGroup[@content-desc='" + contentDesc + "'])[1]")
                ));
                addButton.click();
                clicked = true;
                System.out.println("Added to cart using Strategy 1");
            } catch (Exception e1) {
                System.out.println("Strategy 1 failed, trying Strategy 2...");
            }
            
            // Strategy 2: Find by text
            if (!clicked) {
                try {
                    WebElement addButton = driver.findElement(
                        By.xpath("//android.widget.TextView[@text='ADD TO CART']")
                    );
                    addButton.click();
                    clicked = true;
                    System.out.println("Added to cart using Strategy 2");
                } catch (Exception e2) {
                    System.out.println("Strategy 2 failed, trying Strategy 3...");
                }
            }
            
            // Strategy 3: Using UiAutomator
            if (!clicked) {
                try {
                    driver.findElement(AppiumBy.androidUIAutomator(
                        "new UiSelector().text(\"ADD TO CART\")"
                    )).click();
                    clicked = true;
                    System.out.println("Added to cart using Strategy 3");
                } catch (Exception e3) {
                    System.out.println("Strategy 3 failed");
                    throw new RuntimeException("Could not add product to cart: " + productName);
                }
            }
            
            Thread.sleep(1000);
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while adding product to cart", e);
        }
    }
    
    private void scrollToProduct(String productName) {
        try {
            driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(" +
                "new UiSelector().textContains(\"" + productName + "\"))"
            ));
        } catch (Exception e) {
            System.out.println("Product already visible or scroll not needed");
        }
    }
    
    public void openCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartIcon));
        cartIcon.click();
    }
    
    public int getCartItemCount() {
        try {
            String count = cartBadge.getText();
            return Integer.parseInt(count);
        } catch (Exception e) {
            return 0;
        }
    }
}