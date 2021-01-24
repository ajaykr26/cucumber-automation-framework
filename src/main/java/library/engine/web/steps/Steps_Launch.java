package library.engine.web.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java8.En;
import library.reporting.Reporter;
import library.selenium.common.CommonMethods;

public class Steps_Launch extends CommonMethods implements En {

    @Given("^the user launch the \"([^\"]*)\" application in new window$")
    public void launchApplication(String applicationName) throws Throwable{
        applicationName = parse_value(applicationName);
        getDriver().navigate().to(applicationName);
        Reporter.addStepLog(String.format("application \"%s\" launched in new window", applicationName));

    }



}
