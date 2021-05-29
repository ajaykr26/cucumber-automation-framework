package library.engine.web.steps;

import io.cucumber.java.en.Then;
import library.engine.web.AutoEngWebBaseSteps;
import library.reporting.Reporter;

public class AutoEngWebSelect extends AutoEngWebBaseSteps {

    @Then("^the user select (.+) option by (.+) from dropdown (.+) at the page (.+)$")
    public void select_option_from_dropdownAtPage(String option, String optionBy, String objectName, String pageName) throws Exception {
        getMiscMethods().validateOptionBy(optionBy);
        getInputMethods().selectOptionFromDropdown$(option, optionBy, objectName, pageName);
        Reporter.addStepLog(String.format("\"%s\" is selected from the dropdown \"%s\" at the \"%s\"", option, objectName, pageName));

    }

    @Then("^the user select (\\d+) option by index from dropdown (.+) at the page (.+)$")
    public void select_option_from_dropdown_by_indexPage(String option, String objectName, String pageName) {
        getInputMethods().selectOptionFromDropdown$(option, "selectByIndex", objectName, pageName);
        Reporter.addStepLog(String.format("option at the index \"%s\" is selected from the dropdown \"%s\" at the \"%s\"", option, objectName, pageName));

    }

    @Then("^the user select (.+) option by (.+) from multiselect dropdown (.+) at the page (.+)$")
    public void select_option_from_multiselect_dropdownPage(String option, String optionBy, String objectName, String pageName) throws Exception {
        getMiscMethods().validateOptionBy(optionBy);
        getInputMethods().selectOptionFromDropdown$(objectName, optionBy, option, pageName);
        Reporter.addStepLog(String.format("\"%s\" is selected from the dropdown \"%s\" at the \"%s\"", option, objectName, pageName));

    }

    @Then("^the user select (\\d+) option by index from multiselect dropdown (.+) at the page (.+)$")
    public void select_option_from_multiselect_dropdown_by_indexPage(String option, String locatorType, String locatorText) throws Exception {
        getMiscMethods().validateLocator(locatorType);
        getInputMethods().selectOptionFromDropdown$(locatorType, "selectByIndex", option, locatorText);
    }

    @Then("^the user deselect \"(.*?)\" option by (.+) from multiselect dropdown (.+) at the page \"(.*?)\"$")
    public void deselect_option_from_multiselect_dropdownPage(String option, String optionBy, String locatorType, String locatorText) throws Exception {
        getMiscMethods().validateOptionBy(optionBy);
        getInputMethods().deselectOptionFromDropdown$(locatorType, optionBy, option, locatorText);
    }

    @Then("^the user deselect (\\d+) option by index from multiselect dropdown (.+) at the page \"(.*?)\"$")
    public void deselect_option_from_multiselect_dropdown_by_indexPage(String option, String locatorType, String locatorText) throws Exception {
        getMiscMethods().validateLocator(locatorType);
        getInputMethods().deselectOptionFromDropdown$(locatorType, "selectByIndex", option, locatorText);
    }

    @Then("^the user deselect all options from multiselect dropdown having (.+) at the page \"(.*?)\"$")
    public void unselect_all_option_from_multiselect_dropdownPage(String locatorType, String locatorText) throws Exception {
        getMiscMethods().validateLocator(locatorType);
        getInputMethods().unselectAllOptionFromMultiselectDropdown(locatorType, locatorText);
    }

    @Then("^the user check the checkbox having (.+) at the page \"(.*?)\"$")
    public void check_checkboxPage(String locatorType, String locatorText) throws Exception {
        getMiscMethods().validateLocator(locatorType);
        getInputMethods().checkCheckbox(locatorType, locatorText);
    }

    @Then("^the user uncheck the checkbox having (.+) at the page \"(.*?)\"$")
    public void uncheck_checkboxPage(String locatorType, String locatorText) throws Exception {
        getMiscMethods().validateLocator(locatorType);
        getInputMethods().uncheckCheckbox(locatorType, locatorText);
    }

    @Then("^the user toggle checkbox (.+) at the page \"(.*?)\"$")
    public void toggle_checkboxPage(String locatorType, String locatorText) throws Exception {
        getMiscMethods().validateLocator(locatorType);
        getInputMethods().toggleCheckbox(locatorType, locatorText);
    }

    @Then("^the user select radio button (.+) at the page \"(.*?)\"$")
    public void select_radio_buttonAtPage(String locatorType, String locatorText) throws Exception {
        getMiscMethods().validateLocator(locatorType);
        getInputMethods().selectRadioButton(locatorType, locatorText);
    }

    @Then("^the user select \"(.*?)\" option by (.+) from radio button group (.+) at the page \"(.*?)\"$")
    public void select_option_from_radio_btn_groupAtPage(String option, String by, String locatorType, String locatorText) throws Exception {
        getMiscMethods().validateLocator(locatorType);
        //getMiscMethods().validateOptionBy(optionBy);
        getInputMethods().selectOptionFromRadioButtonGroup(locatorType, option, by, locatorText);
    }

