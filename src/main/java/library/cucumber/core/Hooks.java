package library.cucumber.core;

import io.cucumber.core.api.Scenario;
import io.cucumber.java8.En;
import library.common.*;
import library.common.Constants;
import library.reporting.Reporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import org.apache.logging.log4j.ThreadContext;


public class Hooks implements En {

    protected static Logger logger = LogManager.getLogger(Hooks.class);

    public Hooks() {
        Before(5, (Scenario scenario) -> {
            String scenarioName = scenario.getName();
            String featureFileName = scenario.getId().split("/")[1].split(":")[0].replace("%20", " ");
            ThreadContext.put("logname", String.format("%s/%s", featureFileName.replace(".feature", ""), scenarioName));
            String dataFile = Constants.FEATURE_PATH + featureFileName.replace(".feature", ".json");
            TestContext.getInstance().testdataPut("fw.cucumberTest", "true");
            TestContext.getInstance().testdataPut("fw.testDescription", featureFileName.replace(".feature", scenarioName));
            Map<String, String> dataTable = JSONHelper.getJSONToMap(JSONHelper.getJSONObject(dataFile, scenarioName));
            TestContext.getInstance().testdata().putAll(dataTable);
        });
        After(20, (Scenario scenario) -> {
            logger.info(DataTableFormatter.getDataDictionaryAsFormattedTable());
            logger.info(DataTableFormatter.getMapAsFormattedTable(Property.getPropertiesAsMap(Constants.RUNTIME_PATH)));
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
