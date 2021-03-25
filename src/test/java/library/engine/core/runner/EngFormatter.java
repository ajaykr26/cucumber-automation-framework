package library.engine.core.runner;

import cucumber.api.PickleStepTestStep;
import cucumber.api.Result;
import cucumber.api.event.*;
import cucumber.runtime.formatter.TestSourcesModelProxy;
import library.common.JSONHelper;
import library.common.TestContext;
import library.engine.core.EngBaseStep;
import org.apache.logging.log4j.ThreadContext;

import java.util.*;
import java.util.stream.Collectors;

import static cucumber.api.Result.Type.FAILED;
import static cucumber.api.Result.Type.PASSED;
import static library.common.StringHelper.getClassShortName;
import static library.engine.core.EngConstants.*;
import static library.reporting.Reporter.addScreenCaptureFromPath;

public class EngFormatter implements ConcurrentEventListener {
    private final EventHandler<TestSourceRead> featureStartedHandler = this::handleFeatureStartedHandler;
    private final EventHandler<TestCaseStarted> caseStartedHandler = this::handleTestCaseStarted;
    private final EventHandler<TestCaseFinished> caseFinishedHandler = this::handleTestCaseFinished;
    private final EventHandler<TestStepStarted> stepStartedHandler = this::handleTestStepStarted;
    private final EventHandler<TestStepFinished> stepFinishedHandler = this::handleTestStepFinished;
    private final TestSourcesModelProxy testSources = new TestSourcesModelProxy();
    private final ThreadLocal<String> currentFeatureFile = new InheritableThreadLocal<>();
    private final ThreadLocal<ScrenshotHandler> screenshotHandler = new InheritableThreadLocal<>();
    private final ThreadLocal<String> screenshotPath = new InheritableThreadLocal<>();
    private final ThreadLocal<Map<String, String>> screenshotBehaviorMap = new InheritableThreadLocal<>();
    private final ThreadLocal<Map<String, String>> classToWindowMapping = new InheritableThreadLocal<>();
    private final ThreadLocal<Result.Type> lastStepStatus = new InheritableThreadLocal<>();
    private static final String FW_SCENARIO_NAME = "fw.scenarioName";
    private static final String IGNORE = "Ignore";
    private static final String ERROR_RESPONSE = "-1";


    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {
        eventPublisher.registerHandlerFor(TestSourceRead.class, featureStartedHandler);

        eventPublisher.registerHandlerFor(TestCaseStarted.class, caseStartedHandler);
        eventPublisher.registerHandlerFor(TestCaseFinished.class, caseFinishedHandler);

        eventPublisher.registerHandlerFor(TestStepStarted.class, stepStartedHandler);
        eventPublisher.registerHandlerFor(TestStepFinished.class, stepFinishedHandler);

    }

    private void handleFeatureStartedHandler(final TestSourceRead event) {
        testSources.addTestSourceReadEvent(event.uri, event);
    }


    private void handleTestCaseStarted(TestCaseStarted event) {
        currentFeatureFile.set(event.testCase.getUri());
        TestContext.getInstance().testdataPut("fw.featureName", this.testSources.getFeature(this.currentFeatureFile.get()).getName());
        TestContext.getInstance().testdataPut(FW_SCENARIO_NAME, event.testCase.getName());
        TestContext.getInstance().testdataPut("fw.logFileName", String.format("%s/%s",
                TestContext.getInstance().testdataGet("fw.featureName"),
                TestContext.getInstance().testdataGet(FW_SCENARIO_NAME)));
        ThreadContext.put("logFileName", TestContext.getInstance().testdataGet("fw.logFileName").toString());
        screenshotBehaviorMap.set(readScreenshotMap());
        classToWindowMapping.set(getWindowTypeMap());
        screenshotHandler.set(new ScrenshotHandler());
        lastStepStatus.set(PASSED);
    }

    private void handleTestCaseFinished(TestCaseFinished testCaseFinished) {
        currentFeatureFile.remove();
        screenshotBehaviorMap.remove();
        screenshotPath.remove();
        classToWindowMapping.remove();
        screenshotHandler.remove();
        lastStepStatus.remove();
    }

