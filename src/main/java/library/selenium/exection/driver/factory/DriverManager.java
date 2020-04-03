package library.selenium.exection.driver.factory;

import library.common.Constants;
import library.common.Property;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;


public abstract class DriverManager {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public WebDriver getDriver() {
        if (driver == null) {
            createDriver();
        }
        return driver;
    }

    public void quitDriver() {

        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    public WebDriverWait getWait() {
        if (wait == null) {
            wait = new WebDriverWait(driver, getWaitDuration());
        }
        return wait;
    }

    public int getWaitDuration() {
        final int defaultWit = 10;
        int duration;
        try {
            duration = Property.getProperties(Constants.RUNTIME_PATH).getInt("defaultWait");
        } catch (Exception e) {
            duration = defaultWit;
        }
        return duration;
    }


    protected abstract void createDriver();

    public abstract void updateResults(String result);
}


