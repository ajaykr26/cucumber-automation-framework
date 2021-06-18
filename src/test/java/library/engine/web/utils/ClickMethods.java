package library.engine.web.utils;

import library.selenium.exec.BasePO;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class ClickMethods extends BasePO {
    private WebElement element = null;

    public void click(String accessType, String accessName) {
        element = getWait().until(ExpectedConditions.presenceOfElementLocated(getObjectLocatedBy(accessType, accessName)));
        element.click();
    }

    public void click(WebElement element) {
        element.click();
    }

    public void clickForcefully(String accessType, String accessName) {
        element = getWait().until(ExpectedConditions.presenceOfElementLocated(getObjectLocatedBy(accessType, accessName)));
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].click();", element);
    }

    public void clickJS(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].click();", element);
    }

    public void doubleClick(WebElement element) {
        Actions action = new Actions(getDriver());
        action.moveToElement(element).doubleClick().perform();
    }

    public void doubleClick(String accessType, String accessValue) {
        element = getWait().until(ExpectedConditions.presenceOfElementLocated(getObjectLocatedBy(accessType, accessValue)));

        Actions action = new Actions(getDriver());
        action.moveToElement(element).doubleClick().perform();

    }

    public void rightClick(String accessType, String accessValue) {
        element = getWait().until(ExpectedConditions.presenceOfElementLocated(getObjectLocatedBy(accessType, accessValue)));

        Actions action = new Actions(getDriver());
        action.moveToElement(element).contextClick().perform();
    }

    public void hoverElement(String accessType, String accessValue) {
        element = getWait().until(ExpectedConditions.presenceOfElementLocated(getObjectLocatedBy(accessType, accessValue)));

        Actions action = new Actions(getDriver());
        action.moveToElement(element).perform();
    }
}