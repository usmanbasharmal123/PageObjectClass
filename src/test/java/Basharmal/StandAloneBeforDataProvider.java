package Basharmal;

import Basharmal.TestComponents.BaseTest;
import Basharmal.pageObjects.CartSectionPage;
import Basharmal.pageObjects.CheckOutPage;
import Basharmal.pageObjects.OrderSectionPage;
import Basharmal.pageObjects.ProductCatalogue;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class StandAloneBeforDataProvider extends BaseTest {
    String userEmail ="usman.basharmal123@gmail.com";
    String userPassword ="R@hulshetty.123";
    List<String> itemsAddToCart = Arrays.asList("ADIDAS ORIGINAL", "ZARA COAT 3", "iphone 13 pro");
    @Test
    public void submitOrder() {
        ProductCatalogue productCatalogue =loginPage.loginAs(userEmail,userPassword);
        productCatalogue.addItemToCart(itemsAddToCart);
        CartSectionPage cartSectionPage = productCatalogue.goToCartPage();
        Boolean result = cartSectionPage.checkItemAvailability(itemsAddToCart);//
        Assert.assertTrue(result);
        CheckOutPage chk = cartSectionPage.checkOut();
        String country = "India";
        chk.selectCountry(country);
       String confirmationText= chk.getConfirmationText();
      Assert.assertTrue(confirmationText.equalsIgnoreCase("Thankyou for the order."));

    }
    @Test(dependsOnMethods = {"submitOrder"})
    public void OrderHistoryTest() throws InterruptedException {
        //login
        ProductCatalogue productCatalogue =loginPage.loginAs(userEmail,userPassword);
        OrderSectionPage orderSectionPage=productCatalogue.goToOrdersPage();
        Assert.assertTrue(orderSectionPage.VerifyOrderDisplay(itemsAddToCart));
    }

}
