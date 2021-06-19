package library.selenium.exec.driver.managers;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
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
    private static final String CUKES_APPIUM_END_POINT = "fw.appiumEndPoint";

    protected Logger logger = LogManager.getLogger(this.getClass().getName());

    @Override
    public void createDriver() {
        Capabilities caps = new Capabilities();

        try {
            switch (caps.getDc().getCapability(PLATFORM_NAME).toString()) {
                case "android":
                    driver = new AndroidDriver<AndroidElement>(new URL(Property.getVariable(CUKES_APPIUM_END_POINT)), caps.getDc());
                    break;
                case "iOS":
                    driver = new IOSDriver<AndroidElement>(new URL(Property.getVariable(CUKES_APPIUM_END_POINT)), caps.getDc());
                    break;
                case "windows":
                    driver = new WindowsDriver<AndroidElement>(new URL(Property.getVariable(CUKES_APPIUM_END_POINT)), caps.getDc());
                    break;
            }
        } catch (Exception exception) {
            logger.error("could not connect to appium server: {}", exception.getMessage());
        }
    }

    @Override
    public void updateResults(String result) {

    }
}
