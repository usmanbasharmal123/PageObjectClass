package Basharmal.TestComponents;

import Basharmal.resources.ExtentReportNG;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.apache.commons.compress.harmony.pack200.CPNameAndType;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;

public class Listeners extends BaseTest implements ITestListener {
    ExtentTest test;
    ExtentReports extent = ExtentReportNG.getReportObject();
    //this line will avoid overriting one test on another what do you mean is when one is in the process
    //of complation it will make it unique id for another test because if we don't it will overite on another method and we will
    // not know what happen with the prevouse test
    ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
//    WebDriver driver;driver

    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
        extentTest.set(test); //unique thread id (Errorvalidatiotest)
    }

    public void onTestSuccess(ITestResult result) {
        extentTest.get().log(Status.PASS, "Test Passed");
    }

    public void onTestFailure(ITestResult result) {
        extentTest.get().fail(result.getThrowable());
        try {
            driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String path = null;
        try {
            path = getScreenshoot(result.getMethod().getMethodName(), driver);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        extentTest.get().addScreenCaptureFromPath(path, result.getMethod().getMethodName());
        System.out.println("Screen Capture Path: " + path);
    }
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
