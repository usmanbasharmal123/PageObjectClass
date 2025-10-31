package Basharmal;

import Basharmal.TestComponents.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProductErrorValidationTest extends BaseTest {
    @Test
    public void LoginErrorValidationTest() {
        String userEmail ="usman.basharmal4123@gmail.com";
        String userPassword ="R@hulshetty4.123";
        loginPage.loginAs(userEmail,userPassword);
        String error =loginPage.errorValidation();
        Assert.assertEquals(error, "incorrect username and password");


    }

}
