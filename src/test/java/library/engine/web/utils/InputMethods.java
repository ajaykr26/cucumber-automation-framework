package library.engine.web.utils;

import library.selenium.exec.BasePO;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;


public class InputMethods extends BasePO {

    private WebElement dropdown = null;
    private Select selectList = null;

    public void enterText(String locatorType, String text, String locatorText) {
        getWait().until(ExpectedConditions.presenceOfElementLocated(getObjectLocatedBy(locatorType, locatorText)));
        getDriver().findElement(getObjectLocatedBy(locatorType, locatorText)).sendKeys(text);
    }

    public void clearText(String locatorType, String locatorText) {
        getWait().until(ExpectedConditions.presenceOfElementLocated(getObject(locatorType, locatorText)));
        getDriver().findElement(getObject(locatorType, locatorText)).clear();
    }

    public void selectElementFromDropdownByType(Select select_list, String bytype, String option) {
        switch (bytype) {
            case "selectByIndex":
                int index = Integer.parseInt(option);
                select_list.selectByIndex(index - 1);
                break;
            case "value":
                select_list.selectByValue(option);
                break;
            case "text":
                select_list.selectByVisibleText(option);
                break;
        }
    }

    public void selectOptionFromDropdown(String locatorType, String optionBy, String option, String locatorText) {
        dropdown = getWait().until(ExpectedConditions.presenceOfElementLocated(getObjectLocatedBy(locatorType, locatorText)));
        selectList = new Select(dropdown);

        switch (optionBy) {
            case "selectByIndex":
                selectList.selectByIndex(Integer.parseInt(option) - 1);
                break;
            case "value":
                selectList.selectByValue(option);
                break;
            case "text":
                selectList.selectByVisibleText(option);
                break;
        }
    }

    public void selectOptionFromDropdown$(String option, String optionBy, String objectName, String pageName) {
        dropdown = getWait().until(ExpectedConditions.presenceOfElementLocated(getObject(objectName, pageName)));
        selectList = new Select(dropdown);

        switch (optionBy) {
            case "selectByIndex":
                selectList.selectByIndex(Integer.parseInt(option) - 1);
                break;
            case "value":
                selectList.selectByValue(option);
                break;
            case "text":
                selectList.selectByVisibleText(option);
                break;
        }
    }

    public void unselectAllOptionFromMultiselectDropdown(String locatorType, String locatorText) {
        dropdown = getWait().until(ExpectedConditions.presenceOfElementLocated(getObject(locatorType, locatorText)));
        selectList = new Select(dropdown);
        selectList.deselectAll();
    }

    public void deselectOptionFromDropdown(String locatorType, String optionBy, String option, String locatorText) {
        dropdown = getWait().until(ExpectedConditions.presenceOfElementLocated(getObject(locatorType, locatorText)));
        selectList = new Select(dropdown);

        switch (optionBy) {
            case "selectByIndex":
                selectList.deselectByIndex(Integer.parseInt(option) - 1);
                break;
            case "value":
                selectList.deselectByValue(option);
                break;
            case "text":
                selectList.deselectByVisibleText(option);
                break;
        }
    }

    public void deselectOptionFromDropdown$(String locatorType, String optionBy, String option, String locatorText) {
        dropdown = getWait().until(ExpectedConditions.presenceOfElementLocated(getObject(locatorType, locatorText)));
        selectList = new Select(dropdown);

        switch (optionBy) {
            case "selectByIndex":
                selectList.deselectByIndex(Integer.parseInt(option) - 1);
                break;
            case "value":
                selectList.deselectByValue(option);
                break;
            case "text":
                selectList.deselectByVisibleText(option);
                break;
        }
    }

    public void checkCheckbox(String locatorType, String locatorText) {
        WebElement checkbox = getWait().until(ExpectedConditions.presenceOfElementLocated(getObject(locatorType, locatorText)));
        if (!checkbox.isSelected())
            checkbox.click();
    }

    public void uncheckCheckbox(String locatorType, String locatorText) {
        WebElement checkbox = getWait().until(ExpectedConditions.presenceOfElementLocated(getObject(locatorType, locatorText)));
        if (checkbox.isSelected())
            checkbox.click();
    }

    public void toggleCheckbox(String locatorType, String locatorText) {
        getWait().until(ExpectedConditions.presenceOfElementLocated(getObject(locatorType, locatorText))).click();
    }

    public void selectRadioButton(String locatorType, String locatorText) {
        WebElement radioButton = getWait().until(ExpectedConditions.presenceOfElementLocated(getObject(locatorType, locatorText)));
        if (!radioButton.isSelected())
            radioButton.click();
    }

    public void selectOptionFromRadioButtonGroup(String locatorType, String option, String by, String locatorText) {
        List<WebElement> radioButtonGroup = getDriver().findElements(getObjectLocatedBy(locatorType, locatorText));
        for (WebElement rb : radioButtonGroup) {
            if (by.equals("value")) {
                if (rb.getAttribute("value").equals(option) && !rb.isSelected())
                    rb.click();
            } else if (by.equals("text")) {
                if (rb.getText().equals(option) && !rb.isSelected())
                    rb.click();
            }
        }
    }
}
