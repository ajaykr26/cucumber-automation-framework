package library.engine.web.steps;

import io.cucumber.java.en.Then;
import library.common.Constants;
import library.selenium.utils.FileDownloadHelper;
import library.common.FileHelper;
import library.common.TestContext;
import library.engine.web.AutoEngWebBaseSteps;
import library.reporting.Reporter;
import library.selenium.core.Element;
import library.selenium.core.LocatorType;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static library.engine.core.AutoEngCoreParser.parseValue;

public class AutoEngWebUtils extends AutoEngWebBaseSteps {

    @Then("^the user wait for page to load$")
    public void waitForPageLoad() {
        getBasePO().waitForPageToLoad();
    }

    @Then("^the user waits for \"([^\"]*)\" element to be (?:present|clickable|visible) on the \"([^\"]*)\" page$")
    public void userWaitForElement(String objectName, String option, String pageName) {
        switch (option) {
            case "visible":
                getWait().until(ExpectedConditions.visibilityOfElementLocated(getObject(objectName, pageName)));
                break;
            case "present":
                getWait().until(ExpectedConditions.presenceOfElementLocated(getObject(objectName, pageName)));
                break;
            case "clickable":
                getWait().until(ExpectedConditions.elementToBeClickable(getObject(objectName, pageName)));
                break;
        }
    }

    @Then("^the user (?:accept|dismiss) alert$")
    public void acceptAlert(String action) {
        getWait().until(ExpectedConditions.alertIsPresent());
        try {
            switch (action) {
                case "accept":
                    getDriver().switchTo().alert().accept();
                    break;
                case "dismiss":
                    getDriver().switchTo().alert().dismiss();
                    break;
            }
        } catch (NoAlertPresentException exception) {
            logger.error(exception.getMessage());
        }
    }

    @Then("^the user enters \"([^\"]*)\" into the textbook on the alert$")
    public void enterTextInToAlertTextbook(String textToEnter) {
        try {
            getWait().until(ExpectedConditions.alertIsPresent());
            getDriver().switchTo().alert().sendKeys(parseValue(textToEnter));
        } catch (NoAlertPresentException exception) {
            logger.error(exception.getMessage());
        }
    }

    @Then("^the user store text from from the alert and store into the data diction with dictionary key \"([^\"]*)\"$")
    public void storeTextFromAlert(String dictionaryKey) {
        try {
            getWait().until(ExpectedConditions.alertIsPresent());
            String textToStore = getDriver().switchTo().alert().getText();
            TestContext.getInstance().testdataPut(dictionaryKey, textToStore);
        } catch (NoAlertPresentException exception) {
            logger.error(exception.getMessage());
        }

    }

    @Then("^the user navigate back$")
    public void navigateBack() {
        getDriver().navigate().back();
    }

    @Then("^the user navigate forward$")
    public void navigateForward() {
        getDriver().navigate().forward();
    }

    @Then("^the user navigate refresh the page$")
    public void refreshPage() {
        getDriver().navigate().refresh();
    }

    @Then("^the user navigate to \"([^\"]*)\"$")
    public void navigateTo(String url) {
        url = parseValue(url);
        getDriver().navigate().to(url);
    }

    @Then("^the user switches to frame having frame (name|id): \"([^\"]*)\"$")
    public void switchFrameNameOrId(String frameNameOrId) {
        getDriver().switchTo().frame(frameNameOrId);
    }

    @Then("^the user switches to frame having frame xpath: \"([^\"]*)\"$")
    public void switchFrameLocatedBy(String locatorType, String locatorValue) {
        byObject = getObjectLocatedBy(locatorType, locatorValue);
        element = getWait().until(ExpectedConditions.presenceOfElementLocated(byObject));
        getDriver().switchTo().frame(element);
    }

    @Then("^the user switches to frame having element located by \"([^\"]*)\": \"([^\"]*)\"$")
    public void switchFrameHavingElementLocatedBy(String locatorType, String locatorValue) {
        List<WebElement> frames = getElementsLocatedBy(LocatorType.XPATH.toString(), "//iframe");
        for (WebElement frame : frames) {
            try {
                getDriver().switchTo().parentFrame();
                getDriver().switchTo().frame(frame);
            } catch (NoSuchFrameException exception) {
                continue;
            }
            elements = getDriver().findElements(getObjectLocatedBy(locatorType, locatorValue));
            if (elements.size() > 0)
                break;
        }
    }

    @Then("^the user switched to default content")
    public void switchToDefaultContent() {
        getDriver().switchTo().defaultContent();
    }

    @Then("^the user switched to parent frame")
    public void switchToParentFrame() {
        getDriver().switchTo().parentFrame();
    }

    @Then("^the user Creates a new window and switches to that$")
    public void createsNewWindowAndSwitchesThat() {
        getDriver().findElement(getObjectLocatedBy("cssSelector", "body")).sendKeys(Keys.CONTROL + "n");
        defaultWindowHandle = getDriver().getWindowHandle();
        windowHandles = getDriver().getWindowHandles();
        windowHandles.remove(defaultWindowHandle);
        for (String windowHandle : windowHandles) {
            if (!defaultWindowHandle.equals(windowHandle))
                getDriver().switchTo().window(windowHandle);
        }
    }

