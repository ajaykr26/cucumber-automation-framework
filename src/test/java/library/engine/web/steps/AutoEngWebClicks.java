package library.engine.web.steps;

import io.cucumber.java.en.Then;
import library.engine.web.AutoEngWebBaseSteps;
import library.reporting.Reporter;

public class AutoEngWebClicks extends AutoEngWebBaseSteps {

    @Then("^the user clicks on the element \"([^\"]*)\" at the page \"([^\"]*)\"$")
    public void userClickOnElementPage(String objectName, String pageName) {
        getElement(objectName, pageName).click();
        Reporter.addStepLog(String.format("clicked to the button on \"%s\" at \"%s\" page", objectName, pageName));
    }

    @Then("^the user forcefully click on the element \"([^\"]*)\" at the page \"([^\"]*)\"$")
    public void forceClickOnPage(String objectName, String pageName) {
        getClickMethods().clickJS(getElement(objectName, pageName));
    }

    @Then("^the user double click on the element \"([^\"]*)\" at the page \"([^\"]*)\"$")
    public void doubleClickOnPage(String objectName, String pageName) {
        getClickMethods().doubleClick(getElement(objectName, pageName));
    }

    @Then("^the user clicks at matching cell where the value \"([^\"]*)\" is present in the column \"([^\"]*)\" in the table \"([^\"]*)\" at the page \"([^\"]*)\"$")
    public void clickOnMatchingCellInColumnOnPage(String textToFind, String columnName, String objectName, String pageName) throws Exception {
        getTableMethods().getMatchingCellElement(textToFind, columnName, objectName, pageName).click();
        Reporter.addStepLog(String.format("user clicked at the cell contains \"%s\" in the table having \"%s\" \"%s\"", textToFind, columnName, pageName));
    }

    @Then("^the user click on element having \"([^\"]*)\": \"([^\"]*)\"$")
    public void click(String locatorType, String locatorText) throws Exception {
        getMiscMethods().validateLocator(locatorType);
        getClickMethods().click(locatorType, locatorText);
        Reporter.addStepLog(String.format("clicked to the button on \"%s\" at \"%s\" page", locatorType, locatorText));

    }

    @Then("^the user forcefully click on element having \"([^\"]*)\": \"([^\"]*)\"$")
    public void forceClick(String locatorType, String locatorText) throws Exception {
        getMiscMethods().validateLocator(locatorType);
        getClickMethods().clickForcefully(locatorType, locatorText);
    }

    @Then("^the user double click on element having \"([^\"]*)\": \"([^\"]*)\"$")
    public void doubleClick(String locatorType, String locatorText) throws Exception {
        getMiscMethods().validateLocator(locatorType);
        getClickMethods().doubleClick(locatorType, locatorText);
    }

    @Then("^the user click on link having text: \"([^\"]*)\"$")
    public void clickOnLinkText(String linkText) {
        getClickMethods().click("linkText", linkText);
    }

    @Then("^the user click on link having partial text: \"([^\"]*)\"$")
    public void clickOnPartialLinkText(String partialLinkText) {
        getClickMethods().click("partialLinkText", partialLinkText);
    }

    @Then("^the user clicks at matching cell where the value \"([^\"]*)\" is present in the column \"([^\"]*)\" in the table having \"([^\"]*)\": \"([^\"]*)\"$")
    public void clickOnMatchingCellInColumn(String textToFind, String columnName, String locatorType, String locatorText) throws Exception {
        getMiscMethods().validateLocator(locatorType);
        getTableMethods().getMatchingCellElement(textToFind, columnName, locatorType, locatorText).click();
        Reporter.addStepLog(String.format("user clicked at the cell contains \"%s\" in the table having \"%s\" \"%s\"", textToFind, columnName, locatorText));
    }


}
