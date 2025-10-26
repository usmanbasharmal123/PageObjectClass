package Basharmal.AbstructComponents;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class StandAlonePOC {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver= intialSetup();
        String userEmail ="usman.basharmal123@gmail.com";
        String userPassword ="R@hulshetty.123";
        LoginPage loginPage = new LoginPage(driver);
        loginPage.goTo(driver);
        ProductCatalogue productCatalogue =loginPage.loginAs(userEmail,userPassword);
        List<String> itemsAddToCart = Arrays.asList("ADIDAS ORIGINAL", "ZARA COAT 3", "iphone 13 pro");
        productCatalogue.addItemToCart(itemsAddToCart);
        CartSectionPage cartSectionPage = productCatalogue.clickCartButton();
        Boolean result = cartSectionPage.checkItemAvailability(itemsAddToCart);//
        Assert.assertTrue(result);
        CheckOutPage chk = cartSectionPage.checkOut();
        String country = "India";
        chk.selectCountry(country);
       String confirmationText= chk.getConfirmationText();
      Assert.assertTrue(confirmationText.equalsIgnoreCase("Thankyou for the order."));

    }
    public static WebDriver intialSetup(){
        WebDriverManager.firefoxdriver().setup();
        WebDriver driver= new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        return driver;
    }
}
