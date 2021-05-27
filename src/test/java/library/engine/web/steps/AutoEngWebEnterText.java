package library.engine.web.steps;

import io.cucumber.java.en.Then;
import library.engine.web.AutoEngBaseWebSteps;
import library.reporting.Reporter;

public class AutoEngWebEnterText extends AutoEngBaseWebSteps {

    @Then("^the user enters \"([^\"]*)\" into the input field \"([^\"]*)\" at the page \"([^\"]*)\"$")
    public void enterTextIntoTheInputFieldPage(String textToEnter, String objectName, String pageName) throws Throwable {
        textToEnter = parseValue(textToEnter);
        getElement(objectName, pageName).sendKeys(textToEnter);
        Reporter.addStepLog(String.format("\"%s\" is entered in the field \"%s\" at the \"%s\"", textToEnter, objectName, pageName));
    }

    @Then("^the user enters credential \"([^\"]*)\" into the input field \"([^\"]*)\" at the page \"([^\"]*)\"$")
    public void entersCredentialIntoTheInputFieldPage(String credential, String objectName, String pageName) throws Throwable {
        credential = parseValue(credential);
        getElement(objectName, pageName).sendKeys(credential);
        Reporter.addStepLog(String.format("credential is entered in the field \"%s\" at the \"%s\"", objectName, pageName));

    }

    @Then("^the user enter \"([^\"]*)\" into input field having (.+) \"([^\"]*)\"$")
    public void enter_text(String textToEnter, String locatorType, String locatorText) throws Exception {
        textToEnter = parseValue(textToEnter);
        getMiscMethods().validateLocator(locatorType);
        getInputMethods().enterText(locatorType, textToEnter, locatorText);
        Reporter.addStepLog(String.format("\"%s\" is entered in the field having \"%s\": \"%s\"", textToEnter, locatorType, locatorText));

    }

    @Then("^the user clear input field having (.+) \"([^\"]*)\"$")
    public void clear_text(String type, String accessName) throws Exception {
        getMiscMethods().validateLocator(type);
        getInputMethods().clearText(type, accessName);
    }


}
