package library.reporting;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class Reporter {
    protected static Logger logger = LogManager.getLogger(Reporter.class);

    private Reporter() {
    }

    public static void addStepLog(String message) {
        Allure.step(message, Status.SKIPPED);
    }

    public static void addStepLog(String type, String message) {
        switch (type) {
            case "ERROR":
                Allure.step(message, Status.BROKEN);
                break;
            case "FAIL":
                Allure.step(message, Status.FAILED);
                break;
            case "PASS":
                Allure.step(message, Status.PASSED);
                break;
            case "SKIP":
                Allure.step(message, Status.SKIPPED);
                break;
            default:
                Allure.step(message);
        }
    }

    public static void addScreenCaptureFromPath(String imagePath) {
        Path content = Paths.get(imagePath);
        try (InputStream inputStream = Files.newInputStream(content)) {
            Allure.addAttachment("Screenshot", inputStream);
        } catch (IOException exception) {
            logger.error("Screenshot failed: {}", exception.getMessage());
        }
    }

    public static void addScreenCaptureFromPath(String imagePath, String title) {
        Path content = Paths.get(imagePath);
        try (InputStream inputStream = Files.newInputStream(content)) {
            Allure.addAttachment(title, inputStream);
        } catch (IOException exception) {
            logger.error("Screenshot failed: {}", exception.getMessage());
        }
    }

    public static void addTextLogContent(String logTitle, String content) {
        try (InputStream inputStream = IOUtils.toInputStream(content, String.valueOf(StandardCharsets.UTF_8))){
            Allure.addAttachment(logTitle, inputStream);
        }catch (IOException exception){
            logger.error("{} failed {}", logTitle, exception.getMessage());

        }
    }

    public static void addDataTable(String tableName, Map<String, Object> dataTable) {

    }

    public static void getReportPath() {

    }

    public static void getReportName() {

    }

    public static void getScreenshotPath() {

    }

    public static void startFinalStep(Boolean scenarioIsFailed) {

    }

    public static void stopFinalStep() {

    }

    public static void failScenario(Throwable message) {

    }
}
