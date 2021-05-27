package library.engine.mobile.steps;

import io.cucumber.java.en.Given;
import library.common.TestContext;
import library.engine.mobile.AutoEngBaseMobileSteps;
import library.reporting.Reporter;

import static library.engine.core.AutoEngCoreConstants.MOBILE;

public class AutoEngMobileLaunch extends AutoEngBaseMobileSteps {

    @Given("^the user launch the mobile application \"([^\"]*)\"$")
    public void launchMobileApplication(String applicationName) {
        TestContext.getInstance().setActiveWindowType(MOBILE);
        applicationName = parseValue(applicationName);
        getDriver().navigate().to(applicationName);
        Reporter.addStepLog(String.format("application \"%s\" launched in new window", applicationName));
    }

}
