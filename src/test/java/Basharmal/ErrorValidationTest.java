package Basharmal;

import Basharmal.TestComponents.BaseTest;
import Basharmal.TestComponents.Retry;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ErrorValidationTest extends BaseTest {
    @Test(groups = {"ErrorHandling"},retryAnalyzer= Retry.class)
    public void LoginErrorValidationTest() {

        String userEmail = "usman.basharmal4123@gmail.com";
        String userPassword = "R@hulshetty4.123";
        loginPage.loginAs(userEmail, userPassword);
//        String error =loginPage.errorValidation();
//        System.out.println(loginPage.errorValidation());
        Assert.assertEquals(loginPage.errorValidation(), "Incorrect email or password.");

    }

//    public void productTest() {
//        String userEmail ="usman.basharmal123@gmail.com";
//        String userPassword ="R@hulshetty.123";
//        loginPage.loginAs(userEmail,userPassword);
//        String error =loginPage.errorValidation();
//        Assert.assertEquals(error, "incorrect username and password");
//
//
//    }

}
