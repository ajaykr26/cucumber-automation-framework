package library.selenium.exec;

import library.common.JSONHelper;
import library.common.Property;
import library.common.TestContext;
import library.cucumber.core.CukesConstants;
import library.selenium.exec.driver.factory.DriverContext;
import library.selenium.exec.driver.factory.DriverFactory;
import org.apache.commons.configuration2.PropertiesConfiguration;
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

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseTest {


    private static final String PROJECT_NAME = "PROJECT_NAME";
    private static final String BUILD_NUMBER = "BUILD_NUMBER";
    protected BasePO po;
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

    public BasePO getPO() {
        logger.debug("obtaining an instance of the base page object");
        if (this.po == null) {
            this.po = new BasePO();
        }
        return po;
    }

    public void setPO() {
        logger.debug("obtaining an instance of the base page object");
        this.po = new BasePO();
    }

    protected SoftAssertions softAssertions() {
        return TestContext.getInstance().softAssertions();
    }



//    @DataProvider(name = "getTechStack", parallel = true)
//    public Object[][] getTechStack() {
//        Map<String, String> techStack = new HashMap<>();
//        PropertiesConfiguration props = Property.getProperties(Constants.RUNTIME_PATH);
//        if (Property.getVariable("techstack") != null) {
//            techStack = JSONHelper.getJSONObjectToMap(Constants.TECHSTACK_PATH);
//            if (!techStack.isEmpty()) {
//                techStack.putAll(techStack);
//            } else {
//                logger.warn("Tech stack JSON file not found: {}. defaulting to local chrome driver instance.", Constants.TECHSTACK_PATH);
//                techStack.put("serverName", "local");
//                techStack.put("browserName", "chrome");
//            }
//        } else if (props.containsKey("serverName") && props.containsKey("browserName")) {
//            logger.info("techstack is not defined in vm arguments. getting the configuration from runtime.properties file");
//            techStack.put("serverName", Property.getProperty(Constants.RUNTIME_PATH, "serverName").toLowerCase());
//            techStack.put("browserName", Property.getProperty(Constants.RUNTIME_PATH, "browserName").toLowerCase());
//        } else {
//            logger.info("nether techstack is not defined in vm arguments nor the configuration is defined in runtime.properties file");
//            techStack.put("serverName", Property.getProperty(Constants.RUNTIME_PATH, "serverName").toLowerCase());
//            techStack.put("browserName", Property.getProperty(Constants.RUNTIME_PATH, "browserName").toLowerCase());
//        }
//        return new Object[][]{Collections.singletonList(techStack).toArray()};
//
//    }

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
