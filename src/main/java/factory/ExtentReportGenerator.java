package factory;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportGenerator {
    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            extent = createInstance();
        }
        return extent;
    }

    private static ExtentReports createInstance() {
        String reportPath = "path/to/extent/report.html";
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(reportPath);

        htmlReporter.config().setTheme(Theme.STANDARD);
        // Additional configuration options can be set here

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        return extent;
    }
}
