package Hook;

 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Tracing;
import factory.DriverFactory;
import factory.ExtentReportGenerator;
import flightonly.pom.FlightOnlyPageNavigation;
import flightonly.pom.HomePage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Tracing;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


 

public class Hook {
    public DriverFactory driverFactory;
    public Page page;

    private FlightOnlyPageNavigation flightOnlyPageNavigation;
    private HomePage homePage;
    
    private static ExtentReports extentReports;
    private static ExtentTest feature;
    private static ExtentTest scenario;
    private static ExtentTest step;

    @Before
    public void launchBrowser() {
//        String browserName = WebActions.getProperty("browser");  //Fetching browser value from config file
    	extentReports = ExtentReportGenerator.getInstance();
    	driverFactory = new DriverFactory();
        page = driverFactory.initDriver(); // Passing browser name to launch the browser

        // Create an instance of FlightOnlyPageNavigation
        flightOnlyPageNavigation = new FlightOnlyPageNavigation(page);

        // Create an instance of HomePage
        homePage = new HomePage(page);
    }

 

    //After runs in reverse order so order=1 will run first
    @After(order = 0)
    public void quitBrowser() {
        page.close();
        driverFactory.closeContext();
    }

 

    @After(order = 1)
    public void takeScreenshotAndTrace(Scenario scenario) {
        if (scenario.isFailed()) {
            String screenshotName = scenario.getName().replaceAll("", "_"); //Replace all space in scenario name with underscore
            byte[] sourcePath = page.screenshot();
            scenario.attach(sourcePath, "image/png", screenshotName);  //Attach screenshot to report if scenario fails
            DriverFactory.context.tracing().stop(new Tracing.StopOptions().setPath(Paths.get("target/" + screenshotName + ".zip")));
        }
    }

    @After(order = 2)
	public void tearDown(Scenario scenario) {
	    if (scenario.isFailed()) {
	        ExtentTest step = extentReports.createTest("Step", "Step"); // Create a new ExtentTest with a dummy keyword and name
	        step.log(Status.FAIL, "Step failed"); // Log the failed step

	        // Attach a screenshot if available
	        if (scenario instanceof io.cucumber.java.Scenario) {
	            io.cucumber.java.Scenario cucumberScenario = (io.cucumber.java.Scenario) scenario;
	            if (cucumberScenario.isFailed()) {
	                String screenshotPath = "F://playwrightAutomation.png"; // Path to the screenshot captured during the test
	                Path screenshot = Paths.get(screenshotPath);
	                if (screenshot.toFile().exists()) {
	                    try {
	                        step.addScreenCaptureFromPath(screenshotPath);
	                    } catch (IOException e) {
	                        // Handle the exception (e.g., log an error message or throw a custom exception)
	                        e.printStackTrace();
	                    }
	                }
	            }
	        }
	    }

	    extentReports.flush(); // Flush the report at the end of each scenario
	}


}