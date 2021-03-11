package library.selenium.utils;

import library.selenium.exec.BasePO;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class ClickMethods extends BasePO {
    private WebElement element = null;

    public void click(String accessType, String accessName) {
        element = getWait().until(ExpectedConditions.presenceOfElementLocated(getObjectBy(accessType, accessName)));
        element.click();
    }

    public void clickForcefully(String accessType, String accessName) {
        element = getWait().until(ExpectedConditions.presenceOfElementLocated(getObjectBy(accessType, accessName)));
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].click();", element);
    }

    public void doubleClick(String accessType, String accessValue) {
        element = getWait().until(ExpectedConditions.presenceOfElementLocated(getObjectBy(accessType, accessValue)));

        Actions action = new Actions(getDriver());
        action.moveToElement(element).doubleClick().perform();
    }
}