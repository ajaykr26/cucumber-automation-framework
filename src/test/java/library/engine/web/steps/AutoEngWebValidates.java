package library.engine.web.steps;

import io.cucumber.java.en.Then;
import library.common.Constants;
import library.engine.web.AutoEngWebBaseSteps;
import library.engine.web.utils.TestCaseFailed;
import library.reporting.Reporter;
import org.testng.Assert;

import java.io.File;

import static library.engine.core.AutoEngCoreParser.parseValue;
import static library.selenium.core.Screenshot.compareScreenshot;
import static library.selenium.core.Screenshot.getImageFromUrl;

public class AutoEngWebValidates extends AutoEngWebBaseSteps {

    @Then("^the user validate that the page title exactly matched with \"([^\"]*)\"$")
    public void validatePageTitle(String expectedPageTitle) {
        String actualPageTitle = getDriver().getTitle();
        Assert.assertEquals(actualPageTitle, expectedPageTitle);
        Reporter.addStepLog(String.format("page title validated \n expected: \"%s\" actual: \"%s\"", expectedPageTitle, actualPageTitle));
    }

    @Then("^the user validate that text at the element having (.+) \"([^\"]*)\" is \"(.*?)\" with the value \"(.*?)\"$")
    public void validateText(String locatorType, String locatorText, String matchType, String value) throws Throwable {
        value = parseValue(value);
        getMiscMethods().validateLocator(locatorType);
        getAssertionMethods().validateElementText(matchType, locatorType, value, locatorText);
    }

    @Then("^the user validates image at \"(.*?)\" url matches with \"([^\"]*)\" image file$")
    public void validateImageMatched(String imageUrl, String filename) throws Throwable {
        filename = parseValue(filename);
        imageUrl = parseValue(imageUrl);
        getImageFromUrl(imageUrl, Constants.ACTUAL_IMAGE_PATH, filename);
        boolean isVerified = compareScreenshot(new File(Constants.EXPECTED_IMAGE_PATH + filename), new File(Constants.ACTUAL_IMAGE_PATH + filename));
        Assert.assertTrue(isVerified);
    }

    @Then("^the user validate \\s*((?:not)?)\\s+see page title as \"(.+)\"$")
    public void check_title(String present, String title) throws TestCaseFailed {
        getAssertionMethods().checkTitle(title, present.isEmpty());
    }

    @Then("^the user should\\s*((?:not)?)\\s+see page title having partial text as \"(.*?)\"$")
    public void check_partial_text(String present, String partialTextTitle) throws TestCaseFailed {
        getAssertionMethods().checkPartialTitle(partialTextTitle, present.isEmpty());
    }

    @Then("^element having (.+) \"([^\"]*)\" should\\s*((?:not)?)\\s+have text as \"(.*?)\"$")
    public void check_element_text(String locatorType, String locatorText, String present, String value) throws Throwable {
        getMiscMethods().validateLocator(locatorType);
        getAssertionMethods().checkElementText(locatorType, value, locatorText, present.isEmpty());
    }

    @Then("^element having (.+) \"([^\"]*)\" should\\s*((?:not)?)\\s+have partial text as \"(.*?)\"$")
    public void check_element_partial_text(String locatorType, String locatorText, String present, String value) throws Throwable {
        getMiscMethods().validateLocator(locatorType);
        getAssertionMethods().checkElementPartialText(locatorType, value, locatorText, present.isEmpty());
    }

    @Then("^element having (.+) \"([^\"]*)\" should\\s*((?:not)?)\\s+have attribute \"(.*?)\" with value \"(.*?)\"$")
    public void check_element_attribute(String locatorType, String locatorText, String present, String attribute, String value) throws Throwable {
        getMiscMethods().validateLocator(locatorType);
        getAssertionMethods().checkElementAttribute(locatorType, attribute, value, locatorText, present.isEmpty());
    }

    @Then("^element having (.+) \"([^\"]*)\" should\\s*((?:not)?)\\s+be (enabled|disabled)$")
    public void check_element_enable(String locatorType, String locatorText, String present, String state) throws Throwable {
        getMiscMethods().validateLocator(locatorType);
        boolean flag = state.equals("enabled");
        if (!present.isEmpty()) {
            flag = !flag;
        }
        getAssertionMethods().checkElementEnable(locatorType, locatorText, flag);
    }

    @Then("^element having (.+) \"(.*?)\" should\\s*((?:not)?)\\s+be present$")
    public void check_element_presence(String locatorType, String locatorText, String present) throws Throwable {
        getMiscMethods().validateLocator(locatorType);
        getAssertionMethods().checkElementPresence(locatorType, locatorText, present.isEmpty());
    }

    @Then("^checkbox having (.+) \"(.*?)\" should be (checked|unchecked)$")
    public void is_checkbox_checked(String locatorType, String locatorText, String state) throws Throwable {
        getMiscMethods().validateLocator(locatorType);
        boolean flag = state.equals("checked");
        getAssertionMethods().isCheckboxChecked(locatorType, locatorText, flag);
    }

    @Then("^radio button having (.+) \"(.*?)\" should be (selected|unselected)$")
    public void is_radio_button_selected(String locatorType, String locatorText, String state) throws Throwable {
        getMiscMethods().validateLocator(locatorType);
        boolean flag = state.equals("selected");
        getAssertionMethods().isRadioButtonSelected(locatorType, locatorText, flag);
    }

    @Then("^option \"(.*?)\" by (.+) from radio button group having (.+) \"(.*?)\" should be (selected|unselected)$")
    public void is_option_from_radio_button_group_selected(String option, String attribute, String locatorType, String locatorText, String state) throws Throwable {
        getMiscMethods().validateLocator(locatorType);
        boolean flag = state.equals("selected");
        getAssertionMethods().isOptionFromRadioButtonGroupSelected(locatorType, attribute, option, locatorText, flag);
    }

    @Then("^link having text \"(.*?)\" should\\s*((?:not)?)\\s+be present$")
    public void check_element_presence(String locatorText, String present) throws Throwable {
        getAssertionMethods().checkElementPresence("linkText", locatorText, present.isEmpty());
    }

    @Then("^link having partial text \"(.*?)\" should\\s*((?:not)?)\\s+be present$")
    public void check_partial_element_presence(String locatorText, String present) throws Throwable {
        getAssertionMethods().checkElementPresence("partialLinkText", locatorText, present.isEmpty());
    }

    @Then("^the user should see alert text as \"(.*?)\"$")
    public void check_alert_text(String actualValue) throws TestCaseFailed {
        getAssertionMethods().checkAlertText(actualValue);
    }

}
