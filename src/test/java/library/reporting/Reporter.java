package library.reporting;

import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.model.Link;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StatusDetails;
import io.qameta.allure.model.StepResult;
import io.qameta.allure.util.ResultsUtils;
import library.common.Constants;
import library.common.Property;
import library.common.TestContext;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        try (InputStream inputStream = IOUtils.toInputStream(content, String.valueOf(StandardCharsets.UTF_8))) {
            Allure.addAttachment(logTitle, inputStream);
        } catch (IOException exception) {
            logger.error("{} failed {}", logTitle, exception.getMessage());

        }
    }

    public static String addDataTable(String tableName, Map<String, Object> dataTable) {
        final StringBuilder dataTableTabSeperated = new StringBuilder();
        if (dataTable != null && !dataTable.isEmpty()) {
            dataTable.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(entry -> {
                        dataTableTabSeperated.append(String.join("\t", entry.getKey(), entry.getValue().toString()));
                        dataTableTabSeperated.append("\n");
                    });

            final String attachmentSource = Allure.getLifecycle().prepareAttachment(tableName, "text\tab-separated-values", "csv");
            Allure.getLifecycle().writeAttachment(attachmentSource, new ByteArrayInputStream(dataTableTabSeperated.toString().getBytes(StandardCharsets.UTF_8)));
            return attachmentSource;
        }
        return null;
    }

    public static void setStepStatus(String step, Status status) {
        Allure.step(step, status);
    }

    public static String getReportPath() {
        String defaultReportPath = Constants.ALLURE_RESULT_PATH;
        String reportPath = Property.getVariable("reportPath");
        return (reportPath == null ? defaultReportPath : reportPath);
    }

    public static String getReportName() {
        DateFormat dataFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return "RunReport_" + dataFormat.format(new Date()) + ".html";
    }

    public static String getScreenshotPath() {
        return getReportPath() + "/screenshots/" + TestContext.getInstance().testdataGet("fw.scenarioName") + "/";
    }

    public static void startFinalStep(Boolean scenarioIsFailed) {
        String testID = getTestIDFromAfterHook();
        String stepUUID = testID + "finalStep";
        Status stepStatus;
        String stepMessage;
        if (scenarioIsFailed) {
            stepStatus = Status.FAILED;
            stepMessage = "Scenario Failed";
        } else {
            stepStatus = Status.PASSED;
            stepMessage = "Scenario Passed";
        }
        Allure.getLifecycle().startStep(testID, stepUUID, new StepResult().setName(stepMessage).setStatus(stepStatus));
    }

    public static void stopFinalStep() {
        String testID = getTestIDFromAfterHook();
        String stepUUID = testID + "finalStep";
        Allure.getLifecycle().stopStep(stepUUID);
        addTestIDHistoryObject(testID);
    }

    public static void failScenario(Throwable message) {
        AllureLifecycle lifecycle = Allure.getLifecycle();
        final StatusDetails statusDetails = ResultsUtils.getStatusDetails(message).orElse(new StatusDetails());
        lifecycle.getCurrentTestCase().ifPresent(tcUUID -> lifecycle.updateTestCase(tcUUID, testResult -> testResult.setStatus(Status.FAILED).setStatusDetails(statusDetails)));
    }

    public static void addLink(String name, String url) {
        String testID = getTestIDFromAfterHook();
        Link link = (new Link()).setName(name).setUrl(url);
        Allure.getLifecycle().updateTestCase(testID, testResult -> testResult.getLinks().add(link));
    }

    private static String getTestIDFromAfterHook() {
        Optional<String> testID = Allure.getLifecycle().getCurrentTestCase();
        if (testID.isPresent()) {
            Pattern pattern = Pattern.compile(String.format("^%s(.*)after", TestContext.getInstance().testdataGet("fw.featureName")));
            Matcher testIDMatcher = pattern.matcher(testID.get());
            if (testIDMatcher.find()) {
                return testIDMatcher.group(1);
            }
        }
        return "-1";
    }

    public static void addTestIDHistoryObject(String testID) {
        Map<String, String> updateHistory = TestContext.getInstance().testdataToClass("fw.updateHistory", Map.class);
        if (updateHistory == null) {
            updateHistory = new HashMap<>();
        }
        updateHistory.put("allureTestiD", testID);
        TestContext.getInstance().testdataPut("fw.updateHistory", updateHistory);
    }
}
