package library.selenium.exec.driver.factory;

import library.common.Constants;
import library.common.Property;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;


public abstract class DriverManager {
    protected WebDriver driver;
    protected WebDriverWait wait;

    protected DriverManager() {
    }

    public WebDriverWait getWait() {
        if (wait == null) {
            wait = new WebDriverWait(driver, getWaitDuration());
        }
        return wait;
    }

    public WebDriver getDriver() {
        if (driver == null) {
            setDriver();
        }
        return driver;
    }

    public void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    public int getWaitDuration() {
        final int defaultWait = 30;
        int duration;
        try {
            duration = Property.getProperties(Constants.RUNTIME_PATH).getInt("defaultWait");
        } catch (Exception e) {
            duration = defaultWait;
        }
        return duration;
    }

    public String getDriverPath(String driver) {
        String extention = System.getProperty("os.name").split(" ")[0].toLowerCase().equalsIgnoreCase("windows") ? ".exe" : " ";
        String drivername = driver + extention;
        String driverPath = Property.getVariable("cuke.driverPath");
        return (driverPath == null ? Constants.DRIVER_PATH + System.getProperty("os.name").split(" ")[0].toLowerCase() + File.separator + drivername : driverPath);
    }

    protected abstract void setDriver();

    public abstract void updateResults(String result);

}


