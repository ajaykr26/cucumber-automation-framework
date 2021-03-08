package library.engine.web.steps;

import io.cucumber.java.en.Then;
import library.selenium.exec.BasePO;
import library.selenium.utils.TestCaseFailed;

import static library.selenium.core.FactoryMethod.*;


public class Steps_Common extends BasePO {

    //Step to navigate to the url************************************
    @Then("^the user navigate to \"([^\"]*)\"$")
    public void navigate_to(String link) {
        link = parse_value(link);
        getDriver().navigate().to(link);
    }

    @Then("^the user wait for page to load$")
    public void waitForPageLoad() {
        waitForPageToLoad();

    }

    //Step to navigate forward
    @Then("^the user navigate forward")
    public void navigate_forward() {
        getDriver().navigate().forward();
    }

    //Step to navigate backward
    @Then("^the user navigate back")
    public void navigate_back() {
        getDriver().navigate().back();
    }

    // steps to refresh page
    @Then("^the user refresh page$")
    public void refresh_page() {
        getDriver().navigate().refresh();
    }

    // Switch between windows************************************

    //Switch to new window
    @Then("^the user switch to new window$")
    public void switch_to_new_window() {
        getNavigationMethods().switchToNewWindow();
    }

    //Switch to old window
    @Then("^the user switch to previous window$")
    public void switch_to_old_window() {
        getNavigationMethods().switchToOldWindow();
    }

    //Switch to new window by window title
    @Then("^the user switch to window having title \"(.*?)\"$")
    public void switch_to_window_by_title(String windowTitle) throws Exception {
        getNavigationMethods().switchToWindowByTitle(windowTitle);
    }

    //Close new window
    @Then("^the user close new window$")
    public void close_new_window() {
        getNavigationMethods().closeNewWindow();
    }

    // Switch between frame************************************

    // Step to switch to frame by web element
    @Then("^the user switch to frame having (.+) \"(.*?)\"$")
    public void switch_frame_by_element(String method, String value) throws Throwable {
        getNavigationMethods().switchFrame(method, value);
    }

    // step to switch to main content
    @Then("^the user switch to main content$")
    public void switch_to_default_content() {
        getNavigationMethods().switchToDefaultContent();
    }

    // To interact with browser************************************

    // step to resize browser
    @Then("^the user resize browser window size to width (\\d+) and height (\\d+)$")
    public void resize_browser(int width, int heigth) {
        getNavigationMethods().resizeBrowser(width, heigth);
    }

    // step to maximize browser
    @Then("^the user maximize browser window$")
    public void maximize_browser() {
        getNavigationMethods().maximizeBrowser();
    }

    //Step to close the browser
    @Then("^the user close browser$")
    public void close_browser() {
        getNavigationMethods().closeDriver();
    }

    // zoom in/out page************************************

    // steps to zoom in page
    @Then("^the user zoom in page$")
    public void zoom_in() throws Throwable {
        getNavigationMethods().zoomInOut("ADD");
    }

    // steps to zoom out page
    @Then("^the user zoom out page$")
    public void zoom_out() throws Throwable {
        getNavigationMethods().zoomInOut("SUBTRACT");
    }

    // zoom out webpage till necessary element displays************************************

    // steps to zoom out till element displays
    @Then("^the user zoom out page till I see element having (.+) \"(.*?)\"$")
    public void zoom_till_element_display(String type, String accessName) throws Throwable {
        getMiscMethods().validateLocator(type);
        getNavigationMethods().zoomInOutTillElementDisplay(type, "substract", accessName);
    }

    // reset webpage view use
    @Then("^the user reset page view$")
    public void reset_page_zoom() throws Throwable {
        getNavigationMethods().zoomInOut("reset");
    }

    // scroll webpage
    @Then("^the user scroll to (top|end) of page$")
    public void scroll_page(String to) throws Exception {
        getNavigationMethods().scrollPage(to);
    }


    // scroll webpage to specific element
    @Then("^the user scroll to element having (.+) \"(.*?)\"$")
    public void scroll_to_element(String type, String accessName) throws Throwable {
        getMiscMethods().validateLocator(type);
        getNavigationMethods().scrollToElement(type, accessName);
    }

    // hover over element************************************

    // Note: Doesn't work on Windows firefox
    @Then("^the user hover over element having (.+) \"(.*?)\"$")
    public void hover_over_element(String type, String accessName) throws Throwable {
        getMiscMethods().validateLocator(type);
        getNavigationMethods().hoverOverElement(type, accessName);
    }

    //Assertion steps