    @Then("^the user select (\\d+) option by index from dropdown having (.+) \"(.*?)\"$")
    public void select_option_from_dropdown_by_index(String option, String locatorType, String locatorText) throws Exception {
        getMiscMethods().validateLocator(locatorType);
        getInputMethods().selectOptionFromDropdown(locatorType, "selectByIndex", option, locatorText);
    }

    @Then("^the user select \"(.*?)\" option by (.+) from multiselect dropdown having (.+) \"(.*?)\"$")
    public void select_option_from_multiselect_dropdown(String option, String optionBy, String locatorType, String locatorText) throws Exception {
        getMiscMethods().validateLocator(locatorType);
        getMiscMethods().validateOptionBy(optionBy);
        getInputMethods().selectOptionFromDropdown(locatorType, optionBy, option, locatorText);
    }

    @Then("^the user select (\\d+) option by index from multiselect dropdown having (.+) \"(.*?)\"$")
    public void select_option_from_multiselect_dropdown_by_index(String option, String locatorType, String locatorText) throws Exception {
        getMiscMethods().validateLocator(locatorType);
        getInputMethods().selectOptionFromDropdown(locatorType, "selectByIndex", option, locatorText);
    }

    @Then("^the user deselect \"(.*?)\" option by (.+) from multiselect dropdown having (.+) \"(.*?)\"$")
    public void deselect_option_from_multiselect_dropdown(String option, String optionBy, String locatorType, String locatorText) throws Exception {
        getMiscMethods().validateLocator(locatorType);
        getMiscMethods().validateOptionBy(optionBy);
        getInputMethods().deselectOptionFromDropdown(locatorType, optionBy, option, locatorText);
    }

    @Then("^the user deselect (\\d+) option by index from multiselect dropdown having (.+) \"(.*?)\"$")
    public void deselect_option_from_multiselect_dropdown_by_index(String option, String locatorType, String locatorText) throws Exception {
        getMiscMethods().validateLocator(locatorType);
        getInputMethods().deselectOptionFromDropdown(locatorType, "selectByIndex", option, locatorText);
    }

    @Then("^the user deselect all options from multiselect dropdown having (.+) \"(.*?)\"$")
    public void unselect_all_option_from_multiselect_dropdown(String locatorType, String locatorText) throws Exception {
        getMiscMethods().validateLocator(locatorType);
        getInputMethods().unselectAllOptionFromMultiselectDropdown(locatorType, locatorText);
    }

    @Then("^the user check the checkbox having (.+) \"(.*?)\"$")
    public void check_checkbox(String locatorType, String locatorText) throws Exception {
        getMiscMethods().validateLocator(locatorType);
        getInputMethods().checkCheckbox(locatorType, locatorText);
    }

    @Then("^the user uncheck the checkbox having (.+) \"(.*?)\"$")
    public void uncheck_checkbox(String locatorType, String locatorText) throws Exception {
        getMiscMethods().validateLocator(locatorType);
        getInputMethods().uncheckCheckbox(locatorType, locatorText);
    }

    @Then("^the user toggle checkbox having (.+) \"(.*?)\"$")
    public void toggle_checkbox(String locatorType, String locatorText) throws Exception {
        getMiscMethods().validateLocator(locatorType);
        getInputMethods().toggleCheckbox(locatorType, locatorText);
    }

    @Then("^the user select radio button having (.+) \"(.*?)\"$")
    public void select_radio_button(String locatorType, String locatorText) throws Exception {
        getMiscMethods().validateLocator(locatorType);
        getInputMethods().selectRadioButton(locatorType, locatorText);
    }

    @Then("^the user select \"(.*?)\" option by (.+) from dropdown having (.+) \"(.*?)\"$")
    public void select_option_from_dropdown(String option, String optionBy, String locatorType, String locatorText) throws Exception {
        getMiscMethods().validateLocator(locatorType);
        getMiscMethods().validateOptionBy(optionBy);
        getInputMethods().selectOptionFromDropdown(locatorType, optionBy, option, locatorText);
    }

    @Then("^the user select \"(.*?)\" option by (.+) from radio button group having (.+) \"(.*?)\"$")
    public void select_option_from_radio_btn_group(String option, String by, String locatorType, String locatorText) throws Exception {
        getMiscMethods().validateLocator(locatorType);
        getInputMethods().selectOptionFromRadioButtonGroup(locatorType, option, by, locatorText);
    }

    @Then("^option \"(.*?)\" by (.+) from dropdown having (.+) \"(.*?)\" should be (selected|unselected)$")
    public void is_option_from_dropdown_selected(String option, String by, String locatorType, String locatorText, String state) throws Throwable {
        getMiscMethods().validateLocator(locatorType);
        boolean flag = state.equals("selected");
        getAssertionMethods().isOptionFromDropdownSelected(locatorType, by, option, locatorText, flag);
    }


}