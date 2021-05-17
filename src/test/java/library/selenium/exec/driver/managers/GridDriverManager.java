package library.selenium.exec.driver.managers;

import library.common.Property;
import library.selenium.exec.driver.factory.Capabilities;
import library.selenium.exec.driver.factory.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class GridDriverManager extends DriverManager {
    private static final String PLATFORM_NAME = "platformName";
    private static final String CUKES_SELENIUM_GRID = "fw.seleniumGrid";
    protected Logger logger = LogManager.getLogger(this.getClass().getName());

    @Override
    public void createDriver() {
        Capabilities capabilities = new Capabilities();
        try {
            if (capabilities.getDesiredCapabilities().getCapability(PLATFORM_NAME) != null) {
                switch (capabilities.getDesiredCapabilities().getCapability(PLATFORM_NAME).toString().toLowerCase()) {
                    case "android":
                        driver = new RemoteWebDriver(new URL(Property.getVariable(CUKES_SELENIUM_GRID)), capabilities.getDesiredCapabilities());
                        break;
                    case "ios":
                        driver = new RemoteWebDriver(new URL(Property.getVariable(CUKES_SELENIUM_GRID)), capabilities.getDesiredCapabilities());
                        break;
                    case "windows-ui":
                        driver = new RemoteWebDriver(new URL(Property.getVariable(CUKES_SELENIUM_GRID)), capabilities.getDesiredCapabilities());
                        break;
                    default:
                        driver = new RemoteWebDriver(new URL(Property.getVariable(CUKES_SELENIUM_GRID)), capabilities.getDesiredCapabilities());

                }
            } else {
                driver = new RemoteWebDriver(new URL(Property.getVariable(CUKES_SELENIUM_GRID)), capabilities.getDesiredCapabilities());
                ;
            }
        } catch (MalformedURLException exception) {
            logger.error("unable to connect to selenium grid: ", exception);
        }
    }

    @Override
    public void updateResults(String result) {

    }
}
