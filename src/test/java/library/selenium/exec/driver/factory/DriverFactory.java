package library.selenium.exec.driver.factory;

import library.selenium.exec.driver.enums.BrowserType;
import library.selenium.exec.driver.enums.ServerType;
import library.selenium.exec.driver.managers.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.regexp.RE;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;


public class DriverFactory {
    private Logger logger = LogManager.getLogger(this.getClass().getName());

    protected DriverFactory() {
    }

    public static DriverManager createDriver() {

        return new DriverFactory().setDriverManager();
    }

    private DriverManager setDriverManager() {
        ServerType serverType = ServerType.get(DriverContext.getInstance().getTechStack().get("serverType"));
        BrowserType browserType = BrowserType.get(DriverContext.getInstance().getBrowserName());
        switch (serverType) {
            case REMOTE:
                return new HtmlUnitDriverManager();
            default:
                switch (browserType) {
                    case CHROME:
                        return new ChromeDriverManager();
                    case FIREFOX:
                        return new FirefoxDriverManager();
                    case IE:
                        return new IEDriverManager();
                    case MICROSOFTEDGE:
                        return new EdgeDriverManager();
                    default:
                        throw new UnsupportedOperationException("invalid driver type provide" + browserType);
                }
        }
    }


}

