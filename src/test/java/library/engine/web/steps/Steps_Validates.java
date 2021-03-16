package library.engine.web.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java8.En;
import library.cucumber.core.CukesConstants;
import library.reporting.Reporter;
import library.selenium.exec.BasePO;
import org.assertj.core.api.Assertions;
import org.testng.Assert;
import org.testng.asserts.Assertion;

import java.io.File;

import static library.selenium.core.FactoryMethod.*;
import static library.selenium.core.Screenshot.compareScreenshot;
import static library.selenium.core.Screenshot.getImageFromUrl;

public class Steps_Validates extends BasePO implements En {

    @Then("^the user validate that the page title exactly matched with \"([^\"]*)\"$")
    public void validatePageTitle(String expectedPageTitle) throws Throwable {
        String actualPageTitle = getDriver().getTitle();
        Assert.assertEquals(actualPageTitle, expectedPageTitle);
        Reporter.addStepLog(String.format("page title validated \n expected: \"%s\" actual: \"%s\"", expectedPageTitle, actualPageTitle));
    }

    @Then("^the user validate that text at the element having (.+) \"([^\"]*)\" is \"(.*?)\" with the value \"(.*?)\"$")
    public void validateText(String type, String accessName, String matchType, String value) throws Throwable {
        value = parse_value(value);
        getMiscMethods().validateLocator(type);
        getAssertionMethods().validateElementText(matchType, type, value, accessName);
    }

    @Then("^the user validates image at \"(.*?)\" url matches with \"([^\"]*)\" image file$")
    public void validateImageMatched(String imageUrl, String filename) throws Throwable {
        filename = parse_value(filename);
        imageUrl = parse_value(imageUrl);
        getImageFromUrl(imageUrl, CukesConstants.ACTUAL_IMAGE_PATH, filename);
        boolean isVerified = compareScreenshot(new File(CukesConstants.EXPECTED_IMAGE_PATH + filename), new File(CukesConstants.ACTUAL_IMAGE_PATH + filename));
        Assert.assertTrue(isVerified);
    }
}
