package library.engine.web.steps;

import io.cucumber.java.en.Given;
import library.common.TestContext;
import library.engine.web.AutoEngBaseWebSteps;
import library.reporting.Reporter;
import library.selenium.exec.driver.factory.DriverContext;

import static library.engine.core.AutoEngCoreConstants.SELENIUM;

public class AutoEngWebLaunch extends AutoEngBaseWebSteps {

    @Given("^the user launches the \"([^\"]*)\" application in new window$")
    public void launchApplication(String applicationName) {
        TestContext.getInstance().setActiveWindowType(SELENIUM);
        applicationName = parseValue(applicationName);
        getDriver().navigate().to(applicationName);
        Reporter.addStepLog(String.format("application \"%s\" launched in new window", applicationName));

    }

    @Given("^the user closes the \"([^\"]*)\" application$")
    public void closeApplication(String applicationName) {
        DriverContext.getInstance().quit();
    }

    @Given("^the user launches the \"([^\"]*)\" application in a \"([^\"]*)\"$")
    public void launchApplicationInBrowser(String applicationName, String location) {
        TestContext.getInstance().setActiveWindowType(SELENIUM);
        applicationName = parseValue(applicationName);
        getDriver().navigate().to(applicationName);
        Reporter.addStepLog(String.format("application \"%s\" launched in new window", applicationName));

    }

    @Given("^the user closes the \"([^\"]*)\" application in \"([^\"]*)\"$")
    public void closeApplicationInBrowser(String applicationName, String location) {
        DriverContext.getInstance().quit();
    }

}
