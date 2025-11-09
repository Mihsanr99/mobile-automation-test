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

public class CartPage {
    
    private AppiumDriver driver;
    private WebDriverWait wait;
    
    public CartPage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
    
    public void clickCheckout() {
    try {
        Thread.sleep(2000);
        
        // Scroll down first - maybe button is below
        try {
            driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true)).scrollForward()"
            ));
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Scroll not needed or failed");
        }
        
        boolean clicked = false;
        
        // Strategy 1: Scroll to CHECKOUT text
        try {
            driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().textContains(\"CHECKOUT\"))"
            )).click();
            clicked = true;
            System.out.println("Checkout clicked using Strategy 1 (scroll)");
        } catch (Exception e1) {
            System.out.println("Strategy 1 failed, trying Strategy 2...");
        }
        
        // Strategy 2: Find all TextView and click any that contains CHECKOUT
        if (!clicked) {
            try {
                driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiSelector().textContains(\"CHECKOUT\")"
                )).click();
                clicked = true;
                System.out.println("Checkout clicked using Strategy 2");
            } catch (Exception e2) {
                System.out.println("Strategy 2 failed, trying Strategy 3...");
            }
        }
        
        // Strategy 3: Try with different case
        if (!clicked) {
            try {
                driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiSelector().text(\"Checkout\")"
                )).click();
                clicked = true;
                System.out.println("Checkout clicked using Strategy 3");
            } catch (Exception e3) {
                System.out.println("Strategy 3 failed, trying Strategy 4...");
            }
        }
        
        // Strategy 4: Get page source and print (for debugging)
        if (!clicked) {
            System.out.println("=== CART PAGE SOURCE ===");
            String pageSource = driver.getPageSource();
            // Print only lines containing "checkout" or "CHECKOUT"
            for (String line : pageSource.split("\n")) {
                if (line.toLowerCase().contains("checkout")) {
                    System.out.println(line.trim());
                }
            }
            System.out.println("=== END PAGE SOURCE ===");
            throw new RuntimeException("CHECKOUT button not found after all strategies");
        }
        
        Thread.sleep(1000);
        
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
    
    public void fillCheckoutInfo(String firstName, String lastName, String zipCode) {
        try {
            Thread.sleep(1000);
            
            // First name
            driver.findElement(By.xpath("//android.widget.EditText[@content-desc='test-First Name']")).sendKeys(firstName);
            
            // Last name
            driver.findElement(By.xpath("//android.widget.EditText[@content-desc='test-Last Name']")).sendKeys(lastName);
            
            // Zip code
            driver.findElement(By.xpath("//android.widget.EditText[@content-desc='test-Zip/Postal Code']")).sendKeys(zipCode);
            
            System.out.println("Checkout info filled");
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    public void clickContinue() {
        try {
            Thread.sleep(1000);
            driver.findElement(By.xpath("//android.widget.TextView[@text='CONTINUE']")).click();
            System.out.println("Continue clicked");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    public void clickFinish() {
    try {
        Thread.sleep(2000);
        
        // Scroll first
        try {
            driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true)).scrollForward()"
            ));
        } catch (Exception e) {
            System.out.println("Scroll not needed");
        }
        
        // Try multiple strategies
        boolean clicked = false;
        
        // Strategy 1: Scroll into view
        try {
            driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().textContains(\"FINISH\"))"
            )).click();
            clicked = true;
            System.out.println("Finish clicked using Strategy 1");
        } catch (Exception e1) {
            System.out.println("Strategy 1 failed, trying Strategy 2...");
        }
        
        // Strategy 2: Direct find
        if (!clicked) {
            try {
                driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiSelector().text(\"FINISH\")"
                )).click();
                clicked = true;
                System.out.println("Finish clicked using Strategy 2");
            } catch (Exception e2) {
                System.out.println("Strategy 2 failed, trying Strategy 3...");
            }
        }
        
        // Strategy 3: Contains
        if (!clicked) {
            driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().textContains(\"FINISH\")"
            )).click();
            System.out.println("Finish clicked using Strategy 3");
        }
        
        Thread.sleep(2000);
        
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
    
    public boolean isOrderConfirmed() {
        try {
            Thread.sleep(2000);
            WebElement confirmation = driver.findElement(
                By.xpath("//android.widget.TextView[@text='THANK YOU FOR YOU ORDER']")
            );
            return confirmation.isDisplayed();
        } catch (Exception e) {
            System.out.println("Order confirmation not found");
            return false;
        }
    }
    
    public void completeCheckout(String firstName, String lastName, String zipCode) {
        clickCheckout();
        fillCheckoutInfo(firstName, lastName, zipCode);
        clickContinue();
        clickFinish();
    }
}