package library.engine.web.utils;

import library.selenium.exec.BasePO;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class NavigationMethods extends BasePO {
    private WebElement element = null;

    public Keys getKey() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win"))
            return Keys.CONTROL;
        else if (os.contains("nux") || os.contains("nix"))
            return Keys.CONTROL;
        else if (os.contains("mac"))
            return Keys.COMMAND;
        else
            return null;
    }

    public void zoomInOut(String inOut) {
        WebElement Sel = getDriver().findElement(getObject("tagName", "html"));
        if (inOut.equals("ADD"))
            Sel.sendKeys(Keys.chord(getKey(), Keys.ADD));
        else if (inOut.equals("SUBTRACT"))
            Sel.sendKeys(Keys.chord(getKey(), Keys.SUBTRACT));
        else if (inOut.equals("reset"))
            Sel.sendKeys(Keys.chord(getKey(), Keys.NUMPAD0));
    }

    public void zoomInOutTillElementDisplay(String locatorType, String inOut, String locatorText) {
        Actions action = new Actions(getDriver());
        element = getWait().until(ExpectedConditions.presenceOfElementLocated(getObject(locatorType, locatorText)));
        while (true) {
            if (element.isDisplayed())
                break;
            else
                action.keyDown(getKey()).sendKeys(inOut).keyUp(getKey()).perform();
        }
    }

    public void resizeBrowser(int width, int height) {
        getDriver().manage().window().setSize(new Dimension(width, height));
    }

    public void maximizeBrowser() {
        getDriver().manage().window().maximize();
    }

    public void hoverOverElement(String locatorType, String locatorText) throws Throwable {
        Actions action = new Actions(getDriver());
        element = getWait().until(ExpectedConditions.presenceOfElementLocated(getObject(locatorType, locatorText)));
        action.moveToElement(element).perform();
    }
    
    public void scrollToElement(String locatorType, String locatorText) throws Throwable {
        element = getWait().until(ExpectedConditions.presenceOfElementLocated(getObject(locatorType, locatorText)));
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].scrollIntoView();", element);
    }

    public void scrollPage(String to) throws Exception {
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        if (to.equals("end"))
            executor.executeScript("window.scrollTo(0,Math.max(document.documentElement.scrollHeight,document.body.scrollHeight,document.documentElement.clientHeight));");
        else if (to.equals("top"))
            executor.executeScript("window.scrollTo(Math.max(document.documentElement.scrollHeight,document.body.scrollHeight,document.documentElement.clientHeight),0);");
        else
            throw new Exception("Exception : Invalid Direction (only scroll \"top\" or \"end\")");
    }

}
