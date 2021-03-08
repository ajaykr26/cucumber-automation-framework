package library.selenium.exec.driver.managers;


import io.github.bonigarcia.wdm.WebDriverManager;
import library.common.Property;
import library.cucumber.core.CukesConstants;
import library.selenium.exec.driver.factory.DriverContext;
import library.selenium.exec.driver.factory.DriverManager;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class EdgeDriverManager extends DriverManager {

    protected Logger logger = LogManager.getLogger(this.getClass().getName());

    @Override
    public void createDriver(){
        PropertiesConfiguration propertiesConfiguration = Property.getProperties(CukesConstants.RUNTIME_PATH);

        if(Property.getVariable("cukes.webdrivermanager") != null && Property.getVariable("cukes.webdrivermanager").equalsIgnoreCase("true")){
            if(Property.getVariable("cukes.chromedriver") != null){
                WebDriverManager.chromedriver().driverVersion(Property.getVariable("cukes.webdrivermanager")).setup();
            }else {
                WebDriverManager.chromedriver().setup();

            }
        }else {
            System.setProperty("webdriver.chrome.diver", CukesConstants.DRIVER_PATH+"chromedriver.exe");
        }
        System.setProperty("webdriver.chrome.silentOutput", "true");
        ChromeOptions chromeOptions = new ChromeOptions();

        for(String option: propertiesConfiguration.getStringArray("options." + DriverContext.getInstance().getBrowserName().replaceAll("\\s", ""))){
            chromeOptions.addArguments(option);
        }

        if (propertiesConfiguration.getString("options.chrome.useAutomationExtension") != null &&
                propertiesConfiguration.getString("options.chrome.useAutomationExtension").equalsIgnoreCase("false")) {
                chromeOptions.setExperimentalOption("options.chrome.useAutomationExtension", false);

        }

        if(DriverContext.getInstance().getBrowserName().contains("kiosk")){
            chromeOptions.addArguments("--kiosk");
        }
        driver = new ChromeDriver(chromeOptions);
    }

    @Override
    public void updateResults(String result) {

    }
}


