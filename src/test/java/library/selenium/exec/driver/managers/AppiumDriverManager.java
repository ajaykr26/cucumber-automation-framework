package library.selenium.exec.driver.managers;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.windows.WindowsDriver;
import library.common.Property;
import library.selenium.exec.driver.factory.Capabilities;
import library.selenium.exec.driver.factory.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;

public class AppiumDriverManager extends DriverManager {
    private static final String PLATFORM_NAME = "platformName";
    private static final String AUTOMATION_NAME = "automationName";
    private static final String CUKES_APPIUM_END_POINT = "cukes.appiumEndPoint";

    protected Logger logger = LogManager.getLogger(this.getClass().getName());

    @Override
    public void createDriver() {
        Capabilities capabilities = new Capabilities();
        try {
            if (capabilities.getDesiredCapabilities().getCapability(PLATFORM_NAME).toString().equalsIgnoreCase("android")) {
                capabilities.getDesiredCapabilities().setCapability(AUTOMATION_NAME, "uiautomator2");
                driver = new AndroidDriver(new URL(Property.getVariable(CUKES_APPIUM_END_POINT)), capabilities.getDesiredCapabilities());
            }
            if (capabilities.getDesiredCapabilities().getCapability(PLATFORM_NAME).toString().equalsIgnoreCase("iOS")) {
                capabilities.getDesiredCapabilities().setCapability(AUTOMATION_NAME, "XCUITest");
                driver = new IOSDriver<>(new URL(Property.getVariable(CUKES_APPIUM_END_POINT)), capabilities.getDesiredCapabilities());
            }

            if (capabilities.getDesiredCapabilities().getCapability(PLATFORM_NAME).toString().equalsIgnoreCase("windows")) {
                capabilities.getDesiredCapabilities().setCapability(AUTOMATION_NAME, "Windows");
                driver = new WindowsDriver<>(new URL(Property.getVariable(CUKES_APPIUM_END_POINT)), capabilities.getDesiredCapabilities());
            }


        } catch (Exception exception) {
            logger.error("could not connect to appium server: {}", exception.getMessage());
        }
    }

    @Override
    public void updateResults(String result) {

    }
}
