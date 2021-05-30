package library.engine.web.steps;

import io.cucumber.java.en.Then;
import library.engine.web.AutoEngWebBaseSteps;

import static library.engine.core.AutoEngCoreParser.parseValue;


public class AutoEngWebCommon extends AutoEngWebBaseSteps {


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

}
