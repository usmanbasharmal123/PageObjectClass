package Basharmal;

import Basharmal.TestComponents.BaseTest;
import Basharmal.pageObjects.CartSectionPage;
import Basharmal.pageObjects.CheckOutPage;
import Basharmal.pageObjects.OrderSectionPage;
import Basharmal.pageObjects.ProductCatalogue;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class StandAloneTest extends BaseTest {
    String userEmail = "usman.basharmal123@gmail.com";
    String userPassword = "R@hulshetty.123";
    String userEmail2 = "walwalahsherzai@gmail.com";
    String userPassword2 = "walwalah@S123";
    //this is for githubHook test new
    List<String> itemsAddToCart = Arrays.asList("ADIDAS ORIGINAL", "ZARA COAT 3", "iphone 13 pro");

    //    List<String> itemsAddToCart = Arrays.asList("ADIDAS ORIGINAL", "ZARA COAT 3", "iphone 13 pro");
    @Test(dataProvider = "getData", groups = {"Purchase"})
//    @Test()
    public void submitOrder(HashMap<String, Object> map) {
//    public void submitOrder() throws IOException {

        ProductCatalogue productCatalogue = loginPage.loginAs((String) map.get("userEmail"), (String) map.get("userPassword"));
        productCatalogue.addItemToCart((List<String>) map.get("itemsAddToCart"));
        CartSectionPage cartSectionPage = productCatalogue.goToCartPage();
        Boolean result = cartSectionPage.checkItemAvailability(itemsAddToCart);//
        Assert.assertTrue(result);
        CheckOutPage chk = cartSectionPage.checkOut();

        chk.selectCountry((String) map.get("country"));
        String confirmationText = chk.getConfirmationText();
        Assert.assertTrue(confirmationText.equalsIgnoreCase("Thankyou for the order."));
//        driver.quit(); // close browser after test

    }

    @Test(dependsOnMethods = {"submitOrder"})
    public void OrderHistoryTest() throws InterruptedException {        //login
//        ProductCatalogue productCatalogue =loginPage.loginAs("usman.basharmal123@gmail.com","R@hulshetty.123");
        ProductCatalogue productCatalogue = loginPage.loginAs(userEmail, userPassword);
//        ProductCatalogue productCatalogue =loginPage.loginAs("walwalahsherzai@gmail.com","walwalah@S123");
        OrderSectionPage orderSectionPage = productCatalogue.goToOrdersPage();
        Assert.assertTrue(orderSectionPage.VerifyOrderDisplay(itemsAddToCart));
        System.out.println("this code is run success ");
    }

    @DataProvider
    public Object[][] getData() throws IOException {
//        C:\Users\Basharmal Safi\IdeaProjects\SeleniumFrameworkDesigne\src\test\java\Basharmal\Data\Data.json";
        String filePath = (System.getProperty("user.dir") + "//src//test//java//Basharmal//Data//Data.json");
        List<HashMap<String, Object>> data = getDataFromJson(filePath);
        return new Object[][]{{data.get(0)}, {data.get(1)}};


    }

}
