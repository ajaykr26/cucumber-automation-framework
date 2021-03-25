package library.engine.web.utils;

import library.selenium.exec.BasePO;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class WaitMethods extends BasePO {

    public void wait(String time) throws NumberFormatException, InterruptedException {
        Thread.sleep(Integer.parseInt(time) * 1000);
    }

    public void waitForElementToDisplay(String locatorType, String locatorText, String duration) {
        By byEle = getObject(locatorType, locatorText);
        WebDriverWait wait = (new WebDriverWait(getDriver(), Integer.parseInt(duration) * 1000));
        wait.until(ExpectedConditions.visibilityOfElementLocated(byEle));
    }

    public void waitForElementToClick(String locatorType, String locatorText, String duration) {
        By byEle = getObject(locatorType, locatorText);
        WebDriverWait wait = (new WebDriverWait(getDriver(), Integer.parseInt(duration) * 1000));
        wait.until(ExpectedConditions.elementToBeClickable(byEle));
    }

    public void waitForElementToDisplay$(String objectName, String pageName, String duration) throws Throwable {
        WebDriverWait wait = (new WebDriverWait(getDriver(), Integer.parseInt(duration) * 1000));
        wait.until(ExpectedConditions.visibilityOfElementLocated(getObject(objectName, pageName)));
    }

    public void waitForElementToClick$(String objectName, String pageName, String duration) throws Throwable {
        WebDriverWait wait = (new WebDriverWait(getDriver(), Integer.parseInt(duration) * 1000));
        wait.until(ExpectedConditions.elementToBeClickable(getObject(objectName, pageName)));
    }

}
