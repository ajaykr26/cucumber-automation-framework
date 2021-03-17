package library.selenium.utils;

import gherkin.ast.Scenario;
import library.cucumber.core.CukesConstants;
import library.selenium.exec.BasePO;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFileFilter;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import javax.imageio.ImageIO;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ScreenShotMethods extends BasePO {
    protected WebDriver driver = getDriver();

    public void takeScreenShot() throws IOException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar cal = Calendar.getInstance();
        System.out.println(dateFormat.format(cal.getTime()));

        String scrFilepath = scrFile.getAbsolutePath();
        System.out.println("scrFilepath: " + scrFilepath);

        File currentDirFile = new File("Screenshots");
        String path = currentDirFile.getAbsolutePath();
        System.out.println("path: " + path + "+++");

        System.out.println("****\n" + path + "\\screenshot" + dateFormat.format(cal.getTime()) + ".png");

        FileUtils.copyFile(scrFile, new File(path + "\\screenshot" + dateFormat.format(cal.getTime()) + ".png"));
		
		/*DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		System.out.println(dateFormat.format(cal.getTime()));*/
    }
	 /* cur_time = Time.now.strftime('%Y%m%d%H%M%S%L')
	  $driver.save_screenshot('./features/screenshots/screenshot' + cur_time + '.png')*/

    public static void insertImageToWord(String scenario, Boolean flag) throws IOException, InvalidFormatException {
        if (flag) {
            String directoryPath = CukesConstants.SCREENSHOT_PATH + scenario;
            final Stream<Path> fileStream = Files.list(Paths.get(directoryPath));
            List<File> files = fileStream
                    .map(Path::toFile)
                    .sorted(Comparator.comparing(File::lastModified))
                    .collect(Collectors.toList());
            XWPFDocument doc = new XWPFDocument();
            XWPFParagraph p = doc.createParagraph();
            XWPFRun xwpfRun = p.createRun();

            for (File file : files) {
                int format = XWPFDocument.PICTURE_TYPE_PNG;
                p.setAlignment(ParagraphAlignment.CENTER);
                xwpfRun.addBreak();
                xwpfRun.addPicture(new FileInputStream(file), format, file.getName(), Units.toEMU(480), Units.toEMU(240));
            }
            FileOutputStream out = new FileOutputStream(directoryPath + File.separator + scenario + ".doc");
            doc.write(out);
            out.close();
        }
    }
}
