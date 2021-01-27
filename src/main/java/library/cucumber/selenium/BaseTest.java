package library.cucumber.selenium;

import io.cucumber.testng.CucumberFeatureWrapper;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.PickleEventWrapper;
import io.cucumber.testng.TestNGCucumberRunner;
import library.common.Constants;
import library.common.JSONHelper;
import library.common.Property;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.*;

@CucumberOptions(
        plugin = {"json:target/cucumber-reports/runReport.json", "io.qameta.allure.cucumber4jvm.AllureCucumber4Jvm"},
        features = "classpath:features",
        glue = "library",
        strict = true
)
public class BaseTest extends library.selenium.exec.BaseTest {

    private TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());


    @Test(groups = "cucumber", description = "Run Cucumber Scenarios", dataProvider = "techStackWithScenarioList")
    public void scenario(Map<String, String> map, PickleEventWrapper pickleEventWrapper, CucumberFeatureWrapper cucumberFeatureWrapper) throws Throwable {
        testNGCucumberRunner.runScenario(pickleEventWrapper.getPickleEvent());
    }

    @DataProvider(name = "getTechStack", parallel = true)
    private Object[][] getTechStack() {
        Map<String, String> techStack = new HashMap<>();
        PropertiesConfiguration props =Property.getProperties(Constants.RUNTIME_PATH);
        if (Property.getVariable("techstack") != null) {
            techStack = JSONHelper.getJSONObjectToMap(Constants.TECHSTACK_PATH);
            if (!techStack.isEmpty()) {
                techStack.putAll(techStack);
            }else {
                logger.warn("Tech stack JSON file not found: {}. defaulting to local chrome driver instance.", Constants.TECHSTACK_PATH);
                techStack.put("serverName", "local");
                techStack.put("browserName", "chrome");
            }
        } else if (props.containsKey("serverName") && props.containsKey("browserName")){
            logger.info("techstack is not defined in vm arguments. getting the configuration from runtime.properties file");
            techStack.put("serverName", Property.getProperty(Constants.RUNTIME_PATH, "serverName").toLowerCase());
            techStack.put("browserName", Property.getProperty(Constants.RUNTIME_PATH, "browserName").toLowerCase());
        }else {
            logger.info("nether techstack is not defined in vm arguments nor the configuration is defined in runtime.properties file");
            techStack.put("serverName", Property.getProperty(Constants.RUNTIME_PATH, "serverName").toLowerCase());
            techStack.put("browserName", Property.getProperty(Constants.RUNTIME_PATH, "browserName").toLowerCase());
        }
        return new Object[][]{Collections.singletonList(techStack).toArray()};

    }

    @DataProvider(name = "techStackWithScenarioList", parallel = true)
    public Object[][] combineDataProvider() {
        List<Object[]> techStackList = Lists.newArrayList();
        List<Object[]> scenarioList = Lists.newArrayList();
        List<List<Object>> comboList = Lists.newArrayList();

        techStackList.addAll(Arrays.asList(getTechStack()));
        scenarioList.addAll(Arrays.asList(testNGCucumberRunner.provideScenarios()));

        techStackList.forEach(techStack ->
                scenarioList.forEach(scenario ->
                        comboList.add(Arrays.asList(techStack[0], scenario[0], scenario[1]))));
        Object[][] comboArray = new Object[comboList.size()][3];

        for (int i = 0; i < comboList.size(); i++) {
            comboArray[i][0] = comboList.get(i).get(0);
            comboArray[i][1] = comboList.get(i).get(1);
            comboArray[i][2] = comboList.get(i).get(2);
        }
        return comboArray;
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        testNGCucumberRunner.finish();
    }

}
