package library.selenium.core;

import io.cucumber.java8.Fi;
import library.common.Property;
import library.cucumber.core.CukesConstants;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.texen.util.FileUtil;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.util.UUID;

public class Screenshot {

    private Screenshot() {
    }

    private static Logger logger = LogManager.getLogger(Screenshot.class);

    public static File grabScreenshot(WebDriver driver) {
        String screenshotType = null;
        screenshotType = System.getProperty("fw.scrollinScreenshot");

        try {
            if (driver != null) {
                if (driver.getWindowHandle() != null) {
                    if (screenshotType != null) {
                        return screenshotType.equalsIgnoreCase("true") ? grabScrollingScreenshot(driver) : grabDisplayAreScreenshot(driver);
                    } else {
                        return grabDisplayAreScreenshot(driver);
                    }

                } else {
                    logger.warn("driver in not launched, skipping the screenshot");
                }
            }

        } catch (NoSuchWindowException exception) {
            logger.warn("skipping screenshot, Error: {}", exception.getMessage());
        }
        return null;
    }

    public static File grabDisplayAreScreenshot(WebDriver driver) {
        try {
            Thread.sleep(Property.getProperties(CukesConstants.RUNTIME_PATH).getInt("screenshotDelay", 0));
        } catch (InterruptedException | NumberFormatException exception) {
            logger.error(exception.getMessage());
            Thread.currentThread().interrupt();
        }
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    }

    public static File grabScrollingScreenshot(WebDriver driver) {
        try {
            Thread.sleep(Property.getProperties(CukesConstants.RUNTIME_PATH).getInt("screenshotDelay", 0));
        } catch (InterruptedException | NumberFormatException exception) {
            logger.error(exception.getMessage());
            Thread.currentThread().interrupt();
        }

        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    }

    public static File saveScreenshot(File screenshot, String filepath) {
        UUID uuid = UUID.randomUUID();
        File file = new File(filepath + uuid + ".png");
        if (screenshot != null) {
            try {
                FileUtils.moveFile(screenshot, file);
            } catch (Exception exception) {
                logger.error(exception.getMessage());
            }
            return file;
        }
        return null;
    }

}
