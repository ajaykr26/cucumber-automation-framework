package library.cucumber.common;

import io.cucumber.testng.CucumberFeatureWrapper;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.PickleEventWrapper;
import io.cucumber.testng.TestNGCucumberRunner;
import library.selenium.core.BaseTest;
import org.apache.commons.compress.utils.Lists;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@CucumberOptions(
        plugin = {"json:target/cucumber-reports/runReport.json", "io.qameta.allure.cucumber4jvm.AllureCucumber4Jvm"},
        features = "classpath:features",
        glue = "library.cucumber",
        strict = true
)
public class CucumberBaseTest extends BaseTest {

    private TestNGCucumberRunner testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());


    @Test(groups = "cucumber", description = "Run Cucumber Scenarios", dataProvider = "techstackwithScenarioList")
    public void scenario(Map<String, String> map, PickleEventWrapper pickleEventWrapper, CucumberFeatureWrapper cucumberFeatureWrapper) throws Throwable {
        testNGCucumberRunner.runScenario(pickleEventWrapper.getPickleEvent());
    }

    @DataProvider(name = "techstackwithScenarioList", parallel = true)
    public Object[][] combineDataProvider() {
        List<Object[]> techStackList = org.apache.commons.compress.utils.Lists.newArrayList();
        List<Object[]> scenarioList = org.apache.commons.compress.utils.Lists.newArrayList();
        List<List<Object>> comboList = Lists.newArrayList();

        try {
            techStackList.addAll(Arrays.asList(techStackJSON()));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

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
    public void tearDownClass(){
        testNGCucumberRunner.finish();
    }

}
