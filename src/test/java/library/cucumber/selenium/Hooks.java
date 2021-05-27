package library.cucumber.selenium;

import io.cucumber.java8.En;
import io.cucumber.core.api.Scenario;
import library.common.Constants;
import library.common.Property;
import library.selenium.exec.driver.factory.DriverContext;
import org.apache.commons.configuration2.PropertiesConfiguration;

public class Hooks implements En {

    public Hooks() {
        Before(30, (Scenario scenario) -> {
            setRuntimeProperties();
        });
        After(30, (Scenario scenario) -> {
            if (DriverContext.getInstance().getDriverManager()!= null) {
                if (scenario.isFailed()) {
                    takeScreenShotOnFailure();
                    if (!DriverContext.getInstance().getKeepBrowserOpen())
                        DriverContext.getInstance().quit();
                } else {
                    DriverContext.getInstance().quit();
                }
                DriverContext.getInstance().getDriverManager().updateResults(scenario.isFailed() ? "failed" : "passed");
            }
        });
    }

    private void setRuntimeProperties() {

        PropertiesConfiguration props = Property.getProperties(Constants.RUNTIME_PATH);
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
            String seleniumGridURL = props.getString("seleniumGridURL");
            if (seleniumGridURL != null) {
                System.setProperty("fw.seleniumGrid", seleniumGridURL);
            }
        }
    }


    private void takeScreenShotOnFailure() {


    }
}