package library.selenium.core;

import library.common.Constants;
import library.common.Property;
import library.common.TestContext;
import library.selenium.exection.driver.factory.DriverContext;
import library.selenium.exection.driver.factory.DriverFactory;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageobjects.LoginPage;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BaseTest{


    private static final String PROJECT_NAME = "PROJECT_NAME";
    private static final String BUILD_NUMBER = "BUILD_NUMBER";
    protected TestContext context = TestContext.getInstance();
    protected Logger logger = LogManager.getLogger(this.getClass().getName());


    public BaseTest() {
    }

    public WebDriver getDriver() {
        logger.debug("obtaining the driver for current thread");
        return DriverFactory.getInstance().getDriver();
    }

    public WebDriverWait getWait() {
        logger.debug("obtaining the wait for current thread");
        return DriverFactory.getInstance().getWait();
    }

    protected SoftAssertions softAssertions() {
        return TestContext.getInstance().softAssertions();
    }

    @DataProvider(name = "techStackJSON", parallel = true)
    public Object[][] techStackJSON() throws ConfigurationException {

        Map<String, String> techStack = new HashMap<>();
        techStack.put("seleniumServer", "local");
        techStack.put("browser", Property.getProperty(Constants.CONFIG_PATH + "config.properties", "browser"));
        return new Object[][]{Collections.singletonList(techStack).toArray()};
    }

    @BeforeMethod
    public void startUp(Method method, Object[] args) {
        Test test = method.getAnnotation(Test.class);
        Map<String, String> map = (Map<String, String>) args[0];
        if (!TestContext.getInstance().testdata().containsKey("fw.cucumberTest")) ;
        TestContext.getInstance().testdataPut("fw.testDescription", test.description() + "(" + map.get("description") + ")");
        if (Property.getVariable(PROJECT_NAME) != null && !Property.getVariable(PROJECT_NAME).isEmpty())
            TestContext.getInstance().testdataPut("fw.projectName", Property.getVariable(PROJECT_NAME));
        if (Property.getVariable(BUILD_NUMBER) != null && !Property.getVariable(BUILD_NUMBER).isEmpty())
            TestContext.getInstance().testdataPut("fw.buildNumber", Property.getVariable(BUILD_NUMBER));
        DriverContext.getInstance().setDriverContext(map);

    }

    @AfterMethod(groups = {"quitDriver"})
    public void closeDown(ITestResult result) {
        if (!TestContext.getInstance().testdata().containsKey(("fw.cucumberTest"))) {
            DriverFactory.getInstance().driverManager().updateResults(result.isSuccess() ? "passed" : "failed");
            DriverFactory.getInstance().quit();
        }
    }

}
