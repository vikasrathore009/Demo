import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
strict = true,
features = {"C:/code_auto/Demo/src/test/resources/features/FB_Demo_Assignment.feature"},
plugin = {"json:C:/code_auto/Demo/target/1.json"},
monochrome = false,
tags = {},
glue = {"com.automation.stepdefinitions"})
public class Parallel02IT {
}