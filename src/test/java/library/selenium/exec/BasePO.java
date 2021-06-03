package library.selenium.exec;

import library.common.Constants;
import library.common.Property;
import library.common.TestContext;
import library.selenium.core.LocatorType;
import library.selenium.core.PageObject;
import library.selenium.exec.driver.factory.DriverContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static library.engine.core.objectmatcher.ObjectFinder.getMatchingObject;

public class BasePO extends PageObject {

    protected Logger logger = LogManager.getLogger(this.getClass().getName());

    public By getObjectLocatedBy(String locatorType, String locatorText) {
        switch (LocatorType.get(locatorType)) {
            case ID:
                return By.id(locatorText);
            case NAME:
                return By.name(locatorText);
            case CLASS_NAME:
                return By.className(locatorText);
            case XPATH:
                return By.xpath(locatorText);
            case CSS:
                return By.cssSelector(locatorText);
            case LINK_TEXT:
                return By.linkText(locatorText);
            case PARTIAL_LINK_TEXT:
                return By.partialLinkText(locatorText);
            case TAGE_NAME:
                return By.tagName(locatorText);
            default:
                return null;

        }
    }

    public WebElement getElementLocatedBy(String locatorType, String locatorValue) throws Throwable {
        By byObject = getObjectLocatedBy(locatorType, locatorValue);
        getWait().until(ExpectedConditions.presenceOfElementLocated(byObject));
        return getDriver().findElement(byObject);

    }

    public List<WebElement> getElementsLocatedBy(String locatorType, String locatorValue) {
        By byObject = getObjectLocatedBy(locatorType, locatorValue);
        getWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(byObject));
        return getDriver().findElements(byObject);
    }


    public By getObject(String objectName, String pageName) {
        String methodName = "get" + objectName.substring(0, 1).toUpperCase() + objectName.substring(1);
        return getMatchingObject(methodName, pageName);
    }

    public WebElement getElement(String objectName, String pageName) throws Throwable {
        By byObject = getObject(objectName, pageName);
        getWait().until(ExpectedConditions.presenceOfElementLocated(byObject));
        return getDriver().findElement(byObject);


    }

    public List<WebElement> getElements(String objectName, String pageName) throws Throwable {
        By byObject = getObject(objectName, pageName);
        getWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(byObject));
        return getDriver().findElements(byObject);
    }



    }

    public List<WebElement> getElements(String objectName, String pageName) throws Throwable {
        By byObject = getObject(objectName, pageName);
        getWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(byObject));
        return getDriver().findElements(byObject);
    }

}