    /**
     * page title checking
     *
     * @param present :
     * @param title   :
     */
    @Then("^the user validate \\s*((?:not)?)\\s+see page title as \"(.+)\"$")
    public void check_title(String present, String title) throws TestCaseFailed {
        //System.out.println("Present :" + present.isEmpty());
        getAssertionMethods().checkTitle(title, present.isEmpty());
    }

    // step to check element partial text
    @Then("^the user should\\s*((?:not)?)\\s+see page title having partial text as \"(.*?)\"$")
    public void check_partial_text(String present, String partialTextTitle) throws TestCaseFailed {
        //System.out.println("Present :" + present.isEmpty());
        getAssertionMethods().checkPartialTitle(partialTextTitle, present.isEmpty());
    }

    // step to check element text
    @Then("^element having (.+) \"([^\"]*)\" should\\s*((?:not)?)\\s+have text as \"(.*?)\"$")
    public void check_element_text(String type, String accessName, String present, String value) throws Throwable {
        getMiscMethods().validateLocator(type);
        getAssertionMethods().checkElementText(type, value, accessName, present.isEmpty());
    }

    //step to check element partial text
    @Then("^element having (.+) \"([^\"]*)\" should\\s*((?:not)?)\\s+have partial text as \"(.*?)\"$")
    public void check_element_partial_text(String type, String accessName, String present, String value) throws Throwable {
        getMiscMethods().validateLocator(type);
        getAssertionMethods().checkElementPartialText(type, value, accessName, present.isEmpty());
    }

    // step to check attribute value
    @Then("^element having (.+) \"([^\"]*)\" should\\s*((?:not)?)\\s+have attribute \"(.*?)\" with value \"(.*?)\"$")
    public void check_element_attribute(String type, String accessName, String present, String attrb, String value) throws Throwable {
        getMiscMethods().validateLocator(type);
        getAssertionMethods().checkElementAttribute(type, attrb, value, accessName, present.isEmpty());
    }

    // step to check element enabled or not
    @Then("^element having (.+) \"([^\"]*)\" should\\s*((?:not)?)\\s+be (enabled|disabled)$")
    public void check_element_enable(String type, String accessName, String present, String state) throws Throwable {
        getMiscMethods().validateLocator(type);
        boolean flag = state.equals("enabled");
        if (!present.isEmpty()) {
            flag = !flag;
        }
        getAssertionMethods().checkElementEnable(type, accessName, flag);
    }

    //step to check element present or not
    @Then("^element having (.+) \"(.*?)\" should\\s*((?:not)?)\\s+be present$")
    public void check_element_presence(String type, String accessName, String present) throws Throwable {
        getMiscMethods().validateLocator(type);
        getAssertionMethods().checkElementPresence(type, accessName, present.isEmpty());
    }

    //step to assert checkbox is checked or unchecked
    @Then("^checkbox having (.+) \"(.*?)\" should be (checked|unchecked)$")
    public void is_checkbox_checked(String type, String accessName, String state) throws Throwable {
        getMiscMethods().validateLocator(type);
        boolean flag = state.equals("checked");
        getAssertionMethods().isCheckboxChecked(type, accessName, flag);
    }

    //steps to assert radio button checked or unchecked
    @Then("^radio button having (.+) \"(.*?)\" should be (selected|unselected)$")
    public void is_radio_button_selected(String type, String accessName, String state) throws Throwable {
        getMiscMethods().validateLocator(type);
        boolean flag = state.equals("selected");
        getAssertionMethods().isRadioButtonSelected(type, accessName, flag);
    }

    //steps to assert option by text from radio button group selected/unselected
    @Then("^option \"(.*?)\" by (.+) from radio button group having (.+) \"(.*?)\" should be (selected|unselected)$")
    public void is_option_from_radio_button_group_selected(String option, String attrb, String type, String accessName, String state) throws Throwable {
        getMiscMethods().validateLocator(type);
        boolean flag = state.equals("selected");
        getAssertionMethods().isOptionFromRadioButtonGroupSelected(type, attrb, option, accessName, flag);
    }

    //steps to check link presence
    @Then("^link having text \"(.*?)\" should\\s*((?:not)?)\\s+be present$")
    public void check_element_presence(String accessName, String present) throws Throwable {
        getAssertionMethods().checkElementPresence("linkText", accessName, present.isEmpty());
    }

    //steps to check partail link presence
    @Then("^link having partial text \"(.*?)\" should\\s*((?:not)?)\\s+be present$")
    public void check_partial_element_presence(String accessName, String present) throws Throwable {
        getAssertionMethods().checkElementPresence("partialLinkText", accessName, present.isEmpty());
    }

