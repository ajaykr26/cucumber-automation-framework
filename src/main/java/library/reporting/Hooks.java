package library.reporting;

import io.cucumber.core.api.Scenario;
import io.cucumber.java8.En;
import library.common.Property;
import library.common.TestContext;
import library.cucumber.core.CukesConstants;

import java.text.SimpleDateFormat;
import java.util.Date;

import static library.common.FileHelper.copyDir;
import static library.reporting.Reporter.addScreenCaptureFromPath;

public class Hooks implements En {

    public Hooks() {
        After(10, (Scenario scenario) -> {
            if (scenario.isFailed()) {
                String screenshotOnFailure = Property.getVariable("screenshotOnFailure");
                if (Boolean.parseBoolean(screenshotOnFailure)) {
                    String screenshotPath = (String) TestContext.getInstance().testdataGet("fw.screenshotAbsolutePath");
                    if (screenshotPath != null) {
                        addScreenCaptureFromPath(screenshotPath);
                    }
                }
            }
            copyAllureResult();
        });
    }

    private static void copyAllureResult() {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String target = CukesConstants.ALLURE_RESULT_PATH + timeStamp;
        copyDir(CukesConstants.ALLURE_RESULT_PATH, target, true);
    }


}
