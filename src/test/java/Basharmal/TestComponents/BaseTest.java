package Basharmal.TestComponents;

import Basharmal.pageObjects.LoginPage;
import com.aventstack.extentreports.ExtentReports;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class BaseTest {
    //    public Field driver;
    public WebDriver driver;
    public LoginPage loginPage;
    public ExtentReports extent;

    public WebDriver intialSetup() throws IOException {
   //this is for githubHook test
        Properties prop = new Properties();
        FileInputStream input = new FileInputStream(System.getProperty("user.dir") + "//src//main//java//Basharmal//resources//GlobalData.properties");
        prop.load(input);
        String browser =System.getProperty("browser")==null? prop.getProperty("browser"):System.getProperty("browser");
        if (browser.contains("chrome")) {
            ChromeOptions options = new ChromeOptions();
            WebDriverManager.chromedriver().setup();
            if(browser.contains("headless")) {
            options.addArguments("headless");
            }

            driver = new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("edge")) {

            try {
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
            } catch (Exception e) {
                System.setProperty("webdriver.edge.driver", "C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedgedriver.exe");
                driver = new EdgeDriver();
            }
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        System.out.println("Browser selected: " + browser);
        return driver;
    }

    //read data from json file
    public List<HashMap<String, Object>> getDataFromJson(String filePath) throws IOException {
        String jsonData = FileUtils.readFileToString(new File(filePath), "UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        List<HashMap<String, Object>> data = objectMapper.readValue(jsonData, new TypeReference<List<HashMap<String, Object>>>() {
        });
        return data;
    }

    public String getScreenshoot(String testCaseFileName, WebDriver driver) throws IOException {
        String screenshotDir = System.getProperty("user.dir") + "//reports//screenshots//";
        File screenshotFolder = new File(screenshotDir);
        if (!screenshotFolder.exists()) {
            screenshotFolder.mkdirs(); // Create folder if it doesn't exist
        }

        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String screenshotPath = screenshotDir + testCaseFileName + ".png";
        File destination = new File(screenshotPath);
        FileUtils.copyFile(src, destination);

        // Return relative path for embedding in HTML
        return "screenshots/" + testCaseFileName + ".png";

    }

    @BeforeMethod(alwaysRun = true)
    public void launchApplication() throws IOException {
//        extent.createTest("Initial Demo");
        driver = intialSetup();
        loginPage = new LoginPage(driver);
        loginPage.goTo(driver);
//        extent.flush();
    }

    @AfterMethod(alwaysRun = true)
    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
        } else {
            System.out.println("Driver was null — browser may have failed to launch.");
        }
    }

}
