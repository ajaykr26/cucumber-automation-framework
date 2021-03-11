package library.cucumber.selenium;

import io.cucumber.java8.En;
import io.cucumber.core.api.Scenario;
import library.common.Property;
import library.cucumber.core.CukesConstants;
import library.selenium.exec.driver.factory.DriverContext;
import library.selenium.exec.driver.factory.DriverFactory;
import org.apache.commons.configuration2.PropertiesConfiguration;

public class Hooks implements En {

    public Hooks() {
        Before(30, (Scenario scenario) -> {
            setRuntimeProperties();
        });
        After(30, (Scenario scenario) -> {
            if (DriverContext.getInstance().getTechStack() != null) {
                if (scenario.isFailed()) {
                    takeScrenShotOnFailure();
                    if (!DriverContext.getInstance().getKeepBrowserOpen())
                        DriverContext.getInstance().quit();
                } else {
                    DriverContext.getInstance().quit();
                }
                DriverContext.getInstance().getWebDriverManager().updateResults(scenario.isFailed() ? "failed" : "passed");
            }
        });
    }

    private void setRuntimeProperties() {

        PropertiesConfiguration props = Property.getProperties(CukesConstants.RUNTIME_PATH);
        if (props != null) {
            String screenshotOnEveryStep = props.getString("screenshotOnEveryStep");

            if (screenshotOnEveryStep != null) {
                System.setProperty("fw.screenshotOnEveryStep", screenshotOnEveryStep);
            }
            String screenshotOnValidation = props.getString("screenshotOnValidation");
            if (screenshotOnValidation != null) {
                System.setProperty("fw.screenshotOnValidation", screenshotOnValidation);
            }
            String saveAllureResult = props.getString("saveAllureResult");
            if (saveAllureResult != null) {
                System.setProperty("fw.saveAllureResult", saveAllureResult);
            }
            String addScreenshotToWord = props.getString("addScreenshotToWord");
            if (addScreenshotToWord != null) {
                System.setProperty("fw.addScreenshotToWord", addScreenshotToWord);
            }
            String screenshotOnFailure = props.getString("screenshotOnFailure");
            if (screenshotOnFailure != null) {
                System.setProperty("fw.screenshotOnFailure", screenshotOnFailure);
            }
        }
    }


    private void takeScrenShotOnFailure() {


    }
}