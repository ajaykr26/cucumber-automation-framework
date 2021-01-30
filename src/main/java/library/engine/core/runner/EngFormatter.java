package library.engine.core.runner;

import cucumber.api.PickleStepTestStep;
import cucumber.api.event.*;
import cucumber.runtime.formatter.TestSourcesModelProxy;
import library.common.StringHelper;
import library.common.TestContext;
import org.apache.logging.log4j.ThreadContext;

import java.util.Map;

public class EngFormatter implements ConcurrentEventListener {
    private final EventHandler<TestSourceRead> featureStartedHandler = this::handleFeatureStartedHandler;
    private final EventHandler<TestCaseStarted> caseStartedHandler = this::handleCaseStartedHandler;
    private final EventHandler<TestCaseFinished> caseFinishedHandler = this::handleCaseFinishedHandler;
    private final EventHandler<TestStepStarted> stepStartedHandler = this::handleStepStartedHandler;
    private final EventHandler<TestStepFinished> stepFinishedHandler = this::handleStepFinishedHandler;
    private final TestSourcesModelProxy testSources = new TestSourcesModelProxy();
    private final ThreadLocal<String> currentFeatureFile = new InheritableThreadLocal<>();
    private final ThreadLocal<ScrenshotHandler> screenshotHandler = new InheritableThreadLocal<>();
    private final ThreadLocal<String> screenshotPath = new InheritableThreadLocal<>();
    private final ThreadLocal<Map<String, String>> screenshotBehaviorMap = new InheritableThreadLocal<>();
    private final ThreadLocal<Map<String, String>> classToWindowMapping = new InheritableThreadLocal<>();
    private static final String FW_SCENARIO_NAME = "fw.scenarioName";
    private static final String IGNORE = "Ignore";


    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {
        eventPublisher.registerHandlerFor(TestSourceRead.class, featureStartedHandler);
        eventPublisher.registerHandlerFor(TestCaseStarted.class, caseStartedHandler);
        eventPublisher.registerHandlerFor(TestCaseFinished.class, caseFinishedHandler);
        eventPublisher.registerHandlerFor(TestStepStarted.class, stepStartedHandler);
        eventPublisher.registerHandlerFor(TestStepFinished.class, stepFinishedHandler);

    }

    private void handleStepStartedHandler(TestStepStarted testStepStarted) {
    }

    private void handleStepFinishedHandler(TestStepFinished event) {
//        if (event.testStep instanceof PickleStepTestStep){
//            setWindowType(event.testStep.getCodeLocation());
//
//            final PickleStepTestStep testStep=(PickleStepTestStep) event.testStep ;
//            if (isBeforeStep(event.testStep.getCodeLocation(),testStep.getPickleStep().getText())){
//                String stepText=getStepDescription()
//            }
//        }
    }

    private void handleCaseFinishedHandler(TestCaseFinished event) {
    }

    private void handleCaseStartedHandler(TestCaseStarted event) {
        currentFeatureFile.set(event.testCase.getUri());
        TestContext.getInstance().testdataPut("fw.featureName", this.testSources.getFeature(this.currentFeatureFile.get()).getName());
        TestContext.getInstance().testdataPut(FW_SCENARIO_NAME, event.testCase.getName());
        TestContext.getInstance().testdataPut("fw.logFileName", String.format("%s/%s",
                TestContext.getInstance().testdataGet("fw.featureName"),
                TestContext.getInstance().testdataGet(FW_SCENARIO_NAME)));
        ThreadContext.put("logFileName", TestContext.getInstance().testdataGet("fw.logFileName").toString());

    }

    private void handleFeatureStartedHandler(final TestSourceRead event) {
        testSources.addTestSourceReadEvent(event.uri, event);
    }

}
