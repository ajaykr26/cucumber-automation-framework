package library.selenium.exection.driver.managers;

import library.selenium.exection.driver.factory.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class IEDriverManager extends DriverManager {

    protected Logger logger = LogManager.getLogger(this.getClass().getName());

    @Override
    protected void createDriver() {

    }

    @Override
    public void updateResults(String result) {

    }
}
