package library.selenium.exec.driver.managers;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import library.common.Property;
import library.selenium.exec.driver.factory.Capabilities;
import library.selenium.exec.driver.factory.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class BrowserStackDriverManager extends DriverManager {
    private static final String PLATFORM_NAME = "platformName";
    protected Logger logger = LogManager.getLogger(this.getClass().getName());

    @Override
    public void createDriver() {
        Capabilities caps = new Capabilities();
        try {
            if (caps.getDc().getCapability(PLATFORM_NAME) != null) {
                switch (caps.getDc().getCapability(PLATFORM_NAME).toString().toLowerCase()) {
                    case "ios":
                        driver = new IOSDriver<IOSElement>(new URL("http://hub.browserstack.com/wd/hub"), caps.getDc());
                        break;
                    case "android":
                        driver = new AndroidDriver<AndroidElement>(new URL("http://hub.browserstack.com/wd/hub"), caps.getDc());
                        break;
                    case "windows":
                        driver = new WindowsDriver<WindowsElement>(new URL("http://hub.browserstack.com/wd/hub"), caps.getDc());
                        break;
                }
            } else {
                driver = new RemoteWebDriver(new URL("http://hub.browserstack.com/wd/hub"), caps.getDc());
            }
        } catch (MalformedURLException exception) {
            logger.error("unable to connect to selenium grid: ", exception);
        }
    }

    @Override
    public void updateResults(String result) {

    }
}
