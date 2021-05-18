package library.engine.web;

import io.cucumber.java8.En;
import library.common.Constants;
import library.common.Property;
import library.common.TestContext;
import library.engine.core.EngBaseStep;
import library.engine.web.utils.*;
import library.selenium.core.LocatorType;
import library.selenium.exec.BasePO;
import library.selenium.exec.driver.factory.DriverContext;
import library.selenium.utils.ClickMethods;
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

public class BaseStepsWeb extends EngBaseStep {

    public BaseStepsWeb() {
    }

    protected Logger logger = LogManager.getLogger(this.getClass().getName());

    public static ClickMethods getClickMethods() {
        return new ClickMethods();
    }

    public static AssertionMethods getAssertionMethods() {
        return new AssertionMethods();
    }

    public static TableMethods getTableMethods() {
        return new TableMethods();
    }

    public static MiscMethods getMiscMethods() {
        return new MiscMethods();
    }

    public static WaitMethods getWaitMethods() {
        return new WaitMethods();
    }

    public static InputMethods getInputMethods() {
        return new InputMethods();
    }

    public static NavigationMethods getNavigationMethods() {
        return new NavigationMethods();
    }

    public static JSMethods getJSMethods() {
        return new JSMethods();
    }

    public WebDriver getDriver() {
        logger.debug("obtaining the driver for current thread");
        return DriverContext.getInstance().getWebDriver();
    }

    public WebDriverWait getWait() {
        logger.debug("obtaining the wait for current thread");
        return DriverContext.getInstance().getWait();
    }

    public void waitForPageToLoad() {
        long timeOut = Integer.parseInt(Property.getProperty(Constants.RUNTIME_PATH, "waitForPageLoad")) * 1000;
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

    public By getObject(String objectName, String pageName) throws Throwable {
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

    public static String parseValue(String string) {
        String parsedValue = null;
        String parsedKeyJSON = parseKeyJSON(string);
        String parsedKeyProps = parseKeyProps(string);
        if (parsedKeyJSON != null) {
            parsedValue = TestContext.getInstance().testdataGet(parsedKeyJSON).toString();
        } else if (parsedKeyProps != null) {
            parsedValue = Property.getProperty(Constants.ENVIRONMENT_PATH, parseKeyProps(string));
        } else {
            parsedValue = string;
        }
        return parsedValue;
    }

    public static String parseKeyJSON(String string) {
        String dataParsed = null;

        Pattern patternJSON = Pattern.compile("#\\((.*)\\)");
        Matcher matcherJSON = patternJSON.matcher(string);

        if (matcherJSON.matches()) {
            return matcherJSON.group(1);
        } else {
            return null;
        }
    }

    public static String parseKeyProps(String string) {
        String dataParsed = null;
        Pattern patternProp = Pattern.compile("@\\((.*)\\)");
        Matcher matcherProp = patternProp.matcher(string);

        if (matcherProp.matches()) {
            return matcherProp.group(1);
        } else {
            return null;
        }

    }

}
