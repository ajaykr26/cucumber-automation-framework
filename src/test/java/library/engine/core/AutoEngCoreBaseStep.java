package library.engine.core;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import io.cucumber.java8.En;
import library.common.Constants;
import library.common.Property;
import library.common.TestContext;
import library.engine.core.objectmatcher.ObjectNotFoundException;
import library.reporting.Reporter;
import library.selenium.core.Element;
import library.selenium.core.LocatorType;

import library.selenium.exec.BasePO;

import library.selenium.exec.driver.factory.DriverContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static library.engine.core.AutoEngCoreParser.parseValue;
import static library.engine.core.objectmatcher.ObjectFinder.getMatchingElement;
import static library.engine.core.objectmatcher.ObjectFinder.getMatchingObject;

public class AutoEngCoreBaseStep implements En {

    protected final Logger logger = LogManager.getLogger(AutoEngCoreBaseStep.class);
    protected static final String ERROR = "ERROR";
    protected static final String REGEX_VALIDATION = "\"([^\"]*)\"";
    protected static final String REGEX_KEY = "(#\\(\\S*?\\))";
    protected static final String ENV_PROP = "cukes.env";
    protected static final String PROPERTIES_EXT = ".properties";
    protected static final String CLICKED_VALUE = "Clicked value: \"%s\"";
    protected static final String SELECTED_VALUE = "Selected value: \"%s\"";
    protected static final String ENTERED_VALUE = "Entered value: \"%s\"";
    protected static final String STORED_VALUE = "Stored value: \"%s\" into key \"%s\"";
    protected static final String FORMATTED_AS = "Formatted as Day suffix {}";
    protected static final String JSON = ".json";
    protected static final String ZONE = "ZONE_";
    protected static final String JAVAX_NET_SSL_TRUST_STORE = "javax.net.ssl.trustStore";

    protected static final String ELEMENT_REF = "fw.elementRef";
    public static final String WINDOW_SWITCH_DELAY = "fw.windowSwitchDelay";
    public static final String COULD_NOT_FIND_UNIQUE_ROW = "Could not find unique row with {} having and {} having and {} having {}";
    private BasePO baseWebPO;
    protected static WebElement element = null;
    protected static By byObject = null;
    protected static List<WebElement> elements = new ArrayList<>();
    protected static String currentWindowHandle;
    protected static String defaultWindowHandle;

    protected static Set<String> windowHandles;


    public AutoEngCoreBaseStep() {
    }

    public enum ScreenshotType {
        DISPLAY, AREA, SCROLLING;
    }

    public WebDriver getDriver() {
        logger.debug("obtaining an instance of base page object");
        return DriverContext.getInstance().getDriver();
    }

    public WebDriverWait getWait() {
        logger.debug("obtaining the wait for current thread");
        return DriverContext.getInstance().getDriverWait();
    }

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

    public WebElement getElementLocatedBy(String locatorType, String locatorValue) {
        byObject = getObjectLocatedBy(locatorType, locatorValue);
        getWait().until(ExpectedConditions.presenceOfElementLocated(byObject));
        return getDriver().findElement(byObject);

    }

    public List<WebElement> getElementsLocatedBy(String locatorType, String locatorValue) {
        byObject = getObjectLocatedBy(locatorType, locatorValue);
        getWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(byObject));
        return getDriver().findElements(byObject);
    }

    public By getObject(String objectName, String pageName) {
        String methodName = "get" + objectName.substring(0, 1).toUpperCase() + objectName.substring(1);
        return getMatchingObject(methodName, pageName);
    }

    protected Element getElementFromPageObject(String objectName, String pageName) {
        Object object = getMatchingElement(objectName, pageName);
        Element element;
        if (object != null) {
            element = (Element) object;
            if (element.element() != null) {
                TestContext.getInstance().testdataPut(ELEMENT_REF, element);
                return element;
            } else {
                String errorMsg = String.format("the \"%s\" element is not present on the page: \"%s\"", objectName, pageName);
                throw new NotFoundException(errorMsg);
            }
        } else {
            throw new ObjectNotFoundException(String.format("the \"%s\" element is not present on the page: \"%s\"", objectName, pageName));
        }
    }

    public List<WebElement> getElements(String objectName, String pageName) {
        getWait().until(ExpectedConditions.presenceOfElementLocated(getObject(objectName, pageName)));
        return getDriver().findElements(getObject(objectName, pageName));
    }

    public WebElement getElement(String objectName, String pageName) {
        getWait().until(ExpectedConditions.presenceOfElementLocated(getObject(objectName, pageName)));
        return getDriver().findElement(getObject(objectName, pageName));
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

    protected void logStepMessage(String message) {
        logStepMessage("DEBUG", message);
    }

    private void logStepMessage(String type, String message) {
        Reporter.addStepLog(type, message);
        logger.debug(message);
    }

    protected String setTrustStoreBasedOnEnv() {
        String envPropsFile = Constants.ENVIRONMENTS;
        String certFile = Property.getProperty(envPropsFile, "certFileForEnv");
        if (certFile != null) {
            String pathForCerts = Paths.get(Constants.ENVIRONMENTS + certFile).toAbsolutePath().toString();
            String currentCertFile = Property.getVariable(JAVAX_NET_SSL_TRUST_STORE);
            logger.debug(currentCertFile);
            return currentCertFile;
        } else {
            logger.warn("path not defined");
            return "";
        }
    }

    protected void setTrustStore(String pathToCertFile) {
        if (pathToCertFile != null) {
            System.setProperty(JAVAX_NET_SSL_TRUST_STORE, pathToCertFile);
            logger.debug("switching {} to {}", JAVAX_NET_SSL_TRUST_STORE, pathToCertFile);
        }
    }

}
