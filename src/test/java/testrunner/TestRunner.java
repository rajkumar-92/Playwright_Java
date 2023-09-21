package testrunner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
		// Rerun failed tests from rerun.txt file

		monochrome = true,
	plugin = {
			   "pretty",
		       "html:target/cucumber-html-report.html"
//
////            "json:target/cucumber-json-report/cucumber-report.json",
////
////            "timeline:target/cucumber-timeline-report",
//
    },
		
		features = { "cucumber/" },
		glue = {"classpath:"}
					)

public class TestRunner {
}