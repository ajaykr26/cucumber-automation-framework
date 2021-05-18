package library.engine.web.utils;

import library.selenium.exec.BasePO;
import org.openqa.selenium.WebDriver;

public class JSMethods extends BasePO {
    protected WebDriver driver = getDriver();

    public void handleAlert(String decision) {
        if (decision.equals("accept"))
            driver.switchTo().alert().accept();
        else
            driver.switchTo().alert().dismiss();
    }
}
