package runners;

import io.cucumber.testng.CucumberOptions;
import library.engine.core.runner.AutoEngBaseTest;

@CucumberOptions(tags = {"@SmokeTest", "not @ignore"})
public class Regression extends AutoEngBaseTest {
}