package library.engine.web.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java8.En;
import library.reporting.Reporter;
import library.selenium.common.CommonMethods;
import org.testng.Assert;

public class Steps_Validates extends CommonMethods implements En {

    @Then("^the user validate that the page title exactly matched with \"([^\"]*)\"$")
    public void validatePageTitle(String expectedPageTitle) throws Throwable {
        String actualPageTitle = getDriver().getTitle();
        Assert.assertEquals(actualPageTitle, expectedPageTitle);
        Reporter.addStepLog(String.format("page title validated \n expected: \"%s\" actual: \"%s\"", expectedPageTitle, actualPageTitle));
    }

    @Then("^the user validate that text at the element having (.+) \"([^\"]*)\" is \"(.*?)\" with the value \"(.*?)\"$")
    public void validateText(String type, String accessName, String matchType, String value) throws Throwable {
        value = parse_value(value);
        miscmethodObj.validateLocator(type);
        assertionObj.validateElementText(matchType, type, value, accessName);
    }
}
