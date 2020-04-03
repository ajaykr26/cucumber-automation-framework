package library.selenium.exection.driver.factory;

import library.selenium.exection.driver.managers.ChromeDriverManager;
import library.selenium.exection.driver.managers.FirefoxDriverManager;
import library.selenium.exection.driver.managers.IEDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;


public class DriverFactory {
    private Logger logger = LogManager.getLogger(this.getClass().getName());

    private static DriverFactory instance = new DriverFactory();
    ThreadLocal<DriverManager> driverManager = new ThreadLocal<DriverManager>() {
        protected DriverManager initialValue() {
            return setDriverManager();
        }
    };

    protected DriverFactory() {
    }

    public static DriverFactory getInstance() {
        return instance;
    }

    public DriverManager driverManager() {
        return driverManager.get();
    }

    public WebDriver getDriver() {

        return driverManager.get().getDriver();
    }

    public WebDriverWait getWait() {

        return driverManager.get().getWait();
    }

    public DriverManager setDriverManager() {
        String browser = DriverContext.getInstance().getBrowserName();

        switch (browser.toUpperCase()) {
            case "CHROME":
                driverManager.set(new ChromeDriverManager());
                break;
            case "firefox":
                driverManager.set(new FirefoxDriverManager());
                break;
            case "iexplorer":
                driverManager.set(new IEDriverManager());
                break;

        }
        return driverManager.get();
    }


    public void quit() {
        driverManager.get().quitDriver();
        driverManager.remove();
    }
}


