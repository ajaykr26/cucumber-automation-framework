package library.cucumber.selenium;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import io.cucumber.java8.En;
import library.common.Constants;
import library.common.Property;
import library.common.TestContext;
import library.engine.core.EngParser;
import library.engine.core.objectmatcher.ObjectNotFoundException;
import library.engine.web.utils.*;
import library.reporting.Reporter;
import library.selenium.core.Element;
import library.selenium.exec.driver.factory.DriverContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static library.engine.core.EngParser.parseValue;

public class BaseSteps implements En {
    protected final Logger logger = LogManager.getLogger(BaseSteps.class);
    protected static final String ERROR = "ERROR";
    protected static final String REGEX_VALIDATION = "\"([^\"]*)\"";
    protected static final String REGEX_FULLKEY = "(#\\(\\S*?\\))";
    protected static final String ENV_PROP = "cukes.env";
    protected static final String PROPERTIES_EXT = ".properties";
    protected static final String CLICKED_VALUE = "Clicked value: \"%s\"";
    protected static final String SELECTED_VALUE = "Selected value: \"%s\"";
    protected static final String ENTERED_VALUE = "Entered value: \"%s\"";
    protected static final String STORED_VALUE = "Stored value: \"%s\" into key \"%s\"";
    protected static final String FORMATTED_AS = "Formatted as Day suffix {}";
    protected static final String JSON = ".json";
    protected static final String ZONE = "ZONE_";
    protected static final String ELEMENT_REF = "fw.elementRef";
    public static final String WINDOW_SWITCH_DELAY = "fw.windowSwitchDelay";
    public static final String COULD_NOT_FIND_UNIQUE_ROW = "Could not find unique row with {} having and {} having and {} having {}";

    public BaseSteps() {
    }

    public WebDriver getWebDriver() {
        logger.debug("obtaining an instance of base page object");
        return DriverContext.getInstance().getWebDriver();
    }

    public List<WebElement> getElements(String objectName, String pageName) throws Throwable {
        getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(getObject(objectName, pageName)));
        return getWebDriver().findElements(getObject(objectName, pageName));
    }

    public WebDriverWait getWebDriverWait() {
        logger.debug("obtaining the wait for current thread");
        return DriverContext.getInstance().getWebDriverWait();
    }

    public void waitForPageToLoad() {
        logStepMessage("waiting for page to load");
        long timeOut = Integer.parseInt(Property.getProperty(Constants.RUNTIME_PATH, "waitForPageLoad")) * 1000L;
        long endTime = System.currentTimeMillis() + timeOut;
        while (System.currentTimeMillis() < endTime) {
            if (String.valueOf(((JavascriptExecutor) getWebDriver()).executeScript("return document.readyState")).equals("complete")) {
                logger.info("page loaded completely");
                break;
            } else {
                logger.info("error in page loading,  time out reached:");
            }
        }
    }

    public By getObject(String objectName, String pageName) throws Throwable {
        String fullClassname = "pageobjects." + pageName;
        Class<?> classInstance = null;
        Object classObject;
        By byObject = null;
        try {
            classInstance = Class.forName(fullClassname);
            classObject = classInstance.newInstance();
            String methodName = "get" + objectName.substring(0, 1).toUpperCase() + objectName.substring(1);
            Method method = classObject.getClass().getMethod("" + methodName);
            byObject = (By) method.invoke(classObject);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return byObject;
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

    public enum ScreenshotType {
        DISPLAY, AREA, SCROLLING;
    }

    public SoftAssertions softAssertions() {
        return TestContext.getInstance().softAssertions();
    }

    public String replaceParameterValues(String stringToReplace) {
        StringBuffer stringBuffer = new StringBuffer();
        Pattern pattern = Pattern.compile(REGEX_FULLKEY);
        Matcher matcher = pattern.matcher(stringToReplace);

        while (matcher.find()) {
            String fullKey = matcher.group(1);
            matcher.appendReplacement(stringBuffer, Matcher.quoteReplacement(getDictionaryValOrRealVal(fullKey)));
        }
        matcher.appendTail(stringBuffer);
        return stringBuffer.toString();
    }

    protected String getDictionaryValOrRealVal(String value) {
        try {
            return parseValue(value);
        } catch (IllegalArgumentException exception) {
            logger.error(String.format("%s string will not include value at key.", exception.getMessage()));
        }
        return null;
    }

    protected String parseDictionaryKey(String keyName) {
        return getValueOrVariable(keyName);
    }

    private String getValueOrVariable(String value) {
        return EngParser.getValueOrVariable(value);
    }

    protected Object parseValueToObject(String value) {
        return EngParser.getValueToObject(value);
    }

    protected void logStepMessage(String message) {
        logStepMessage("DEBUG", message);
    }

    private void logStepMessage(String type, String message) {
        Reporter.addStepLog(type, message);
        logger.debug(message);
    }

    protected List<String> getListFromDictionary(String dictionaryKey) {
        try {
            final Object jsonArray = TestContext.getInstance().testdataGet(parseDictionaryKey(dictionaryKey));
            if (jsonArray != null) {
                if (jsonArray instanceof List) {
                    return (List<String>) jsonArray;
                } else {
                    return new Gson().fromJson(jsonArray.toString(), List.class);
                }
            }
        } catch (JsonSyntaxException exception) {
            logStepMessage(String.format("did not find a json array. found: %s. error: %s", TestContext.getInstance().testdataGet(dictionaryKey)), exception.getMessage());
        }
        return Collections.emptyList();
    }
}
