package library.selenium.exec.driver.factory;

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

        return new DriverFactory().setDriverManager();
    }

    private DriverManager setDriverManager() {
        Servers seleniumServer = Servers.get(DriverContext.getInstance().getTechStack().get("seleniumServer"));
        Browsers browserName = Browsers.get(DriverContext.getInstance().getBrowserName());
        switch (seleniumServer) {
            case GRID:
                return new GridDriverManager();
            case BROWSERSTACK:
            case SAUCELABS:
            case APPIUM:
                return new AppiumDriverManager();
            case PERFECTO:
            case HTMLUNIT:
                return new HtmlUnitDriverManager();
            case LOCAL:
                switch (browserName) {
                    case CHROME:
                        return new ChromeDriverManager();
                    case FIREFOX:
                        return new FirefoxDriverManager();
                    case IE:
                        return new IEDriverManager();
                    case MSEDGE:
                        return new EdgeDriverManager();
                    default:
                        throw new UnsupportedOperationException("invalid driver type provide" + browserName);
                }
            default:
                throw new UnsupportedOperationException("invalid server type provide" + seleniumServer);

        }
    }

}


