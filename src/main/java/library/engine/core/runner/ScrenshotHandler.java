package library.engine.core.runner;

import cucumber.api.Result;
import io.cucumber.java8.En;
import library.common.Property;
import library.common.TestContext;
import library.cucumber.core.CukesConstants;
import library.selenium.exec.ExecConstants;
import library.selenium.exec.driver.factory.DriverContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.concurrent.TimeoutException;

import static library.engine.core.EngConstants.PDF;
import static library.engine.core.EngConstants.SELENIUM;
import static library.reporting.Reporter.getScreenshotPath;

public class ScrenshotHandler implements En {

    private boolean waitForPageToLoad;
    private static final String ELEMENT_REF = "fw.elementRef";
    private static final String VALIDATES = "validates";
    private static final String SCROLLING_SCREENSHOT_FLAG = "fw.scrollingScreenshot";
    public static final String ERROR_RESPONSE = "-1";

    private Logger logger = LogManager.getLogger(this.getClass().getName());
//
//    public String addScreenshotToStep(String stepText, Result.Type status) {
//        String screenshotPath = ERROR_RESPONSE;
//
//        setWaitForPageLoad(stepText);
//        if (stepExecuted(status) && !isStepRequiredScreenshot(stepText)) {
//            if (Boolean.parseBoolean(System.getProperty("fw.screenshotOnEveryStep"))) {
//                screenshotPath = takeScrenshot();
//            }
//        }
//
//    }
//
//    private String takeScrenshot() {
//        String screenshotPath = ERROR_RESPONSE;
//        File file = null;
//        String activeWindowType = TestContext.getInstance().getActiveWindowType();
//        if (activeWindowType != null) {
//            switch (activeWindowType) {
//                case SELENIUM:
//                    highlightActiveWebElement();
//                    file = getWebScreenshot(file);
//                    unhighlightActiveWebElement();
//                    break;
//                case PDF:
//                    file = takePDFScrenshot();
//                    break;
//                default:
//                    break;
//            }
//        }
//        if (file != null && file.exists()) {
//            screenshotPath = file.getAbsolutePath();
//        }
//        return screenshotPath;
//    }
//
//    private File getWebScreenshot(File file) {
//        if (DriverContext.getInstance().driverManager() != null) {
//            if (waitForPageToLoad) {
//                try {
//                    logger.info("implement");
//                } catch (TimeoutException timeoutException) {
//                    logger.debug("wait for page load failed");
//                }
//            }
//            file = saveScreenshot(grabScreenshot(DriverContext.getInstance().getDriver()), getScreenshotPath());
//        } else if (DriverContext.getInstance().mobileDriverManager != null) {
//            file = saveScreenshot(grabScreenshot(DriverContext.getInstance().getMobileDriver()), getScreenshotPath());
//        }
//        return file;
//    }
//
//    private void setWaitForPageLoad(String stepText) {
//        waitForPageToLoad = Boolean.parseBoolean(String.valueOf(TestContext.getInstance().testdataGet("fw.waitForPageLoad"))) &&
//                isStepRefreshRequired(stepText);
//        if (Boolean.parseBoolean(Property.getProperties(CukesConstants.RUNTIME_PATH).getString("scrollingScreenshot"))) {
//            try {
//                if (stepText.contains(VALIDATES)) {
//                    System.setProperty(SCROLLING_SCREENSHOT_FLAG, "true");
//                } else {
//                    System.setProperty(SCROLLING_SCREENSHOT_FLAG, "false");
//                }
//            } catch (Exception exception) {
//                logger.warn("Validate scrollingScreenshot flag in runtime.properties");
//            }
//        }
//
//    }
//
//    private boolean isStepRefreshRequired(String stepText) {
//        return stepText.contains(VALIDATES) || stepText.contains("clicks");
//    }

}
