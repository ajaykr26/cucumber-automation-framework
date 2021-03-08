package library.engine.web.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java8.En;
import library.reporting.Reporter;
import library.selenium.exec.BasePO;

import static library.selenium.core.FactoryMethod.*;

public class Steps_Select extends BasePO implements En {

    // select option by text/value from dropdown
    @Then("^the user select (.+) option by (.+) from dropdown (.+) at the page (.+)$")
    public void select_option_from_dropdown(String option, String optionBy, String objectName, String pageName) throws Exception {
        getMiscMethods().validateOptionBy(optionBy);
        getInputMethods().selectOptionFromDropdown$(option, optionBy, objectName, pageName);
        Reporter.addStepLog(String.format("\"%s\" is selected from the dropdown \"%s\" at the \"%s\"", option, objectName, pageName));

    }

    // select option by index from dropdown
    @Then("^the user select (\\d+) option by index from dropdown (.+) at the page (.+)$")
    public void select_option_from_dropdown_by_index(String option, String objectName, String pageName)  {
        getInputMethods().selectOptionFromDropdown$(option, "selectByIndex", objectName, pageName);
        Reporter.addStepLog(String.format("option at the index \"%s\" is selected from the dropdown \"%s\" at the \"%s\"", option, objectName, pageName));

    }



    // select option by text/value from multiselect
    @Then("^the user select (.+) option by (.+) from multiselect dropdown (.+) at the page (.+)$")
    public void select_option_from_multiselect_dropdown(String option, String optionBy, String objectName, String pageName) throws Exception {
        getMiscMethods().validateOptionBy(optionBy);
        getInputMethods().selectOptionFromDropdown$(objectName, optionBy, option, pageName);
        Reporter.addStepLog(String.format("\"%s\" is selected from the dropdown \"%s\" at the \"%s\"", option, objectName, pageName));

    }

    // select option by index from multiselect
    @Then("^the user select (\\d+) option by index from multiselect dropdown (.+) at the page (.+)$")
    public void select_option_from_multiselect_dropdown_by_index(String option, String type, String accessName) throws Exception {
        getMiscMethods().validateLocator(type);
        getInputMethods().selectOptionFromDropdown$(type, "selectByIndex", option, accessName);
    }

    // deselect option by text/value from multiselect
    @Then("^the user deselect \"(.*?)\" option by (.+) from multiselect dropdown (.+) at the page \"(.*?)\"$")
    public void deselect_option_from_multiselect_dropdown(String option, String optionBy, String type, String accessName) throws Exception {
        getMiscMethods().validateOptionBy(optionBy);
        getInputMethods().deselectOptionFromDropdown$(type, optionBy, option, accessName);
    }

    // deselect option by index from multiselect
    @Then("^the user deselect (\\d+) option by index from multiselect dropdown (.+) at the page \"(.*?)\"$")
    public void deselect_option_from_multiselect_dropdown_by_index(String option, String type, String accessName) throws Exception {
        getMiscMethods().validateLocator(type);
        getInputMethods().deselectOptionFromDropdown$(type, "selectByIndex", option, accessName);
    }

    // step to unselect option from mutliselect dropdown list
    @Then("^the user deselect all options from multiselect dropdown having (.+) at the page \"(.*?)\"$")
    public void unselect_all_option_from_multiselect_dropdown(String type, String accessName) throws Exception {
        getMiscMethods().validateLocator(type);
        getInputMethods().unselectAllOptionFromMultiselectDropdown(type, accessName);
    }

    //check checkbox steps
    @Then("^the user check the checkbox having (.+) at the page \"(.*?)\"$")
    public void check_checkbox(String type, String accessName) throws Exception {
        getMiscMethods().validateLocator(type);
        getInputMethods().checkCheckbox(type, accessName);
    }

    //uncheck checkbox steps
    @Then("^the user uncheck the checkbox having (.+) at the page \"(.*?)\"$")
    public void uncheck_checkbox(String type, String accessName) throws Exception {
        getMiscMethods().validateLocator(type);
        getInputMethods().uncheckCheckbox(type, accessName);
    }

    //steps to toggle checkbox
    @Then("^the user toggle checkbox (.+) at the page \"(.*?)\"$")
    public void toggle_checkbox(String type, String accessName) throws Exception {
        getMiscMethods().validateLocator(type);
        getInputMethods().toggleCheckbox(type, accessName);
    }

    // step to select radio button
    @Then("^the user select radio button (.+) at the page \"(.*?)\"$")
    public void select_radio_button(String type, String accessName) throws Exception {
        getMiscMethods().validateLocator(type);
        getInputMethods().selectRadioButton(type, accessName);
    }

    // steps to select option by text from radio button group
    @Then("^the user select \"(.*?)\" option by (.+) from radio button group (.+) at the page \"(.*?)\"$")
    public void select_option_from_radio_btn_group(String option, String by, String type, String accessName) throws Exception {
        getMiscMethods().validateLocator(type);
        //getMiscMethods().validateOptionBy(optionBy);
        getInputMethods().selectOptionFromRadioButtonGroup(type, option, by, accessName);
    }

}
