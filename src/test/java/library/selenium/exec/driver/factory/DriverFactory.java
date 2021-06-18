package library.selenium.exec.driver.factory;

import library.common.Property;
import library.selenium.exec.driver.enums.Browsers;
import library.selenium.exec.driver.enums.Servers;
import library.selenium.exec.driver.managers.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class DriverFactory {
    private final Logger logger = LogManager.getLogger(this.getClass().getName());

    protected DriverFactory() {
    }

    public static DriverManager createDriver() {
        return setDriverManager();
    }

    private static DriverManager setDriverManager() {
        Servers server = Servers.get(DriverContext.getInstance().getServerName());
        Browsers browser = Browsers.get(DriverContext.getInstance().getBrowserName());
        switch (server) {
            case REMOTE:
                return new RemoteDriverManager();
            case BROWSERSTACK:
                return new BrowserStackDriverManager();
            case SAUCELABS:
            case APPIUM:
                return new AppiumDriverManager();
            case PERFECTO:
            case HTMLUNIT:
                return new HtmlUnitDriverManager();
            case LOCAL:
                switch (browser) {
                    case CHROME:
                        return new ChromeDriverManager();
                    case FIREFOX:
                        return new FirefoxDriverManager();
                    case IE:
                        return new IEDriverManager();
                    case MSEDGE:
                        return new EdgeDriverManager();
                    default:
                        throw new UnsupportedOperationException("invalid driver type provide" + browser);
                }
            default:
                throw new UnsupportedOperationException("invalid server type provide" + server);

        }
    }

}


