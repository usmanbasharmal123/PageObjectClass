package Basharmal.resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportNG {
    public static ExtentReports getReportObject() {
        String path = System.getProperty("user.dir") + "//reports//LoginTestReport.html";
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(path);
        sparkReporter.config().setReportName("LoginTestReport");
        sparkReporter.config().setDocumentTitle("LoginTestReport title");
        sparkReporter.config().setTheme(Theme.DARK);

        ExtentReports extent = new ExtentReports();
        extent.attachReporter(sparkReporter);


        extent.setSystemInfo("Tester", "Baharmal Safi");
        return extent;
    }
}
