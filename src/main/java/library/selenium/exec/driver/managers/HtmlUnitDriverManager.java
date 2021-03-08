package library.selenium.exec.driver.managers;


import com.gargoylesoftware.htmlunit.BrowserVersion;
import library.selenium.exec.driver.enums.BrowserType;
import library.selenium.exec.driver.factory.DriverContext;
import library.selenium.exec.driver.factory.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;


public class HtmlUnitDriverManager extends DriverManager {

    protected Logger logger = LogManager.getLogger(this.getClass().getName());

    @Override
    public void createDriver() {
        BrowserType browser = BrowserType.valueOf(DriverContext.getInstance().getBrowserName().toLowerCase());
        switch (browser) {
            case CHROME:
                driver = new HtmlUnitDriver(BrowserVersion.CHROME);
            case FIREFOX:
                driver = new HtmlUnitDriver(BrowserVersion.FIREFOX);
            case IE:
                driver = new HtmlUnitDriver(BrowserVersion.INTERNET_EXPLORER);
        }

    }

    @Override
    public void updateResults(String result) {

    }


}