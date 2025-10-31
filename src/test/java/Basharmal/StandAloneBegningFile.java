package Basharmal;

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

public class StandAloneBegningFile {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver= intialSetup();
        String userEmail ="usman.basharmal123@gmail.com";
        String userPassword ="R@hulshetty.123";
        driver.get("https://rahulshettyacademy.com/client");
        driver.findElement(By.id("userEmail")).sendKeys(userEmail);
        driver.findElement(By.id("userPassword")).sendKeys(userPassword);
        driver.findElement(By.id("login")).click();
//        LoginPage loginPage = new LoginPage(driver);
//        loginPage.goTo(driver);
//        loginPage.loginAs(userEmail,userPassword);
        //add Items to card from array
        List<String> itemsAddToCart = Arrays.asList("ADIDAS ORIGINAL", "ZARA COAT 3", "iphone 13 pro");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
//        ProductCatalogue productCatalogue = new ProductCatalogue(driver);
//        List<WebElement>products = productCatalogue.getProducts();

        List<WebElement>products =driver.findElements(By.cssSelector("div.card-body"));
        itemsAddToCart.forEach(item -> {
            products.stream()
                    .filter(product -> {
                        WebElement productName=product.findElement(By.tagName("h5"));
                        return productName.getText().equalsIgnoreCase(item);
                    })
                    .findFirst()
                    .ifPresent(product -> {
                        product.findElement(By.xpath(".//button[text()=' Add To Cart']")).click();
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
                        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));

                         System.out.println("Item added to cart successfully " + item);

                    });
        });


//        for(String item:itemsAddToCart){
//            for(WebElement product:products){
//                WebElement productName=product.findElement(By.tagName("h5"));
//                if(productName.getText().equalsIgnoreCase(item)){
//                    product.findElement(By.xpath(".//button[text()=' Add To Cart']")).click();
//                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
//                    wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
//                    System.out.println("Item added to cart successfully "+ item);
//                    break;
//
//                }
//            }
//        }
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));

        driver.findElement(By.cssSelector("button[routerlink*='cart']")).click();
        //get the card products and apply assertion

        List<WebElement>productsAddedToCart = driver.findElements(By.cssSelector(".cartSection h3"));
        System.out.println("Items added to cart successfully " + productsAddedToCart.size());

        Boolean result = itemsAddToCart.stream().
                allMatch(item -> productsAddedToCart.stream().
                        anyMatch(product -> product.getText().equalsIgnoreCase(item)));

        Assert.assertTrue(result);
//        Thread.sleep(5000);
        WebElement btnCheckout = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@class='totalRow']//button")));
      btnCheckout.click();
      Actions actions = new Actions(driver);
      actions.sendKeys(driver.findElement(By.cssSelector("input[placeholder='Select Country']")),"India").perform();
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
//
      actions.click(driver.findElement(By.cssSelector(".ta-item:nth-of-type(2)"))).perform();
      actions.click(driver.findElement(By.className("action__submit"))).perform();
       String confirmationText= driver.findElement(By.cssSelector("h1.hero-primary")).getText();
      Assert.assertTrue(confirmationText.equalsIgnoreCase("Thankyou for the order."));



        //check out

    }
    public static WebDriver intialSetup(){
        WebDriverManager.firefoxdriver().setup();
        WebDriver driver= new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        return driver;
    }
}
