package runners;

import io.cucumber.testng.CucumberOptions;
import library.engine.core.runner.EngBaseTest;

@CucumberOptions(tags = {"@SmokeTest", "not @ignore"})
public class Regression extends EngBaseTest {
}