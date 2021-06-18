package runners;

import io.cucumber.testng.CucumberOptions;
import library.cucumber.selenium.BaseTest;

@CucumberOptions(plugin = {"library.engine.core.runner.AutoEngFormatter",
        "io.qameta.allure.cucumber4jvm.AllureCucumber4Jvm",
        "json:target/cucumber-reports/runReport.json"},
        features = {"@failed-scenario/failed-scenario.txt"})
public class AutoEngBaseTestReRun extends BaseTest {

}
