package runners;

import io.cucumber.testng.CucumberOptions;
import library.common.Constants;
import library.common.Property;
import library.engine.core.runner.EngBaseTest;
import org.testng.annotations.AfterClass;

import java.text.SimpleDateFormat;
import java.util.Date;

import static library.common.FileHelper.copyDir;

@CucumberOptions(tags = {"@SmokeSuite", "not @ignore"})
public class Regression extends EngBaseTest {
}