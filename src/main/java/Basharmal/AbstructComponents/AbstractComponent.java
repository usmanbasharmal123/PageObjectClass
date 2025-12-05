package Basharmal.AbstructComponents;

import Basharmal.pageObjects.CartSectionPage;
import Basharmal.pageObjects.OrderSectionPage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * AbstractComponent provides reusable UI interaction methods
 * such as waits and navigation to common sections like Cart and Orders.
 */
public class AbstractComponent {

    protected WebDriver driver;

    @FindBy(css = "[routerlink*='cart']")
    private WebElement cartButton;

    @FindBy(css = "[routerlink*='myorders']")
    private WebElement ordersButton;

    // Spinner locator using partial class match for dynamic overlays
    private final By spinnerLocator = By.cssSelector("[class*='ngx-spinner-overlay']");

    public AbstractComponent(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Waits for an element located by a By locator to be visible.
     */
    public void waitForElementToAppear(By locator) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitForElementToAppearByElement(WebElement locator) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOf(locator));
    }

    /**
     * Waits for a WebElement to become invisible.
     */
    public void waitForElementToDisappear(WebElement element) {
        new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(2))
                .ignoring(NoSuchElementException.class)
                .until(ExpectedConditions.invisibilityOf(element));
    }

    /**
     * Waits for an element located by a By locator to become invisible.
     */
    public void waitForElementToDisappear(By locator) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Waits for a WebElement to become clickable.
     */
    public void waitForElementToBeClickable(WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Performs a safe click by waiting for spinner to disappear and element to be clickable.
     * Falls back to JavaScript click if intercepted.
     */
    public void safeClick(WebElement element, By spinnerLocator) {
        try {
            waitForElementToDisappear(spinnerLocator);
            waitForElementToBeClickable(element);
            element.click();
        } catch (ElementClickInterceptedException e) {
            System.out.println("Click failed due to overlay. Retrying with JS click.");
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    /**
     * Navigates to the Cart section.
     *
     * @return CartSectionPage object
     */
    public CartSectionPage goToCartPage() {
        safeClick(cartButton, spinnerLocator);
        return new CartSectionPage(driver);
    }

    /**
     * Navigates to the Orders section.
     *
     * @return OrderSectionPage object
     */
    public OrderSectionPage goToOrdersPage() {
        safeClick(ordersButton, spinnerLocator);
        return new OrderSectionPage(driver);
    }
}
