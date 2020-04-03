package library.cucumber.common;

import library.common.Constants;
import library.common.FileHelper;
import library.common.TestContext;
import library.reporting.Reporter;
import library.selenium.exection.driver.factory.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import io.cucumber.core.api.Scenario;
import io.cucumber.java8.En;

public class Hooks implements En {

    protected static Logger logger = LogManager.getLogger(Hooks.class);

    public Hooks() {
        Before(10, (Scenario scenario) -> {
            String scenarioName = scenario.getName();
//            String featureName = scenario.getId().split(";")[0].replace("-", " ");
            String featureName = scenario.getId().split("/")[1].split(":")[0];
            String dataFile = Constants.FEATURE_PATH + featureName.replace(".feature", ".json");
            TestContext.getInstance().testdataPut("fw.cucumberTest", "true");
            TestContext.getInstance().testdataPut("fw.testDescription", featureName+ "-" + scenarioName);
            Map<String, String> dataTable = FileHelper.getJSONToMap(FileHelper.getJSONObject(dataFile, scenarioName));
            TestContext.getInstance().testdata().putAll(dataTable);
        });
        After(20, (Scenario scenario) -> {
            checkForSoftAssertFailure();

        });
    }

    private void checkForSoftAssertFailure() {
        try {
            TestContext.getInstance().softAssertions().assertAll();
        } catch (AssertionError assertionError) {
            Reporter.failScenario(assertionError.fillInStackTrace());
            logger.error(assertionError.getMessage());
            TestContext.getInstance().resetSoftAssert();
            throw assertionError;
        }
    }
}
