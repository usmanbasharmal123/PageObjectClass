package Basharmal.pageObjects;

import Basharmal.AbstructComponents.AbstractComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class OrderSectionPage extends AbstractComponent {
    WebDriver driver;

    public OrderSectionPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
        //this is
    }
    // Page Elements

    @FindBy(css = "tr.ng-star-inserted td:nth-child(3)")
    private List<WebElement> orderList;

    //    WebElement btnCheckout;
    //page Action
    public List<WebElement> orderListTable() {
        return orderList;
    }

    public Boolean VerifyOrderDisplay(List<String> itemsAddToCart) {
        return itemsAddToCart.stream().
                allMatch(item -> orderListTable().stream().
                        anyMatch(product -> product.getText().equalsIgnoreCase(item)));

    }


}