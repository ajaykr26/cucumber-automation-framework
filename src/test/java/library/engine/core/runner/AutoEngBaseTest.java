package library.engine.core.runner;

import io.cucumber.testng.CucumberOptions;
import library.cucumber.selenium.BaseTest;

@CucumberOptions(plugin = {"library.engine.core.runner.AutoEngFormatter",
        "io.qameta.allure.cucumber4jvm.AllureCucumber4Jvm",
        "json:target/cucumber-reports/runReport.json",
        "rerun:failed-scenario/failed-scenario.txt"},
        features = {"classpath:features"})
public class AutoEngBaseTest extends BaseTest {

}
