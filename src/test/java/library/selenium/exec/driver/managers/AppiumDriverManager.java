package library.selenium.exec.driver.managers;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.windows.WindowsDriver;
import library.appium.MobileCapabilities;
import library.common.Property;
import library.selenium.exec.driver.factory.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class AppiumDriverManager extends DriverManager {
    private static final String PLATFORM_NAME = "platformName";
    private static final String AUTOMATION_NAME = "automationName";
    private static final String CUKES_APPIUM_END_POINT = "cukes.appiumEndPoint";

    protected Logger logger = LogManager.getLogger(this.getClass().getName());

    @Override
    public void createDriver() {
        MobileCapabilities capabilities = new MobileCapabilities();
        try {
            if (capabilities.getCap().getCapability(PLATFORM_NAME).toString().equalsIgnoreCase("android")) {
                capabilities.getCap().setCapability(AUTOMATION_NAME, "uiautomator2");
                driver = new AndroidDriver(new URL(Property.getVariable(CUKES_APPIUM_END_POINT)), capabilities.getCap());
                driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
            }
            if (capabilities.getCap().getCapability(PLATFORM_NAME).toString().equalsIgnoreCase("iOS")) {
                capabilities.getCap().setCapability(AUTOMATION_NAME, "XCUITest");
                driver = new IOSDriver<>(new URL(Property.getVariable(CUKES_APPIUM_END_POINT)), capabilities.getCap());
            }

            if (capabilities.getCap().getCapability(PLATFORM_NAME).toString().equalsIgnoreCase("windows")) {
                capabilities.getCap().setCapability(AUTOMATION_NAME, "Windows");
                driver = new WindowsDriver<>(new URL(Property.getVariable(CUKES_APPIUM_END_POINT)), capabilities.getCap());
            }


        } catch (Exception exception) {
            logger.error("could not connect to appium server: {}", exception.getMessage());
        }
    }

    @Override
    public void updateResults(String result) {

    }
}
