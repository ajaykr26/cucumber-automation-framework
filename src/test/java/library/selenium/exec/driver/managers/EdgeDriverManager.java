package library.selenium.exec.driver.managers;


import io.github.bonigarcia.wdm.WebDriverManager;
import library.common.Property;
import library.selenium.exec.driver.factory.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;


public class EdgeDriverManager extends DriverManager {

    protected Logger logger = LogManager.getLogger(this.getClass().getName());
    EdgeOptions options = new EdgeOptions();

    @Override
    protected void createDriver() {
        if (Property.getVariable("cukes.webdrivermanager") != null && Property.getVariable("cukes.webdrivermanager").equalsIgnoreCase("true")) {
            if (Property.getVariable("cukes.msedgedriver") != null) {
                WebDriverManager.iedriver().driverVersion(Property.getVariable("cukes.msedgedriver")).setup();
            } else {
                WebDriverManager.edgedriver().setup();

            }
        } else {
            System.setProperty("webdriver.edge.driver", getDriverPath("msedgedriver"));
        }
        System.setProperty("webdriver.edge.driver.silent", "true");
        System.setProperty("edge.ensureCleanSession", "true");

        driver = new EdgeDriver(options);
    }

    @Override
    public void updateResults(String result) {

    }
}


