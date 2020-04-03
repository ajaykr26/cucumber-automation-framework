package runners;

import io.cucumber.testng.CucumberOptions;
import library.cucumber.common.CucumberBaseTest;

//@CucumberOptions(tags = {"@Scenario1 or @Scenario2", "not @ignore"})
@CucumberOptions(tags = {"@Scenario1"})
public class TestRunner extends CucumberBaseTest {
}