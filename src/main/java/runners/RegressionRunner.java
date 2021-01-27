package runners;

import io.cucumber.testng.CucumberOptions;
import library.cucumber.selenium.BaseTest;

@CucumberOptions(tags = {"@SmokeSuite"})
public class RegressionRunner extends BaseTest {
}