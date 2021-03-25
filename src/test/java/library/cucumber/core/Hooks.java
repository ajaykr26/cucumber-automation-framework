package library.cucumber.core;

import io.cucumber.core.api.Scenario;
import io.cucumber.java8.En;
import library.common.*;
import library.reporting.Reporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;


public class Hooks implements En {

    protected static Logger logger = LogManager.getLogger(Hooks.class);

    public Hooks() {
        Before(10, (Scenario scenario) -> {
            String scenarioName = scenario.getName();
            String dataFileJSON = Constants.FEATURE_PATH + TestContext.getInstance().testdataGet("fw.featureName") + ".json";
//            String dataFileExcel = CukesConstants.TESTDATA_PATH + TestContext.getInstance().testdataGet("fw.featureName") + ".xlsx";
            TestContext.getInstance().testdataPut("fw.cucumberTest", "true");
            TestContext.getInstance().testdataPut("fw.testDescription", String.format("%s-%s",
                    TestContext.getInstance().testdataGet("fw.featureName"),
                    TestContext.getInstance().testdataGet("fw.scenarioName")));
            Map<String, String> jsonDataTable = JSONHelper.getJSONToMap(JSONHelper.getJSONObject(dataFileJSON, scenarioName));
            Map<String, Object> excelDataTable = ExcelHelper.getDataAsMap(Constants.TESTDATA_EXCEL_PATH, TestContext.getInstance().testdataGet("fw.featureName").toString()).get(scenarioName);
            TestContext.getInstance().testdata().putAll(jsonDataTable);
            TestContext.getInstance().testdata().putAll(excelDataTable);
            TestContext.getInstance().propData().putAll(Property.getPropertiesAsMap(Constants.RUNTIME_PATH));
        });
        After(20, (Scenario scenario) -> {
            logger.info(Formatter.getDataDictionaryAsFormattedTable());
            logger.info(Formatter.getMapAsFormattedTable(TestContext.getInstance().propData()));
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
