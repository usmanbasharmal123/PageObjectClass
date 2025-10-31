package Basharmal.TestComponents;

import Basharmal.pageObjects.LoginPage;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class BaseTest {
    public WebDriver driver;
    public LoginPage loginPage;
    public WebDriver intialSetup() throws IOException {
        Properties prop = new Properties();
        FileInputStream input = new FileInputStream(System.getProperty("user.dir")+"//src//main//java//Basharmal//resources//GlobalData.properties");
        prop.load(input);
        String browser = prop.getProperty("browser");
        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }
        else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver= new FirefoxDriver();
        }
        else if (browser.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            driver= new EdgeDriver();
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        return driver;
    }
    //read data from json file
    public List<HashMap<String, Object>> getDataFromJson(String filePath) throws IOException {
        String jsonData = FileUtils.readFileToString(new File(filePath), "UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        List<HashMap<String,Object>> data=objectMapper.readValue(jsonData, new TypeReference<List<HashMap<String, Object>>>() {
        });
        return data;
    }
    @BeforeMethod(alwaysRun = true)
    public void launchApplication() throws IOException {
        driver = intialSetup();
        loginPage = new LoginPage(driver);
        loginPage.goTo(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void closeBrowser() {
//        if (driver != null) {
            driver.quit();
//            System.out.println("Browser closed.");
//        }
    }

}
