package runners;

import io.cucumber.testng.CucumberOptions;
import library.common.Property;
import library.cucumber.core.CukesConstants;
import library.engine.core.runner.EngBaseTest;
import org.testng.annotations.AfterClass;

import java.text.SimpleDateFormat;
import java.util.Date;

import static library.common.FileHelper.copyDir;

@CucumberOptions(tags = {"@SmokeSuite"})
public class Regression extends EngBaseTest {

    @AfterClass
    private void copyAllureResult() {
        if (Boolean.parseBoolean(Property.getProperty(CukesConstants.RUNTIME_PATH, "saveAllureResult"))) {
            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
            String target = CukesConstants.USER_DIR + "/allure-results/" + timeStamp;
            copyDir(CukesConstants.ALLURE_RESULT_PATH, target, false);
        }
    }
}