package library.engine.mobile.steps;

import io.cucumber.java.en.Given;
import library.common.TestContext;
import library.engine.mobile.AutoEngMobileBaseSteps;
import library.reporting.Reporter;

import static library.engine.core.AutoEngCoreConstants.MOBILE;
import static library.engine.core.AutoEngCoreParser.parseValue;

public class AutoEngMobileLaunch extends AutoEngMobileBaseSteps {

    @Given("^the user launches \"([^\"]*)\" in mobile browser$")
    public void launchMobileApplication(String applicationName) {
        TestContext.getInstance().setActiveWindowType(MOBILE);
        applicationName = parseValue(applicationName);
        getDriver().navigate().to(applicationName);
        Reporter.addStepLog(String.format("application \"%s\" launched in new window", applicationName));
    }

}
