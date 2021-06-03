package library.engine.web.steps;

import io.cucumber.java.en.Then;
import library.common.TestContext;
import library.engine.web.AutoEngWebBaseSteps;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static library.engine.core.AutoEngCoreParser.parseValue;

public class AutoEngWebUtils extends AutoEngWebBaseSteps {

    @Then("^the user wait for page to load$")
    public void waitForPageLoad() {
        getBasePO().waitForPageToLoad();
    }

    @Then("^the user (?:accept|dismiss) alert$")
    public void acceptAlert(String action) {
        getDriverWait().until(ExpectedConditions.alertIsPresent());
        try {
            switch (action) {
                case "accept":
                    getDriver().switchTo().alert().accept();
                    break;
                case "dismiss":
                    getDriver().switchTo().alert().dismiss();
                    break;
            }
        } catch (NoAlertPresentException exception) {
            logger.error(exception.getMessage());
        }
    }

    @Then("^the user enters \"(.*?)\" into the textbook on the alert$")
    public void enterTextInToAlertTextbook(String textToEnter) {
        try {
            getDriverWait().until(ExpectedConditions.alertIsPresent());
            getDriver().switchTo().alert().sendKeys(parseValue(textToEnter));
        } catch (NoAlertPresentException exception) {
            logger.error(exception.getMessage());
        }
    }

    @Then("^the user store text from from the alert and store into the data diction with dictionary key \"(.*?)\"$")
    public void storeTextFromAlert(String dictionaryKey) {
        try {
            getDriverWait().until(ExpectedConditions.alertIsPresent());
            String textToStore = getDriver().switchTo().alert().getText();
            TestContext.getInstance().testdataPut(dictionaryKey, textToStore);
        } catch (NoAlertPresentException exception) {
            logger.error(exception.getMessage());
        }

    }

}
