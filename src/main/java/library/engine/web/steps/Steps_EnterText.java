package library.engine.web.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java8.En;
import library.reporting.Reporter;
import library.selenium.common.CommonMethods;

public class Steps_EnterText extends CommonMethods implements En {

    @Then("^the user enters \"([^\"]*)\" into the input field \"([^\"]*)\" at the page \"([^\"]*)\"$")
    public void enterTextIntoTheInputField(String textToEnter, String objectName, String pageName) throws Throwable {
        textToEnter = parse_value(textToEnter);
        getElement(objectName, pageName).sendKeys(textToEnter);
        Reporter.addStepLog(String.format("\"%s\" entere in the field \"%s\" at the \"%s\"", textToEnter, objectName, pageName));
    }

    @Then("^the user enters credential \"([^\"]*)\" into the input field \"([^\"]*)\" at the page \"([^\"]*)\"$")
    public void entersCredentialIntoTheInputField(String credential, String objectName, String pageName) throws Throwable {
        credential = parse_value(credential);
        getElement(objectName, pageName).sendKeys(credential);
        Reporter.addStepLog(String.format("credentials entere in the field \"%s\" at the \"%s\"", objectName, pageName));

    }

}
