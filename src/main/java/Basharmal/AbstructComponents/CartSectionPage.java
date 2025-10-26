package Basharmal.AbstructComponents;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CartSectionPage extends AbstructComponent {
    WebDriver driver;

    public CartSectionPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }



    // Page Elements
//    List<WebElement> productsAddedToCart = driver.findElements(By.cssSelector(".cartSection h3"));

    @FindBy(css = ".cartSection h3")
    private List<WebElement> productsAddToCart;
    @FindBy(css=".totalRow button")
    WebElement btnCheckout;
//    WebElement btnCheckout;
    //page Action
    public List<WebElement> productsAddedToCart(){
        return productsAddToCart;
    }
    public Boolean checkItemAvailability(List<String> itemsAddToCart) {
        return itemsAddToCart.stream().
                allMatch(item -> productsAddedToCart().stream().
                        anyMatch(product -> product.getText().equalsIgnoreCase(item)));

    }
    public CheckOutPage checkOut(){
        waitForElementToBeClickable(btnCheckout);
        btnCheckout.click();
        return new CheckOutPage(driver);
    }

}