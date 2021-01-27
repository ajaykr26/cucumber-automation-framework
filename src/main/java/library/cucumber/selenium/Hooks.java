package library.cucumber.selenium;

import io.cucumber.java8.En;
import io.cucumber.core.api.Scenario;
import library.selenium.exec.driver.factory.DriverContext;
import library.selenium.exec.driver.factory.DriverFactory;

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
                        DriverFactory.getInstance().quit();
                } else {
                    DriverFactory.getInstance().quit();
                }
                DriverFactory.getInstance().driverManager().updateResults(scenario.isFailed() ? "failed" : "passed");
            }
        });
    }

    private void setRuntimeProperties() {

    }

    private void takeScrenShotOnFailure() {

    }
}