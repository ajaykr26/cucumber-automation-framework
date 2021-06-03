package library.engine.web.utils;

import library.common.TestContext;
import library.reporting.Reporter;
import library.selenium.exec.BasePO;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static library.engine.core.AutoEngCoreParser.parseValue;

public class AssertionMethods extends BasePO {

    private WebElement element = null;

    public String getPageTitle() {
        return getDriver().getTitle();
    }

    public void checkTitle(String title, boolean testCase) throws TestCaseFailed {
        String pageTitle = getPageTitle();

        if (testCase) {
            if (!pageTitle.equals(title))
                throw new TestCaseFailed("Page Title Not Matched, Actual Page Title : " + pageTitle);
        } else {
            if (pageTitle.equals(title))
                throw new TestCaseFailed("Page Title Matched, Actual Page Title : " + pageTitle);
        }
    }

    public void checkPartialTitle(String partialTitle, boolean testCase) throws TestCaseFailed {
        String pageTitle = getPageTitle();
        if (testCase) {
            if (!pageTitle.contains(partialTitle))
                throw new TestCaseFailed("Partial Page Title Not Present, Actual Page Title : " + pageTitle);
        } else {
            if (pageTitle.contains(partialTitle))
                throw new TestCaseFailed("Partial Page Title Present, Actual Page Title : " + pageTitle);
        }
    }

    public String getElementText(String locatorType, String locatorText) {
        element = getWait().until(ExpectedConditions.presenceOfElementLocated(getObjectLocatedBy(locatorType, locatorText)));
        return element.getText();

    }

    public void StoreElementText(String locatorType, String locatorText, String dictionaryKey) {
        dictionaryKey = parseValue(dictionaryKey);

        element = getWait().until(ExpectedConditions.presenceOfElementLocated(getObject(locatorType, locatorText)));
        String elementText = element.getText();
        TestContext.getInstance().testdataPut(dictionaryKey, elementText);

    }

    public String getElementText$(String objectName, String pageName) throws Throwable {
        element = getWait().until(ExpectedConditions.presenceOfElementLocated(getObject(objectName, pageName)));
        return element.getText();

    }

    public void checkElementText(String locatorType, String actualValue, String locatorText, boolean testCase) throws TestCaseFailed {
        String elementText = getElementText(locatorType, locatorText);

        if (testCase) {
            if (!elementText.equals(actualValue))
                throw new TestCaseFailed("Text Not Matched");
        } else {
            if (elementText.equals(actualValue))
                throw new TestCaseFailed("Text Matched");
        }
    }

    public void validateElementText(String matchType, String locatorType, String actualValue, String locatorText) throws TestCaseFailed {
        String elementText = getElementText(locatorType, locatorText);
        switch (matchType) {
            case "exact-match":
                if (!elementText.equals(actualValue)) {
                    Reporter.addStepLog("Text Matched");
                }
            case "not matched":
                if (!elementText.equals(actualValue)) {
                    Reporter.addStepLog("Text Not Matched");
                } else {
                    throw new TestCaseFailed(String.format("Expected: not \"%s\" but was found \"%s\"", actualValue, elementText));
                }
        }

    }

    public void checkElementPartialText(String locatorType, String actualValue, String locatorText, boolean testCase) throws TestCaseFailed {
        String elementText = getElementText(locatorType, locatorText);

        if (testCase) {
            if (!elementText.contains(actualValue))
                throw new TestCaseFailed("Text Not Matched");
        } else {
            if (elementText.contains(actualValue))
                throw new TestCaseFailed("Text Matched");
        }
    }

    public boolean isElementEnabled(String locatorType, String locatorText) {
        element = getWait().until(ExpectedConditions.presenceOfElementLocated(getObject(locatorType, locatorText)));
        return element.isEnabled();
    }

    public void checkElementEnable(String locatorType, String locatorText, boolean testCase) throws TestCaseFailed {
        boolean result = isElementEnabled(locatorType, locatorText);
        if (testCase) {
            if (!result)
                throw new TestCaseFailed("Element Not Enabled");
        } else {
            if (result)
                throw new TestCaseFailed("Element Enabled");
        }
    }

    public String getElementAttribute(String locatorType, String locatorText, String attributeName) {
        element = getWait().until(ExpectedConditions.presenceOfElementLocated(getObject(locatorType, locatorText)));
        return element.getAttribute(attributeName);
    }

