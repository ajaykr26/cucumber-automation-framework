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

public class BasePO extends PageObject {
    protected Logger logger = LogManager.getLogger(this.getClass().getName());

    public By getObjectByLocatorType(String locatorType, String locatorText) {
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

    public By getObject(String objectName, String pageName) {
        String classname = "pageobjects." + pageName;
        Class<?> classInstance = null;
        try {
            classInstance = Class.forName(classname);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Object object = null;
        By by = null;
        try {

            object = classInstance != null ? classInstance.newInstance() : null;
            String methodName = "get" + objectName.substring(0, 1).toUpperCase() + objectName.substring(1);
            assert object != null;
            Method method = object.getClass().getMethod("" + methodName);
            by = (By) method.invoke(object);

        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return by;
    }

    public WebElement getElement(String objectName, String pageName) throws Throwable {
        getWait().until(ExpectedConditions.presenceOfElementLocated(getObject(objectName, pageName)));
        return getDriver().findElement(getObject(objectName, pageName));

    }

    public List<WebElement> getElements(String objectName, String pageName) throws Throwable {
        getWait().until(ExpectedConditions.presenceOfElementLocated(getObject(objectName, pageName)));
        return getDriver().findElements(getObject(objectName, pageName));
    }

    public String parse_keyJSON(String string) {
        String parsed_data = null;

        Pattern patternJSON = Pattern.compile("#\\((.*)\\)");
        Matcher matcherJSON = patternJSON.matcher(string);

        if (matcherJSON.matches()) {
            return matcherJSON.group(1);
        } else {
            return null;
        }
    }

    public String parse_keyProps(String string) {
        String parsed_data = null;
        Pattern patternProp = Pattern.compile("@\\((.*)\\)");
        Matcher matcherProp = patternProp.matcher(string);

        if (matcherProp.matches()) {
            return matcherProp.group(1);
        } else {
            return null;
        }

    }

    public String parse_value(String string) {
        String parsed_value = null;
        String parsedkeyJSON = parse_keyJSON(string);
        String parsedkeyProps = parse_keyProps(string);
        if (parsedkeyJSON != null) {
            parsed_value = TestContext.getInstance().testdataGet(parsedkeyJSON).toString();
            logger.info("returning the value from JSON");
        } else if (parsedkeyProps != null) {
            parsed_value = Property.getProperty(Constants.ENVIRONMENT_PATH, parse_keyProps(string));
            logger.info("returning the value from config.properties file");
        } else {
            parsed_value = string;
            logger.info("returning the value as it is provided in the steps");
        }
        return parsed_value;
    }

    public void waitForPageLoad() {
        long timeOut = Integer.parseInt(Property.getProperty(Constants.RUNTIME_PATH, "waitForPageLoad"));
        long endTime = System.currentTimeMillis() + timeOut;
        while (System.currentTimeMillis() < endTime) {
            if (String.valueOf(((JavascriptExecutor) getDriver()).executeScript("return document.readyState")).equals("complete")) {
                logger.info("page loaded completely");
                break;
            } else {
                logger.info("error in page loading,  time out reached:");
            }
        }
    }
}
