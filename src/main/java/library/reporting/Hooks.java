package library.reporting;

import io.cucumber.core.api.Scenario;
import io.cucumber.java8.En;
import library.common.Property;
import library.common.TestContext;

import static library.reporting.Reporter.addScreenCaptureFromPath;

public class Hooks implements En {

    public Hooks() {
        After(10, (Scenario scenario) -> {
            if (scenario.isFailed()) {
                String screenshotOnFailure = Property.getVariable("screenshotOnFailure");
                if (Boolean.parseBoolean(screenshotOnFailure)) {
                    String screenshotPath = (String) TestContext.getInstance().testdataGet("fw.screenshotAbsolutePath");
                    if (screenshotPath != null) {
                        addScreenCaptureFromPath(screenshotPath);
                    }
                }
            }
        });
    }
}
