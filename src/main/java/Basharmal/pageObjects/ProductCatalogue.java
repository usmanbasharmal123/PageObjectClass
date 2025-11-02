package Basharmal.pageObjects;

import Basharmal.abstractcomponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ProductCatalogue extends AbstractComponent {
    WebDriver driver;

    public ProductCatalogue(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Page Elements
    @FindBy(css = "div.card-body")
    private List<WebElement> products;
    @FindBy(css = "[class*='.ng-animating']")
    private WebElement ngAnimating;


    By productsLocator = By.cssSelector("div.card-body");
    By toastContainer = By.cssSelector("#toast-container");
    By btnAddToCart = By.xpath(".//button[text()=' Add To Cart']");
    By tagName = By.tagName("h5");

    //page Action
    public List<WebElement> getProducts() {
        waitForElementToAppear(productsLocator);
        return products;
    }

    public void addItemToCart(List<String> itemsAddToCart) {
        itemsAddToCart.forEach(item -> {
            getProducts().stream()
                    .filter(product -> {
                        WebElement productName = product.findElement(tagName);
                        return productName.getText().equalsIgnoreCase(item);
                    })
                    .findFirst()
                    .ifPresent(product -> {
                        product.findElement(btnAddToCart).click();
                        waitForElementToAppear(toastContainer);
                        waitForElementToDisappear(ngAnimating);
                        System.out.println("Item added to cart successfully " + item);

                    });
        });

    }
}