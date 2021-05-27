package library.selenium.core;

import library.selenium.exec.driver.factory.DriverContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageObject extends library.common.PageObject {
    protected Logger logger = LogManager.getLogger(this.getClass().getName());

    public WebDriver getDriver() {
        logger.debug("obtaining the driver for current thread");
        return DriverContext.getInstance().getDriver();
    }

    public WebDriverWait getWait() {
        logger.debug("obtaining the wait for current thread");
        return DriverContext.getInstance().getDriverWait();
    }

    public void waitForPageToLoad() {
        jsPageLoad();
        jQueryPageLoad();
    }

    public void jsPageLoad() {
        logger.debug("checking if DOM is loaded");
        final JavascriptExecutor javascriptExecutor = (JavascriptExecutor) getDriver();
        boolean isDomLoaded = javascriptExecutor.executeScript("return document.readyState").equals("complete");
        if (!isDomLoaded) {
            getWait().until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver driver) {
                    return (javascriptExecutor.executeScript("return document.readyState").equals("complete"));
                }
            });
        }
    }

    public void jQueryPageLoad() {
        logger.debug("checking if any JQuery operation completed");
        final JavascriptExecutor javascriptExecutor = (JavascriptExecutor) getDriver();
        if ((Boolean) javascriptExecutor.executeScript("return typeof jQuery != 'undefined'")) {
            boolean isJQueryReady = (Boolean) javascriptExecutor.executeScript("return jQuery.active==0");
            if (!isJQueryReady) {
                getWait().until(new ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return (Boolean) javascriptExecutor.executeScript("return window.jQuery.active===0");
                    }
                });
            }
        }
    }
}
