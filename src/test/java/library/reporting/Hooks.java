package library.reporting;

import io.cucumber.core.api.Scenario;
import io.cucumber.java8.En;
import library.common.Property;
import library.common.TestContext;
import library.cucumber.core.CukesConstants;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static library.common.FileHelper.copyDir;
import static library.reporting.Reporter.addScreenCaptureFromPath;
import static library.reporting.Reporter.getReportPath;

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

        });

    }
}
