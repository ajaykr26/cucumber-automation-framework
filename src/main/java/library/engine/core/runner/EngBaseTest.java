package library.engine.core.runner;

import io.cucumber.testng.CucumberOptions;
import library.cucumber.selenium.BaseTest;

@CucumberOptions(
        plugin = {"json:target/cucumber-reports/runReport.json",
                "library.engine.core.runner.EngFormatter",
                "io.qameta.allure.cucumber4jvm.AllureCucumber4Jvm"},
        features = "classpath:features")
public class EngBaseTest extends BaseTest {

}
