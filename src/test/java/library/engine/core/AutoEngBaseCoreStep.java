package library.engine.core;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import io.cucumber.java8.En;
import library.common.Constants;
import library.common.Property;
import library.common.TestContext;
import library.engine.web.utils.*;
import library.reporting.Reporter;
import library.selenium.core.LocatorType;
import library.selenium.exec.BasePO;
import library.selenium.exec.driver.factory.DriverContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AutoEngBaseCoreStep implements En {
    protected Logger logger = LogManager.getLogger(this.getClass().getName());

    protected static final String REGEX_KEY = "(#\\(\\S*?\\))";
    protected static final String ERROR = "ERROR";
    protected static final String REGEX_VALIDATION = "\"([^\"]*)\"";
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
    private BasePO basePO;

    public AutoEngBaseCoreStep() {
    }

    public enum ScreenshotType {
        DISPLAY, AREA, SCROLLING;
    }

    public BasePO getBasePO() {
        logger.debug("obtaining the instance of base page object");
        if (basePO == null) {
            basePO = new BasePO();
        }
        return basePO;
    }

    public WebDriver getDriver() {
        logger.debug("obtaining an instance of base page object");
        return DriverContext.getInstance().getDriver();
    }

    public WebDriverWait getWait() {
        logger.debug("obtaining the wait for current thread");
        return DriverContext.getInstance().getDriverWait();
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

    public WebElement getElement(String objectName, String pageName) throws Throwable {
        getWait().until(ExpectedConditions.presenceOfElementLocated(getObject(objectName, pageName)));
        return getDriver().findElement(getObject(objectName, pageName));

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

    public SoftAssertions softAssertions() {
        return TestContext.getInstance().softAssertions();
    }

    public String replaceParameterValues(String stringToReplace) {
        StringBuffer stringBuffer = new StringBuffer();
        Pattern pattern = Pattern.compile(REGEX_KEY);
        Matcher matcher = pattern.matcher(stringToReplace);

        while (matcher.find()) {
            String fullKey = matcher.group(1);
            matcher.appendReplacement(stringBuffer, Matcher.quoteReplacement(getDictionaryValOrRealVal(fullKey)));
        }
        matcher.appendTail(stringBuffer);
        return stringBuffer.toString();
    }

    protected void logStepMessage(String message) {
        logStepMessage("DEBUG", message);
    }

    private void logStepMessage(String type, String message) {
        Reporter.addStepLog(type, message);
        logger.debug(message);
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
        return AutoEngCoreParser.getValueOrVariable(value);
    }

    protected Object parseValueToObject(String value) {
        return AutoEngCoreParser.getValueToObject(value);
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