    //step to assert javascript pop-up alert text
    @Then("^the user should see alert text as \"(.*?)\"$")
    public void check_alert_text(String actualValue) throws TestCaseFailed {
        getAssertionMethods().checkAlertText(actualValue);
    }

    // step to select dropdown list
    @Then("^option \"(.*?)\" by (.+) from dropdown having (.+) \"(.*?)\" should be (selected|unselected)$")
    public void is_option_from_dropdown_selected(String option, String by, String type, String accessName, String state) throws Throwable {
        getMiscMethods().validateLocator(type);
        boolean flag = state.equals("selected");
        getAssertionMethods().isOptionFromDropdownSelected(type, by, option, accessName, flag);
    }

    //Input steps

    // enter text into input field steps
    @Then("^the user enter \"([^\"]*)\" into input field having (.+) \"([^\"]*)\"$")
    public void enter_text(String text, String type, String accessName) throws Exception {
        text = parse_value(text);
//        System.out.println(text + type + accessName);
        getMiscMethods().validateLocator(type);
        getInputMethods().enterText(type, text, accessName);
    }

    // clear input field steps
    @Then("^the user clear input field having (.+) \"([^\"]*)\"$")
    public void clear_text(String type, String accessName) throws Exception {
        getMiscMethods().validateLocator(type);
        getInputMethods().clearText(type, accessName);
    }

    // select option by text/value from dropdown
    @Then("^the user select \"(.*?)\" option by (.+) from dropdown having (.+) \"(.*?)\"$")
    public void select_option_from_dropdown(String option, String optionBy, String type, String accessName) throws Exception {
        getMiscMethods().validateLocator(type);
        getMiscMethods().validateOptionBy(optionBy);
        getInputMethods().selectOptionFromDropdown(type, optionBy, option, accessName);
    }

    // select option by index from dropdown
    @Then("^the user select (\\d+) option by index from dropdown having (.+) \"(.*?)\"$")
    public void select_option_from_dropdown_by_index(String option, String type, String accessName) throws Exception {
        getMiscMethods().validateLocator(type);
        getInputMethods().selectOptionFromDropdown(type, "selectByIndex", option, accessName);
    }



    // select option by text/value from multiselect
    @Then("^the user select \"(.*?)\" option by (.+) from multiselect dropdown having (.+) \"(.*?)\"$")
    public void select_option_from_multiselect_dropdown(String option, String optionBy, String type, String accessName) throws Exception {
        getMiscMethods().validateLocator(type);
        getMiscMethods().validateOptionBy(optionBy);
        getInputMethods().selectOptionFromDropdown(type, optionBy, option, accessName);
    }

    // select option by index from multiselect
    @Then("^the user select (\\d+) option by index from multiselect dropdown having (.+) \"(.*?)\"$")
    public void select_option_from_multiselect_dropdown_by_index(String option, String type, String accessName) throws Exception {
        getMiscMethods().validateLocator(type);
        getInputMethods().selectOptionFromDropdown(type, "selectByIndex", option, accessName);
    }

    // deselect option by text/value from multiselect
    @Then("^the user deselect \"(.*?)\" option by (.+) from multiselect dropdown having (.+) \"(.*?)\"$")
    public void deselect_option_from_multiselect_dropdown(String option, String optionBy, String type, String accessName) throws Exception {
        getMiscMethods().validateLocator(type);
        getMiscMethods().validateOptionBy(optionBy);
        getInputMethods().deselectOptionFromDropdown(type, optionBy, option, accessName);
    }

    // deselect option by index from multiselect
    @Then("^the user deselect (\\d+) option by index from multiselect dropdown having (.+) \"(.*?)\"$")
    public void deselect_option_from_multiselect_dropdown_by_index(String option, String type, String accessName) throws Exception {
        getMiscMethods().validateLocator(type);
        getInputMethods().deselectOptionFromDropdown(type, "selectByIndex", option, accessName);
    }

    // step to unselect option from mutliselect dropdown list
    @Then("^the user deselect all options from multiselect dropdown having (.+) \"(.*?)\"$")
    public void unselect_all_option_from_multiselect_dropdown(String type, String accessName) throws Exception {
        getMiscMethods().validateLocator(type);
        getInputMethods().unselectAllOptionFromMultiselectDropdown(type, accessName);
    }

    //check checkbox steps
    @Then("^the user check the checkbox having (.+) \"(.*?)\"$")
    public void check_checkbox(String type, String accessName) throws Exception {
        getMiscMethods().validateLocator(type);
        getInputMethods().checkCheckbox(type, accessName);
    }

