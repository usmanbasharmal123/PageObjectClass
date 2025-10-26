package Basharmal.AbstructComponents;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckOutPage extends AbstructComponent {
    WebDriver driver;
    public CheckOutPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    @FindBy(css ="input[placeholder='Select Country']")
    private WebElement selectCountry;
    By taResult =By.cssSelector(".ta-item:nth-of-type(2)");
    @FindBy(css =".ta-item:nth-of-type(2)")
    private WebElement btnFindContry;
    @FindBy(className ="action__submit")
    private WebElement btnSubmit;
    @FindBy(css ="h1.hero-primary")
    private WebElement confirmationText;
    public void selectCountry(String country) {
        Actions actions = new Actions(driver);
        actions.sendKeys(selectCountry,country).perform();
        waitForElementToBeAppeared(taResult);
        actions.click(btnFindContry).perform();
      actions.click(btnSubmit).perform();

    }
    public String getConfirmationText() {
        return confirmationText.getText();
    }

}
