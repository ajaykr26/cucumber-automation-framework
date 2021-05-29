package library.engine.web.steps;

import io.cucumber.java.en.Then;
import library.engine.web.AutoEngWebBaseSteps;

import static library.engine.core.AutoEngCoreParser.parseValue;


public class AutoEngWebCommon extends AutoEngWebBaseSteps {

    //Step to navigate to the url************************************
    @Then("^the user navigate to \"([^\"]*)\"$")
    public void navigate_to(String link) {
        link = parseValue(link);
        getDriver().navigate().to(link);
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
    public void switch_frame_by_element(String locatorType, String locatorText) throws Throwable {
        getNavigationMethods().switchFrame(locatorType, locatorText);
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

    // steps to zoom out till element displays
    @Then("^the user zoom out page till I see element having (.+) \"(.*?)\"$")
    public void zoom_till_element_display(String locatorType, String locatorText) throws Throwable {
        getMiscMethods().validateLocator(locatorType);
        getNavigationMethods().zoomInOutTillElementDisplay(locatorType, "substract", locatorText);
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
    public void scroll_to_element(String locatorType, String locatorText) throws Throwable {
        getMiscMethods().validateLocator(locatorType);
        getNavigationMethods().scrollToElement(locatorType, locatorText);
    }

    // hover over element************************************

    // Note: Doesn't work on Windows firefox
    @Then("^the user hover over element having (.+) \"(.*?)\"$")
    public void hover_over_element(String locatorType, String locatorText) throws Throwable {
        getMiscMethods().validateLocator(locatorType);
        getNavigationMethods().hoverOverElement(locatorType, locatorText);
    }

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
    public void wait_for_element_to_display(String duration, String locatorType, String locatorText) throws Throwable {
        getMiscMethods().validateLocator(locatorType);
        getWaitMethods().waitForElementToDisplay(locatorType, locatorText, duration);
    }

    // wait for specific element to enable for specific period of time
    @Then("^the user wait \"(.*?)\" seconds for element having (.+) \"(.*?)\" to be enabled$")
    public void wait_for_element_to_click(String duration, String locatorType, String locatorText) throws Throwable {
        getMiscMethods().validateLocator(locatorType);
        getWaitMethods().waitForElementToClick(locatorType, locatorText, duration);
    }


}
