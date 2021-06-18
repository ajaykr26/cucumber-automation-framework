package library.selenium.exec.driver.managers;

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
        Capabilities capabilities = new Capabilities();
        String browserstackUserName = Property.getVariable("cukes.browserstackUserName");
        String browserstackPassword = Property.getVariable("cukes.browserstackPassword");
        String browserstackEndPoint = Property.getVariable("cukes.browserstackEndPoint");
        String browserstackServer = "https://" + browserstackUserName + ":" + browserstackPassword + browserstackEndPoint;

        try {
            if (capabilities.getDesiredCapabilities().getCapability(PLATFORM_NAME) != null) {
                switch (capabilities.getDesiredCapabilities().getCapability(PLATFORM_NAME).toString().toLowerCase()) {
                    case "ios":
                    case "android":
                    case "windows-ui":
                    default:
                        driver = new RemoteWebDriver(new URL(browserstackServer), capabilities.getDesiredCapabilities());

                }
            } else {
                driver = new RemoteWebDriver(new URL(browserstackServer), capabilities.getDesiredCapabilities());
            }
        } catch (MalformedURLException exception) {
            logger.error("unable to connect to selenium grid: ", exception);
        }
    }

    @Override
    public void updateResults(String result) {

    }
}
