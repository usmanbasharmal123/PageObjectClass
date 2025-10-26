package Basharmal.AbstructComponents;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends AbstructComponent {
    WebDriver driver;

    public LoginPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Page Elements
    @FindBy(id = "userEmail")
    private WebElement userEmailInput;

    @FindBy(id = "userPassword")
    private WebElement passwordInput;

    @FindBy(id = "login")
    private WebElement loginButton;
    //page Action
    public ProductCatalogue loginAs(String email, String password){
        userEmailInput.clear();
        userEmailInput.sendKeys(email);
        passwordInput.clear();
        passwordInput.sendKeys(password);
        loginButton.click();
        return new ProductCatalogue(driver);
    }
    public void goTo(WebDriver driver){
        driver.get("https://rahulshettyacademy.com/client");
    }
}