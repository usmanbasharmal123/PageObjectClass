package Basharmal.AbstructComponents;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AbstructComponent {
WebDriver driver;
public AbstructComponent(WebDriver driver){
this.driver=driver;
    PageFactory.initElements(driver,this);

}
    @FindBy(css = "[routerlink*='cart']")
    WebElement btnCart;
//Initial WebDriver Setup

    public void waitForElementToBeAppeared(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    public void waitForElementInvisibility(WebElement element) {
        new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(6))
                .pollingEvery(Duration.ofMillis(300))
                .ignoring(Exception.class)
                .until(ExpectedConditions.invisibilityOf(element));
    }
    public void waitForElementToBeClickable(WebElement locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
       wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    //click the cart button to check the added Items

    public CartSectionPage clickCartButton() {
       btnCart.click();
       return new CartSectionPage(driver);
    }
}
