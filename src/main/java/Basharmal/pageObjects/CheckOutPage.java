package Basharmal.pageObjects;

import Basharmal.abstractcomponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.Optional;

public class CheckOutPage extends AbstractComponent {
    WebDriver driver;

    public CheckOutPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "input[placeholder='Select Country']")
    private WebElement selectCountry;
    @FindBy(className = "action__submit")
    private WebElement btnSubmit;
    @FindBy(css = "h1.hero-primary")
    private WebElement confirmationText;

    public void selectCountry(String country) {
        Actions actions = new Actions(driver);
        actions.sendKeys(selectCountry, country).perform();
// Wait for the dropdown to appear
        By countryDropdownContainer = By.cssSelector(".ta-results");
        waitForElementToAppear(countryDropdownContainer);
        // Get all the country suggestions
        By countrySuggestions = By.cssSelector(".ta-item");
        List<WebElement> results = driver.findElements(countrySuggestions);
        results.stream().filter(element -> element.getText().equals(country))
                .findFirst().ifPresentOrElse(WebElement::click, () -> {
                    throw new RuntimeException("Country not found in dropdown: " + country);
                });


//    boolean countryFound = false;
//    for (WebElement result : results) {
//        if (result.getText().equalsIgnoreCase(country)) {
//            result.click();
//            countryFound = true;
//            break;
//        }
//
//    }
//    results.stream().filter(result->{
//        return result.getText().equalsIgnoreCase(country);
//    }).findFirst().ifPresent(result->{
//        result.click();
//        countryFound=true;
//    });
//    if (!countryFound) {
//        throw new RuntimeException("Country not found in dropdown: " + country);
//    }

        actions.click(btnSubmit).perform();
    }

    public String getConfirmationText() {
        return confirmationText.getText();
    }

}