    private void handleTestStepStarted(TestStepStarted event) {
        if (event.testStep instanceof PickleStepTestStep) {
            setWindowType(event.testStep.getCodeLocation());

            final PickleStepTestStep testStep = (PickleStepTestStep) event.testStep;
            if (isBeforeStep(event.testStep.getCodeLocation(), testStep.getPickleStep().getText())) {
                String stepText = getStepDescription(testStep, lastStepStatus.get());
                screenshotPath.set(screenshotHandler.get().addScreenshotToStep(stepText, lastStepStatus.get()));
            } else {
                screenshotPath.set(ERROR_RESPONSE);
            }
        }
    }

    private boolean isBeforeStep(String codeLocation, String steptext) {
        return (screenshotBehaviorMap.get().get(getClassShortName(codeLocation)) != null) || (steptext.contains("the user sends keys"));
    }

    private void setWindowType(String classlocation) {
        String classShortName = getClassShortName(classlocation);

        Map<String, String> matchingWindowType = classToWindowMapping.get()
                .entrySet()
                .stream()
                .filter(entry -> classShortName.contains(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        matchingWindowType.values().forEach(value -> TestContext.getInstance().setActiveWindowType(value));
    }

    private Map<String, String> readScreenshotMap() {
        Map<String, String> tempMap = JSONHelper.loadJSONMapFromResources(EngFormatter.class, "ScreenshotBehavior.json");
        if (tempMap != null && !tempMap.isEmpty()) {
            return tempMap.entrySet()
                    .stream()
                    .filter(entry -> entry.getKey().equalsIgnoreCase("before"))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        } else {
            return Collections.emptyMap();
        }
    }

    private Map<String, String> getWindowTypeMap() {
        Map<String, String> tempMap = new HashMap<>();

        tempMap.put("AutoEngAPI", API);
        tempMap.put("AutoEngDB", DB);
        tempMap.put("AutoEngMobile", MOBILE);
        tempMap.put("AutoEngDesktop", DESKTOP);
        tempMap.put("AutoEngWeb", SELENIUM);
        tempMap.put("AutoEng", SELENIUM);

        return tempMap;

    }

    private void handleTestStepFinished(TestStepFinished event) {
        String errorMsg = null;
        lastStepStatus.set(event.result.getStatus());
        if (event.result.getStatus() == FAILED) {
            errorMsg = event.result.getErrorMessage();
        }

        if (event.testStep instanceof PickleStepTestStep) {
            final PickleStepTestStep testStep = (PickleStepTestStep) event.testStep;
            String stepText = getStepDescription(testStep, event.result.getStatus());

            if (screenshotPath.get().equalsIgnoreCase(ERROR_RESPONSE)) {
                screenshotPath.set(screenshotHandler.get().addScreenshotToStep(stepText, event.result.getStatus()));
            }
            addScreenCaptureToAllure(screenshotPath.get());
        }
    }

    private String getStepDescription(PickleStepTestStep testStep, Result.Type status) {
        final String stepKeyword = Optional.ofNullable(testSources.getKeywordFromSource(currentFeatureFile.get(), testStep.getStepLine())).orElse("UNDEFINED");
        final String stepText;
        if (isStepExecuted(status)) {
            stepText = replaceDirectoryKeysWithVals(testStep.getPickleStep().getText());
        } else {
            stepText = testStep.getPickleStep().getText();
        }
        return String.format("%s %s", stepKeyword, stepText);
    }

    private String replaceDirectoryKeysWithVals(String textToReplace) {
        if (!isStoreSentence(textToReplace)) {
            return new EngBaseStep().replaceParameterVals(textToReplace);
        } else {
            return textToReplace;
        }
    }

    private boolean isStoreSentence(String text) {
        if (text.matches("(.*)concatenated (string|value) of \"([^\"]*)\"(.*)")) {
            return false;
        } else {
            return (text.matches("store in new key \"([^\"]*)\"(.*)"));
        }
    }

    private void addScreenCaptureToAllure(String screenshotPath) {
        if (!screenshotPath.equalsIgnoreCase(ERROR_RESPONSE)) {
            addScreenCaptureFromPath(screenshotPath);
            TestContext.getInstance().testdataPut("fw.screenshotAbsolutePath", screenshotPath);
        }
    }

    private boolean isStepExecuted(Result.Type status) {
        return status == FAILED || status == PASSED;
    }


}
