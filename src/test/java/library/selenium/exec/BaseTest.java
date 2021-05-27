package library.selenium.exec;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import library.common.Property;
import library.common.TestContext;
import library.selenium.exec.driver.factory.DriverContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Map;

public class BaseTest {


    private static final String PROJECT_NAME = "PROJECT_NAME";
    private static final String BUILD_NUMBER = "BUILD_NUMBER";
    protected Logger logger = LogManager.getLogger(this.getClass().getName());
    private static AppiumDriverLocalService service;
    protected BasePO po;

    public BaseTest() {
    }

    public WebDriver getDriver() {
        logger.debug("obtaining the driver for current thread");
        return DriverContext.getInstance().getDriver();
    }

    public WebDriverWait getWait() {
        logger.debug("obtaining the wait for current thread");
        return DriverContext.getInstance().getDriverWait();
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

    @BeforeSuite
    public void globalSetup() {
        if (Property.getVariable("cukes.techstack").startsWith("APPIUM")) {
            service = AppiumDriverLocalService.buildDefaultService();
            service.start();
        }
    }

    @AfterSuite
    public void globalTearDown() {
        if (service != null) {
            service.stop();
        }
    }

    public static URL getServiceUrl() {
        return service.getUrl();
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
            DriverContext.getInstance().getDriverManager().updateResults(result.isSuccess() ? "passed" : "failed");
            DriverContext.getInstance().quit();
        }
    }

}
