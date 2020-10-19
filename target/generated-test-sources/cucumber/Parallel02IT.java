import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
strict = true,
features = {"C:/Users/vikash.rathore/Downloads/jha_api_automation/src/test/resources/features/AdidasUI.feature"},
plugin = {"json:C:/Users/vikash.rathore/Downloads/jha_api_automation/target/2.json"},
monochrome = false,
tags = {},
glue = {"com.automation.stepdefinitions"})
public class Parallel02IT {
}