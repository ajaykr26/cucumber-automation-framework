package library.engine.web.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java8.En;
import library.common.TestContext;
import library.reporting.Reporter;
import library.selenium.utils.CommonMethods;

import static library.engine.core.EngConstants.SELENIUM;

public class Steps_Launch extends CommonMethods implements En {

    @Given("^the user launch the \"([^\"]*)\" application in new window$")
    public void launchApplication(String applicationName) throws Throwable{
        TestContext.getInstance().setActiveWindowType(SELENIUM);
        applicationName = parse_value(applicationName);
        getDriver().navigate().to(applicationName);
        Reporter.addStepLog(String.format("application \"%s\" launched in new window", applicationName));

    }



}
