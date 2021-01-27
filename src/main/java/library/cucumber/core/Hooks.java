package library.cucumber.core;

import io.cucumber.core.api.Scenario;
import io.cucumber.java8.En;
import library.common.*;
import library.reporting.Reporter;
import library.selenium.exec.ExecConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import org.apache.logging.log4j.ThreadContext;


public class Hooks implements En {

    protected static Logger logger = LogManager.getLogger(Hooks.class);

    public Hooks() {
        Before(10, (Scenario scenario) -> {
            String scenarioName = scenario.getName();
            String dataFile = CukesConstants.FEATURE_PATH + TestContext.getInstance().testdataGet("fw.featureName") + ".json";
            TestContext.getInstance().testdataPut("fw.cucumberTest", "true");
            TestContext.getInstance().testdataPut("fw.testDescription", String.format("%s-%s",
                    TestContext.getInstance().testdataGet("fw.featureName"),
                    TestContext.getInstance().testdataGet("fw.scenarioName")));
            Map<String, String> jsonDataTable = JSONHelper.getJSONToMap(JSONHelper.getJSONObject(dataFile, scenarioName));
//            Map<String, String> excelDataTable = ExcelHelper.getDataAsMapWithoutIndex(dataFile, scenarioName).get(0);
            TestContext.getInstance().testdata().putAll(jsonDataTable);
        });
        After(20, (Scenario scenario) -> {
            logger.info(Formatter.getDataDictionaryAsFormattedTable());
            logger.info(Formatter.getMapAsFormattedTable(Property.getPropertiesAsMap(CukesConstants.RUNTIME_PATH)));
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
