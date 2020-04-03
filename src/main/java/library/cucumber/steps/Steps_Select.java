package library.cucumber.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java8.En;
import library.reporting.Reporter;
import library.selenium.common.CommonMethods;
import org.testng.Assert;

public class Steps_Select extends CommonMethods implements En {

    // select option by text/value from dropdown
    @Then("^the user select (.+) option by (.+) from dropdown (.+) at the page (.+)$")
    public void select_option_from_dropdown(String option, String optionBy, String objectName, String pageName) throws Exception {
        miscmethodObj.validateOptionBy(optionBy);
        inputObj.selectOptionFromDropdown$(option, optionBy, objectName, pageName);
        Reporter.addStepLog(String.format("\"%s\" is selected from the dropdown \"%s\" at the \"%s\"", option, objectName, pageName));

    }

    // select option by index from dropdown
    @Then("^the user select (\\d+) option by index from dropdown (.+) at the page (.+)$")
    public void select_option_from_dropdown_by_index(String option, String objectName, String pageName)  {
        inputObj.selectOptionFromDropdown$(option, "selectByIndex", objectName, pageName);
        Reporter.addStepLog(String.format("option at the index \"%s\" is selected from the dropdown \"%s\" at the \"%s\"", option, objectName, pageName));

    }



    // select option by text/value from multiselect
    @Then("^the user select (.+) option by (.+) from multiselect dropdown (.+) at the page (.+)$")
    public void select_option_from_multiselect_dropdown(String option, String optionBy, String objectName, String pageName) throws Exception {
        miscmethodObj.validateOptionBy(optionBy);
        inputObj.selectOptionFromDropdown$(objectName, optionBy, option, pageName);
        Reporter.addStepLog(String.format("\"%s\" is selected from the dropdown \"%s\" at the \"%s\"", option, objectName, pageName));

    }

    // select option by index from multiselect
    @Then("^the user select (\\d+) option by index from multiselect dropdown (.+) at the page (.+)$")
    public void select_option_from_multiselect_dropdown_by_index(String option, String type, String accessName) throws Exception {
        miscmethodObj.validateLocator(type);
        inputObj.selectOptionFromDropdown$(type, "selectByIndex", option, accessName);
    }

    // deselect option by text/value from multiselect
    @Then("^the user deselect \"(.*?)\" option by (.+) from multiselect dropdown (.+) at the page \"(.*?)\"$")
    public void deselect_option_from_multiselect_dropdown(String option, String optionBy, String type, String accessName) throws Exception {
        miscmethodObj.validateOptionBy(optionBy);
        inputObj.deselectOptionFromDropdown$(type, optionBy, option, accessName);
    }

    // deselect option by index from multiselect
    @Then("^the user deselect (\\d+) option by index from multiselect dropdown (.+) at the page \"(.*?)\"$")
    public void deselect_option_from_multiselect_dropdown_by_index(String option, String type, String accessName) throws Exception {
        miscmethodObj.validateLocator(type);
        inputObj.deselectOptionFromDropdown$(type, "selectByIndex", option, accessName);
    }

    // step to unselect option from mutliselect dropdown list
    @Then("^the user deselect all options from multiselect dropdown having (.+) at the page \"(.*?)\"$")
    public void unselect_all_option_from_multiselect_dropdown(String type, String accessName) throws Exception {
        miscmethodObj.validateLocator(type);
        inputObj.unselectAllOptionFromMultiselectDropdown(type, accessName);
    }

    //check checkbox steps
    @Then("^the user check the checkbox having (.+) at the page \"(.*?)\"$")
    public void check_checkbox(String type, String accessName) throws Exception {
        miscmethodObj.validateLocator(type);
        inputObj.checkCheckbox(type, accessName);
    }

    //uncheck checkbox steps
    @Then("^the user uncheck the checkbox having (.+) at the page \"(.*?)\"$")
    public void uncheck_checkbox(String type, String accessName) throws Exception {
        miscmethodObj.validateLocator(type);
        inputObj.uncheckCheckbox(type, accessName);
    }

    //steps to toggle checkbox
    @Then("^the user toggle checkbox (.+) at the page \"(.*?)\"$")
    public void toggle_checkbox(String type, String accessName) throws Exception {
        miscmethodObj.validateLocator(type);
        inputObj.toggleCheckbox(type, accessName);
    }

    // step to select radio button
    @Then("^the user select radio button (.+) at the page \"(.*?)\"$")
    public void select_radio_button(String type, String accessName) throws Exception {
        miscmethodObj.validateLocator(type);
        inputObj.selectRadioButton(type, accessName);
    }

    // steps to select option by text from radio button group
    @Then("^the user select \"(.*?)\" option by (.+) from radio button group (.+) at the page \"(.*?)\"$")
    public void select_option_from_radio_btn_group(String option, String by, String type, String accessName) throws Exception {
        miscmethodObj.validateLocator(type);
        //miscmethodObj.validateOptionBy(optionBy);
        inputObj.selectOptionFromRadioButtonGroup(type, option, by, accessName);
    }

    @Then("^the user click on the cell contains (.+) in the table having (.+) \"(.*?)\"$")
    public void select_option_from_multiselect_dropdown(String textToFind, String type, String accessName) throws Exception {
        miscmethodObj.validateLocator(type);
        tableObject.getMatchingCellElement(textToFind, type, accessName).click();
        Reporter.addStepLog(String.format("user clicked at the cell contains \"%s\" in the table having \"%s\" \"%s\"", textToFind, type, accessName));

    }

    @Then("^the user validate that the value (.+) is present in the column (.+) in the table having (.+) \"(.*?)\"$")
    public void validateTextFoundInColum(String textToFind, String columnName, String type, String accessName) throws Exception {
        miscmethodObj.validateLocator(type);
       String actualText = tableObject.getMatchingCellElement(textToFind, columnName, type, accessName).getText();
        Reporter.addStepLog(String.format("user clicked at the cell contains \"%s\" in the table having \"%s\" \"%s\"", textToFind, actualText, accessName));

    }

    @Then("^the user validate that the value: (.+) is present in the column: (.+) in the table where table locator: (.+) header locator: (.+) rows locator: (.+) and column locator (.+) at the page (.+)$")
    public void validateTextFoundInColumAtPage(String textToFind, String columnName, String tableLocator, String headerLocator, String rowLocator, String columLocator, String pageName) throws Throwable {
        String actualText = tableObject.getMatchingCellElement(textToFind, columnName, tableLocator, headerLocator, rowLocator, columLocator, pageName).getText();
        Reporter.addStepLog(String.format("user clicked at the cell contains \"%s\" in the table having \"%s\"", textToFind, actualText));

    }
}
