package library.engine.web.steps;

import io.cucumber.java.en.Then;
import library.common.TestContext;
import library.engine.web.AutoEngBaseWebSteps;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AutoEngWebWindowHandles extends AutoEngBaseWebSteps {

    @Then("^the user wait for page to load$")
    public void waitForPageLoad() {
        getBasePO().waitForPageToLoad();
    }

    @Then("^the user accept alert$")
    public void acceptAlert() {
        try {
            getWait().until(ExpectedConditions.alertIsPresent());
            getDriver().switchTo().alert().accept();
        } catch (NoAlertPresentException exception) {
            logger.error(exception.getMessage());
        }
    }

    @Then("^the user dismiss alert$")
    public void dismissAlert() {
        try {
            getWait().until(ExpectedConditions.alertIsPresent());
            getDriver().switchTo().alert().dismiss();
        } catch (NoAlertPresentException exception) {
            logger.error(exception.getMessage());
        }
    }

    @Then("^the user enters \"(.*?)\" into the textbook on the alert$")
    public void enterTextInToAlertTextbook(String textToEnter) {
        try {
            getWait().until(ExpectedConditions.alertIsPresent());
            getDriver().switchTo().alert().sendKeys(parseValue(textToEnter));
        } catch (NoAlertPresentException exception) {
            logger.error(exception.getMessage());
        }
    }

    @Then("^the user store text from from the alert and store into the data diction with dictionary key \"(.*?)\"$")
    public void storeTextFromAlert(String dictionaryKey) {
        try {
            getWait().until(ExpectedConditions.alertIsPresent());
            String textToStore = getDriver().switchTo().alert().getText();
            TestContext.getInstance().testdataPut(dictionaryKey, textToStore);
        } catch (NoAlertPresentException exception) {
            logger.error(exception.getMessage());
        }

    }

}
