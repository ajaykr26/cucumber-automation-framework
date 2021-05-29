package library.selenium.core;

import library.common.Constants;
import library.common.Property;
import library.engine.core.AutoEngCoreConstants;
import library.selenium.exec.driver.factory.DriverContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Element {

    private WebElement webElement;
    private WebDriver driver;
    private WebDriverWait wait;
    private By by;
    private static Logger logger = LogManager.getLogger(Element.class);


    public Element(WebDriver driver, WebElement element) {
        this.driver = driver;
        wait = new WebDriverWait(driver, getWaitDuration());
        this.webElement = element;
    }

    public Element(WebDriver driver, WebElement element, By by) {
        this.driver = driver;
        wait = new WebDriverWait(driver, getWaitDuration());
        this.webElement = element;
        this.by = by;
    }

    public Element(WebDriver driver, By by, int... delay) {
        this.driver = driver;
        this.by = by;
        try {
            wait = new WebDriverWait(driver, delay.length > 0 ? delay[0] : getWaitDuration());
            this.webElement = wait.until(ExpectedConditions.presenceOfElementLocated(by));
            logger.debug("element located successfully: {}", by);
        } catch (Exception exception) {
            this.webElement = null;
            logger.debug("element not located: {}", by);
            logger.debug(exception.getMessage());
        }
    }

    public Element(WebDriver driver, ExpectedCondition<?> expectedCondition, int... delay) {
        this.driver = driver;
        this.by = by;
        try {
            wait = new WebDriverWait(driver, delay.length > 0 ? delay[0] : getWaitDuration());
            this.webElement = (WebElement) wait.until(expectedCondition);
            logger.debug("element located successfully: {}", by);
        } catch (Exception exception) {
            this.webElement = null;
            logger.debug("element not located: {}", by);
            logger.debug(exception.getMessage());
        }
    }

    public By by() {
        return by;
    }

    public WebElement element() {
        return webElement;
    }

    public Element $(By by) {
        return new Element(driver, (WebElement) wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(this.webElement, by)), by);
    }

    public List<Element> $$(By by) {
        List<WebElement> elementList = (List<WebElement>) wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(this.webElement, by));
        List<Element> list = new ArrayList<>();
        for (WebElement element : elementList) {
            list.add(new Element(driver, element));
        }
        return list;
    }

    public int getWaitDuration() {
        final int defaultWit = 10;
        int duration;
        try {
            duration = Objects.requireNonNull(Property.getProperties(Constants.RUNTIME_PROP)).getInt("defaultWait");
        } catch (Exception e) {
            duration = defaultWit;
        }
        return duration;
    }

    public Element scroll() {
        return new Element(driver, by);
    }

    public Element visible(int... retries) {
        try {
            wait.until(ExpectedConditions.visibilityOf(this.webElement));
        } catch (Exception e) {
            if (!(retries.length > 0 && retries[0] == 0)) {
                this.refind(retries);
                return this.visible(0);
            } else {
                throw e;
            }
        }
        return this;
    }

    public Element invisible(int... retries) {
        try {
            wait.until(ExpectedConditions.invisibilityOf(this.webElement));
        } catch (Exception e) {
            if (!(retries.length > 0 && retries[0] == 0)) {
                this.refind(retries);
                return this.invisible(0);
            } else {
                throw e;
            }
        }
        return this;
    }

    public Element enabled(int... retries) {
        try {
            wait.until((ExpectedCondition<Boolean>) webDriver -> this.webElement.isEnabled());
        } catch (Exception e) {
            if (!(retries.length > 0 && retries[0] == 0)) {
                this.refind(retries);
                return this.enabled(0);
            } else {
                throw e;
            }
        }
        return this;
    }

    public Element disabled(int... retries) {
        try {
            wait.until((ExpectedCondition<Boolean>) webDriver -> !this.webElement.isEnabled());
        } catch (Exception e) {
            if (!(retries.length > 0 && retries[0] == 0)) {
                this.refind(retries);
                return this.disabled(0);
            } else {
                throw e;
            }
        }
        return this;
    }

    public Element displayed(int... retries) {
        try {
            wait.until((ExpectedCondition<Boolean>) webDriver -> this.webElement.isDisplayed());
        } catch (Exception e) {
            if (!(retries.length > 0 && retries[0] == 0)) {
                this.refind(retries);
                return this.displayed(0);
            } else {
                throw e;
            }
        }
        return this;
    }

    public Element clickable(int... retries) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(this.webElement));
        } catch (Exception e) {
            if (!(retries.length > 0 && retries[0] == 0)) {
                this.refind(retries);
                return this.clickable(0);
            } else {
                throw e;
            }
        }
        return this;
    }

    public boolean notClickable(int... retries) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(this.webElement));
        } catch (Exception e) {
            if (!(retries.length > 0 && retries[0] == 0)) {
                this.refind(retries);
                return this.clickable(0) != null;
            } else {
                throw e;
            }
        }
        return false;
    }

    public Element empty(int... retries) {
        try {
            if (this.getValue() != null) {
                wait.until((ExpectedCondition<Boolean>) webDriver -> this.getValue().isEmpty());
            } else {
                wait.until((ExpectedCondition<Boolean>) webDriver -> this.getText().isEmpty());
            }
        } catch (Exception e) {
            if (!(retries.length > 0 && retries[0] == 0)) {
                this.refind(retries);
                return this.empty(0);
            } else {
                throw e;
            }
        }
        return this;
    }

    public Element notEmpty(int... retries) {
        try {
            if (this.getValue() != null) {
                wait.until((ExpectedCondition<Boolean>) webDriver -> this.getValue().length() != 0);
            } else {
                wait.until((ExpectedCondition<Boolean>) webDriver -> this.getText().length() != 0);
            }
        } catch (Exception e) {
            if (!(retries.length > 0 && retries[0] == 0)) {
                this.refind(retries);
                return this.notEmpty(0);
            } else {
                throw e;
            }
        }
        return this;
    }

    public String getText(int... retries) {
        String str = null;
        try {
            str = this.webElement.getText();
        } catch (Exception e) {
            if (!(retries.length > 0 && retries[0] == 0)) {
                this.refind(retries);
                return this.getText(0);
            } else {
                throw e;
            }
        }
        return str;
    }

    public String getValue(int... retries) {
        String str = null;
        try {
            str = this.webElement.getAttribute("value");
        } catch (Exception e) {
            if (!(retries.length > 0 && retries[0] == 0)) {
                this.refind(retries);
                return this.getValue(0);
            } else {
                throw e;
            }
        }
        return str;
    }

    public String getAttribute(String attribute, int... retries) {
        try {
            return this.webElement.getAttribute(attribute);
        } catch (Exception e) {
            if (!(retries.length > 0 && retries[0] == 0)) {
                this.refind(retries);
                return this.getAttribute(attribute, 0);
            } else {
                throw e;
            }
        }
    }

    public String getTagName(int... retries) {
        try {
            return this.webElement.getTagName();
        } catch (Exception e) {
            if (!(retries.length > 0 && retries[0] == 0)) {
                this.refind(retries);
                return this.getTagName(0);
            } else {
                throw e;
            }
        }
    }

    public Element clear(int... retries) {
        try {
            this.webElement.clear();
        } catch (Exception e) {
            if (!(retries.length > 0 && retries[0] == 0)) {
                this.refind(retries);
                return this.clear(0);
            } else {
                throw e;
            }
        }
        return this;
    }

    public Element sendKeys(String value, int... retries) {
        try {
            try {
                this.webElement.sendKeys(value);
            } catch (Exception e) {
                if (!(retries.length > 0 && retries[0] == 0)) {
                    this.refind(retries);
                    return this.sendKeys(value, 0);
                } else {
                    throw e;
                }
            }
        } catch (Exception exception) {
            if (checkSendKeysJS()) {
                sendKeysJS(value);
            }
        }
        return this;
    }

    public Element sendKeysChord(String value, int... retries) {
        try {
            this.webElement.sendKeys(Keys.chord(Keys.CONTROL, "a"), value);
        } catch (Exception e) {
            if (!(retries.length > 0 && retries[0] == 0)) {
                this.refind(retries);
                return this.sendKeysChord(value, 0);
            } else {
                throw e;
            }
        }
        return this;
    }

    public Element sendKeysChord(Keys keys, int... retries) {
        try {
            this.webElement.sendKeys(Keys.chord(Keys.CONTROL, "a"), keys);
        } catch (Exception e) {
            if (!(retries.length > 0 && retries[0] == 0)) {
                this.refind(retries);
                return this.sendKeysChord(keys, 0);
            } else {
                throw e;
            }
        }
        return this;
    }

    public Element sendKeysEnter(int... retries) {
        try {
            this.webElement.sendKeys(Keys.chord(Keys.ENTER));
        } catch (Exception e) {
            if (!(retries.length > 0 && retries[0] == 0)) {
                this.refind(retries);
                return this.sendKeysEnter(0);
            } else {
                throw e;
            }
        }
        return this;
    }

    public boolean checkSendKeysJS() {
        return Property.getProperties(Constants.RUNTIME_PROP).getBoolean("sendKeysUsesJavaScript." + DriverContext.getInstance().getBrowserName().replaceAll("\\s", ""), false);
    }

    public Element sendKeysJS(String value) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('value', '" + value + "')", this.webElement);
        return this;
    }

    public Element focus(int... retries) {
        try {
            new Actions(driver).moveToElement(this.element()).perform();
            driver.switchTo().activeElement();
        } catch (Exception e) {
            if (!(retries.length > 0 && retries[0] == 0)) {
                this.refind(retries);
                return this.focus(0);
            } else {
                throw e;
            }
        }
        return this;
    }

    public Element refind(int... retries) {
        logger.info("attempting to refind the element: {}", by);
        int attempt = 0;
        boolean retry = true;
        int maxRetry = retries.length > 0 ? retries[0] : getFindRetries();
        while (attempt < maxRetry && retry) {
            try {
                logger.debug("retry number {}", attempt);
                this.webElement = wait.until(ExpectedConditions.presenceOfElementLocated(by));
                this.webElement.getTagName();
                retry = false;
            } catch (Exception e) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException interruptedException) {
                    logger.debug(interruptedException.getMessage());
                }
            }
            attempt++;
        }
        return this;
    }

    public static int getFindRetries() {
        final int defaultFindRetries = 10;
        int refind;
        try {
            refind = Integer.parseInt(System.getProperty("cukes.selenium.defaultFindRetries"));
        } catch (Exception exception) {
            refind = defaultFindRetries;
        }
        return refind;
    }

    public Element click(int... retries) {
        try {
            try {
                this.webElement.click();
            } catch (ElementClickInterceptedException e) {
                if (checkClickJS()) {
                    clickJS();
                }
            } catch (Exception exception) {
                if (!(retries.length > 0 && retries[0] == 0)) {
                    this.refind(retries);
                    return this.click(0);
                } else {
                    throw exception;
                }
            }
        } catch (Exception exception) {
            if (checkClickJS()) {
                clickJS();
            }
        }
        return this;
    }

    public boolean checkClickJS() {
        return Property.getProperties(Constants.RUNTIME_PROP).getBoolean("clickUsesJavaScript." + DriverContext.getInstance().getBrowserName().replaceAll("\\s", ""), false);
    }

    public Element clickJS() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click);", this.webElement);
        return this;
    }

    public Element doubleClick(int... retries) {
        Actions actions = new Actions(driver);
        try {
            actions.doubleClick(this.webElement).build().perform();
        } catch (Exception e) {
            if (!(retries.length > 0 && retries[0] == 0)) {
                this.refind(retries);
                return this.doubleClick(0);
            } else {
                throw e;
            }
        }
        return this;
    }

    public Element rightClick(int... retries) {
        Actions actions = new Actions(driver);
        try {
            actions.contextClick(this.webElement).build().perform();
        } catch (Exception e) {
            if (!(retries.length > 0 && retries[0] == 0)) {
                this.refind(retries);
                return this.rightClick(0);
            } else {
                throw e;
            }
        }
        return this;
    }

    public Element select(String value, int... retries) {
        try {
            if (!this.webElement.isSelected())
                this.webElement.click();
        } catch (Exception exception) {
            if (!(retries.length > 0 && retries[0] == 0)) {
                this.refind(retries);
                return this.select(value, 0);
            } else {
                throw exception;
            }
        }
        return this;
    }

    public Element unSelect(String value, int... retries) {
        try {
            if (this.webElement.isSelected())
                this.webElement.click();
        } catch (Exception exception) {
            if (!(retries.length > 0 && retries[0] == 0)) {
                this.refind(retries);
                return this.unSelect(value, 0);
            } else {
                throw exception;
            }
        }
        return this;
    }


}
