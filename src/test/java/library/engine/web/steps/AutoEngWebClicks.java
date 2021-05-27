package library.engine.web.steps;

import io.cucumber.java.en.Then;
import library.engine.web.AutoEngBaseWebSteps;
import library.reporting.Reporter;

public class AutoEngWebClicks extends AutoEngBaseWebSteps {

    @Then("^the user clicks on the element \"([^\"]*)\" at the page \"([^\"]*)\"$")
    public void userClickOnElementPage(String objectName, String pageName) throws Throwable {
        getElement(objectName, pageName).click();
        Reporter.addStepLog(String.format("clicked to the button on \"%s\" at \"%s\" page", objectName, pageName));
    }

    @Then("^the user clicks at matching cell where the value (.+) is present in the column (.+) in the table having (.+) \"(.*?)\"$")
    public void validateTextFoundInColumn(String textToFind, String columnName, String locatorType, String locatorText) throws Exception {
        getMiscMethods().validateLocator(locatorType);
        getTableMethods().getMatchingCellElement(textToFind, columnName, locatorType, locatorText).click();
        Reporter.addStepLog(String.format("user clicked at the cell contains \"%s\" in the table having \"%s\" \"%s\"", textToFind, columnName, locatorText));
    }

    @Then("^the user click on element having (.+) \"(.*?)\"$")
    public void click(String locatorType, String locatorText) throws Exception {
        getMiscMethods().validateLocator(locatorType);
        getClickMethods().click(locatorType, locatorText);
        Reporter.addStepLog(String.format("clicked to the button on \"%s\" at \"%s\" page", locatorType, locatorText));

    }

    @Then("^the user forcefully click on element having (.+) \"(.*?)\"$")
    public void click_forcefully(String locatorType, String locatorText) throws Exception {
        getMiscMethods().validateLocator(locatorType);
        getClickMethods().clickForcefully(locatorType, locatorText);
    }

    @Then("^the user double click on element having (.+) \"(.*?)\"$")
    public void double_click(String locatorType, String locatorText) throws Exception {
        getMiscMethods().validateLocator(locatorType);
        getClickMethods().doubleClick(locatorType, locatorText);
    }

    @Then("^the user click on link having text \"(.*?)\"$")
    public void click_link(String linkText) {
        getClickMethods().click("linkText", linkText);
    }

    @Then("^the user click on link having partial text \"(.*?)\"$")
    public void click_partial_link(String partialLinkText) {
        getClickMethods().click("partialLinkText", partialLinkText);
    }


}