    //uncheck checkbox steps
    @Then("^the user uncheck the checkbox having (.+) \"(.*?)\"$")
    public void uncheck_checkbox(String type, String accessName) throws Exception {
        getMiscMethods().validateLocator(type);
        getInputMethods().uncheckCheckbox(type, accessName);
    }

    //steps to toggle checkbox
    @Then("^the user toggle checkbox having (.+) \"(.*?)\"$")
    public void toggle_checkbox(String type, String accessName) throws Exception {
        getMiscMethods().validateLocator(type);
        getInputMethods().toggleCheckbox(type, accessName);
    }

    // step to select radio button
    @Then("^the user select radio button having (.+) \"(.*?)\"$")
    public void select_radio_button(String type, String accessName) throws Exception {
        getMiscMethods().validateLocator(type);
        getInputMethods().selectRadioButton(type, accessName);
    }

    // steps to select option by text from radio button group
    @Then("^the user select \"(.*?)\" option by (.+) from radio button group having (.+) \"(.*?)\"$")
    public void select_option_from_radio_btn_group(String option, String by, String type, String accessName) throws Exception {
        getMiscMethods().validateLocator(type);
        //getMiscMethods().validateOptionBy(optionBy);
        getInputMethods().selectOptionFromRadioButtonGroup(type, option, by, accessName);
    }

    //Click element Steps

    // click on web element
    @Then("^the user click on element having (.+) \"(.*?)\"$")
    public void click(String type, String accessName) throws Exception {
        getMiscMethods().validateLocator(type);
        getClickMethods().click(type, accessName);
    }

    //Forcefully click on element
    @Then("^the user forcefully click on element having (.+) \"(.*?)\"$")
    public void click_forcefully(String type, String accessName) throws Exception {
        getMiscMethods().validateLocator(type);
        getClickMethods().clickForcefully(type, accessName);
    }

    // double click on web element
    @Then("^the user double click on element having (.+) \"(.*?)\"$")
    public void double_click(String type, String accessValue) throws Exception {
        getMiscMethods().validateLocator(type);
        getClickMethods().doubleClick(type, accessValue);
    }

    // steps to click on link
    @Then("^the user click on link having text \"(.*?)\"$")
    public void click_link(String linktext) {
        getClickMethods().click("linkText", linktext);
    }

    //Step to click on partial link
    @Then("^the user click on link having partial text \"(.*?)\"$")
    public void click_partial_link(String partialLinktext) {
        getClickMethods().click("partialLinkText", partialLinktext);
    }

    //Progress methods

    // wait for specific period of time
    @Then("^the user wait for \"(.*?)\" sec$")
    public void wait(String time) throws NumberFormatException, InterruptedException {
        getWaitMethods().wait(time);
    }

    //wait for specific element to display for specific period of time
    @Then("^the user wait for \"(.*?)\" seconds for \"(.*?)\" element to be displayed at the page \"(.*?)\"$")
    public void wait_for_ele_to_display(String duration, String objectName, String pageName) throws Throwable {
        getWaitMethods().waitForElementToDisplay$(objectName, pageName, duration);
    }

    // wait for specific element to enable for specific period of time
    @Then("^the user wait for \"(.*?)\" seconds for \"(.*?)\" element to be enabled at the page \"(.*?)\"$")
    public void wait_for_ele_to_click(String duration, String objectName, String pageName) throws Throwable {
        getWaitMethods().waitForElementToClick$(objectName, pageName, duration);
    }

    //wait for specific element to display for specific period of time
    @Then("^the user wait \"(.*?)\" seconds for element having (.+) \"(.*?)\" to display$")
    public void wait_for_element_to_display(String duration, String type, String accessName) throws Throwable {
        getMiscMethods().validateLocator(type);
        getWaitMethods().waitForElementToDisplay(type, accessName, duration);
    }

    // wait for specific element to enable for specific period of time
    @Then("^the user wait \"(.*?)\" seconds for element having (.+) \"(.*?)\" to be enabled$")
    public void wait_for_element_to_click(String duration, String type, String accessName) throws Throwable {
        getMiscMethods().validateLocator(type);
        getWaitMethods().waitForElementToClick(type, accessName, duration);
    }

    //JavaScript handling steps

    //Step to handle java script
    @Then("^the user accept alert$")
    public void handle_alert() {
        getJSMethods().handleAlert("accept");
    }

    //Steps to dismiss java script
    @Then("^the user dismiss alert$")
    public void dismiss_alert() {
        getJSMethods().handleAlert("dismiss");
    }

}
