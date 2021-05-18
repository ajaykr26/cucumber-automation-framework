package library.engine.mobile;

import library.common.Constants;
import library.common.Property;
import library.engine.core.EngBaseStep;
import library.selenium.exec.driver.factory.DriverContext;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;

public class BaseMobileSteps extends EngBaseStep {

    PropertiesConfiguration props = Property.getProperties(Constants.MOBILE_RUNTIME_PROP);

    protected void launchMobile() {
        getMobileDriver();
    }

    public WebDriver getMobileDriver() {
        logger.debug("obtaining the mobile driver for current thread");
        return DriverContext.getInstance().getMobileDriver();
    }

    public void switchContext(String context) {
        DriverContext.getInstance().switchContext(context);
    }

    protected void launchMobileApplication(String applicationName, WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Map<String, String> startapp = new HashMap<>();
        startapp.put("name", applicationName);
        js.executeScript("mobile:application:open", startapp);
    }

    protected void closeMobileApplication(String applicationName, WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Map<String, String> startapp = new HashMap<>();
        startapp.put("name", applicationName);
        js.executeScript("mobile:application:close", startapp);
    }
}