    @Then("^the user Creates a new tab and switches to that$")
    public void createsNewTabAndSwitchesToThat() {
        getDriver().findElement(getObjectLocatedBy("cssSelector", "body")).sendKeys(Keys.CONTROL + "t");
        defaultWindowHandle = getDriver().getWindowHandle();
        windowHandles = getDriver().getWindowHandles();
        windowHandles.remove(defaultWindowHandle);
        for (String windowHandle : windowHandles) {
            if (!defaultWindowHandle.equals(windowHandle))
                getDriver().switchTo().window(windowHandle);
        }
    }

    @Then("^the user switches to window or tab having title: \"([^\"]*)\"$")
    public void switchWindowByTitle(String winTile) {
        defaultWindowHandle = getDriver().getWindowHandle();
        windowHandles = getDriver().getWindowHandles();
        windowHandles.remove(defaultWindowHandle);
        for (String windowHandle : windowHandles) {
            String windowTitle = getDriver().switchTo().window(windowHandle).getTitle();
            if (windowTitle.equals(winTile))
                break;
        }
    }

    @Then("^the user switches to window or tab having current URL: \"([^\"]*)\"$")
    public void switchWindowByBy(String url) {
        url = parseValue(url);
        defaultWindowHandle = getDriver().getWindowHandle();
        windowHandles = getDriver().getWindowHandles();
        windowHandles.remove(defaultWindowHandle);
        for (String windowHandle : windowHandles) {
            String currentUrl = getDriver().switchTo().window(windowHandle).getCurrentUrl();
            if (currentUrl.equals(url))
                break;
        }
    }

    @Then("^the user switches to window or tab having element located by: \"([^\"]*)\" \"([^\"]*)\"$")
    public void switchWindowByBy(String locatorType, String locatorValue) {
        defaultWindowHandle = getDriver().getWindowHandle();
        windowHandles = getDriver().getWindowHandles();
        windowHandles.remove(defaultWindowHandle);
        for (String windowHandle : windowHandles) {
            getDriver().switchTo().window(windowHandle);
            elements = getDriver().findElements(getObjectLocatedBy(locatorType, locatorValue));
            if (elements.size() > 0)
                break;
        }
    }

    @Then("^the user closes current window in focus and switch to default window$")
    public void closeCurrentWindowAndSwitchDefaultWindow() {
        getDriver().close();
        getDriver().switchTo().window(defaultWindowHandle);
    }

    @Then("^the user add cookie with key \"([^\"]*)\" and value \"([^\"]*)\" to the current session$")
    public void addCookie(String key, String value) {
        getDriver().manage().addCookie(new Cookie(key, value));
    }

    @Then("^the user delete cookie with name \"([^\"]*)\" from the current session$")
    public void deleteCookie(String name) {
        getDriver().manage().deleteCookieNamed(name);
    }

    @Then("^the user delete all cookies from the current session$")
    public void deleteAllCookies() {
        getDriver().manage().deleteAllCookies();
    }

    @Then("^the user hover on the \"([^\"]*)\" element on the \"([^\"]*)\" page$")
    public void hoverOnElementOnPage(String objectName, String pageName) {
        Actions action = new Actions(getDriver());
        action.moveToElement(getElement(objectName, pageName)).build().perform();
    }

    @Then("^the user hover on the element having \"([^\"]*)\": \"([^\"]*)\"$")
    public void hoverOnElement(String method, String locator) {
        Actions action = new Actions(getDriver());
        action.moveToElement(getElementLocatedBy(method, locator)).build().perform();
    }

    @Then("^the user uploads the file \"([^\"]*)\" using \"([^\"]*)\" element at the \"([^\"]*)\" page$")
    public void uploadFiles(String filepath, String objectName, String pageName) {
        filepath = parseValue(filepath);
        File fileToUpload = new File(filepath);
        if (!fileToUpload.isAbsolute()) {
            fileToUpload = new File(Paths.get(Constants.TESTDATA_PATH + filepath).toString());
        }
        if (fileToUpload.exists()) {
            final Path pathToFile = fileToUpload.toPath();
            final String fileContent = replaceParameterValues(FileHelper.getFileAsString(pathToFile.toAbsolutePath().toString(), "\n"));
            Path tempFileToUpload = FileHelper.writeTempFile(FileHelper.getFileNameWithoutExtension(pathToFile),
                    FileHelper.getFileNameExtension(pathToFile), fileContent);
            assert tempFileToUpload != null;
            getElement(objectName, pageName).sendKeys(tempFileToUpload.toAbsolutePath().toString());
        } else {
            String message = String.format("file not found at path: %s", fileToUpload);
            logger.error(message);
            Reporter.addStepLog("ERROR", message);
        }
    }

    @Then("^the user downloads the file from \"([^\"]*)\" element at the \"([^\"]*)\" page and save full path to data dictionary with key \"([^\"]*)\"$")
    public void downloadFiles(String objectName, String pageName, String dictionaryKey) throws URISyntaxException {
        String pathToCurrentCerts = setTrustStoreBasedOnEnv();
        FileDownloadHelper downloadHelper = new FileDownloadHelper(getDriver());
        Element downloadLink = new Element(getDriver(), getElement(objectName, pageName));

        downloadHelper.setURISpecifiedInAnchorElement(downloadLink);
        File downloadedFile = downloadHelper.downloadFile("");
        if (downloadedFile != null && downloadedFile.exists()) {
            TestContext.getInstance().testdataPut(parseDictionaryKey(dictionaryKey), downloadedFile.getAbsolutePath());
            logStepMessage(String.format("file downloaded to path: %s", downloadedFile.getAbsolutePath()));
        } else {
            logStepMessage("unable to download file");
        }
        setTrustStore(pathToCurrentCerts);
    }


}
