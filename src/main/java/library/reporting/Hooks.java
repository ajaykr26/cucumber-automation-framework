package library.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.cucumber.core.api.Scenario;
import io.cucumber.java8.En;
import library.common.Property;
import library.common.TestContext;

import java.io.File;

import static library.reporting.Reporter.addScreenCaptureFromPath;

public class Hooks implements En {

	public Hooks(){
		After(10, (Scenario scenario) -> {
			if (scenario.isFailed()){
				String screenshotOnFailure = Property.getVariable("screenshotOnFailure");
				if (Boolean.parseBoolean(screenshotOnFailure)){
					String screeshotPath = (String) TestContext.getInstance().testdataGet("fw.screenshotAbsolutePath");
					if (screeshotPath!= null){
						addScreenCaptureFromPath(screeshotPath);
					}
				}
			}
		});
	}


}