    public void checkElementAttribute(String locatorType, String attributeName, String attributeValue, String locatorText, boolean testCase) throws TestCaseFailed {
        String attrVal = getElementAttribute(locatorType, locatorText, attributeName);
        if (testCase) {
            if (!attrVal.equals(attributeValue))
                throw new TestCaseFailed("Attribute Value Not Matched");
        } else {
            if (attrVal.equals(attributeValue))
                throw new TestCaseFailed("Attribute Value Matched");
        }
    }

    public boolean isElementDisplayed(String locatorType, String locatorText) {
        element = getWait().until(ExpectedConditions.presenceOfElementLocated(getObject(locatorType, locatorText)));
        return element.isDisplayed();
    }

    public void checkElementPresence(String locatorType, String locatorText, boolean testCase) throws TestCaseFailed {
        if (testCase) {
            if (!isElementDisplayed(locatorType, locatorText))
                throw new TestCaseFailed("Element Not Present");
        } else {
            try {
                if (isElementDisplayed(locatorType, locatorText))
                    throw new Exception("Present"); //since it is negative test and we found element
            } catch (Exception e) {
                if (e.getMessage().equals("Present")) //only raise if it present
                    throw new TestCaseFailed("Element Present");
            }
        }
    }

    public void isCheckboxChecked(String locatorType, String locatorText, boolean shouldBeChecked) throws TestCaseFailed {
        WebElement checkbox = getWait().until(ExpectedConditions.presenceOfElementLocated(getObject(locatorType, locatorText)));
        if ((!checkbox.isSelected()) && shouldBeChecked)
            throw new TestCaseFailed("Checkbox is not checked");
        else if (checkbox.isSelected() && !shouldBeChecked)
            throw new TestCaseFailed("Checkbox is checked");
    }

    public void isRadioButtonSelected(String locatorType, String locatorText, boolean shouldBeSelected) throws TestCaseFailed {
        WebElement radioButton = getWait().until(ExpectedConditions.presenceOfElementLocated(getObject(locatorType, locatorText)));
        if ((!radioButton.isSelected()) && shouldBeSelected)
            throw new TestCaseFailed("Radio Button not selected");
        else if (radioButton.isSelected() && !shouldBeSelected)
            throw new TestCaseFailed("Radio Button is selected");
    }

    public void isOptionFromRadioButtonGroupSelected(String locatorType, String by, String option, String locatorText, boolean shouldBeSelected) throws TestCaseFailed {
        List<WebElement> radioButtonGroup = getWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(getObject(locatorType, locatorText)));

        for (WebElement rb : radioButtonGroup) {
            if (by.equals("value")) {
                if (rb.getAttribute("value").equals(option)) {
                    if ((!rb.isSelected()) && shouldBeSelected)
                        throw new TestCaseFailed("Radio Button not selected");
                    else if (rb.isSelected() && !shouldBeSelected)
                        throw new TestCaseFailed("Radio Button is selected");
                }
            } else if (rb.getText().equals(option)) {
                if ((!rb.isSelected()) && shouldBeSelected)
                    throw new TestCaseFailed("Radio Button not selected");
                else if (rb.isSelected() && !shouldBeSelected)
                    throw new TestCaseFailed("Radio Button is selected");
            }
        }
    }

    public String getAlertText() {
        return getDriver().switchTo().alert().getText();
    }

    public void checkAlertText(String text) throws TestCaseFailed {
        if (!getAlertText().equals(text))
            throw new TestCaseFailed("Text on alert pop up not matched");
    }

    public void isOptionFromDropdownSelected(String locatorType, String by, String option, String locatorText, boolean shouldBeSelected) throws TestCaseFailed {
        Select selectList = null;
        WebElement dropdown = getWait().until(ExpectedConditions.presenceOfElementLocated(getObject(locatorType, locatorText)));
        selectList = new Select(dropdown);

        String actualValue = "";
        if (by.equals("text"))
            actualValue = selectList.getFirstSelectedOption().getText();
        else
            actualValue = selectList.getFirstSelectedOption().getAttribute("value");

        if ((!actualValue.equals(option)) && (shouldBeSelected))
            throw new TestCaseFailed("Option Not Selected From Dropwdown");
        else if ((actualValue.equals(option)) && (!shouldBeSelected))
            throw new TestCaseFailed("Option Selected From Dropwdown");
    }
}
