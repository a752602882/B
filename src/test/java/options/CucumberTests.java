package options;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(
		plugin = {"pretty"},
		glue = {"stepdefs"},
		features = {"src/test/features"})
public class CucumberTests extends AbstractTestNGCucumberTests {}
