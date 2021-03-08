package library.selenium.exec.driver.factory;

import library.selenium.exec.driver.utils.DriverContextUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Map;


public class DriverContext {

    private static ThreadLocal<DriverContext> instance = ThreadLocal.withInitial(DriverContext::new);
    private Map<String, String> techStack = null;
    private Map<String, String> mobileTechStack = null;
    private boolean keepBrowserOpen = false;
    private DriverManager webDriverManager;
    private DriverManager mobileDriverManager;

    private DriverContext() {
    }

    public static synchronized DriverContext getInstance() {
        return instance.get();
    }

    public static void removeInstance() {
        instance.remove();
    }

    public void setDriverContext(Map<String, String> techStack) {
        setTechStack(techStack);
    }

    public void updateDriverContext(Map<String, String> newTechStack) {
        DriverContextUtil.updateDriverContext(newTechStack);
    }

    public void updateDriverPath(String browserName) {
        DriverContextUtil.updateDriverPath(browserName);
    }

    public Map<String, String> getTechStack() {
        return this.techStack;
    }

    public void setTechStack(Map<String, String> techStack) {
        this.techStack = techStack;
    }

    public String getBrowserName() {
        if (techStack == null) {
            return null;
        } else {
            return this.techStack.get("browserName") == null ? this.techStack.get("browser") : this.techStack.get("browserName");
        }
    }

    public String getBrowserVersion() {
        if (techStack == null) {
            return null;
        } else {
            return this.techStack.get("version") == null ? this.techStack.get("browser_version") : this.techStack.get("version");
        }
    }

    public String getPlatform() {
        if (techStack == null) {
            return null;
        } else {
            return this.techStack.get("platform") == null ? this.techStack.get("os") + "_" + this.techStack.get("os_version") : this.techStack.get("platform");
        }
    }

    public Boolean getKeepBrowserOpen() {
        return this.keepBrowserOpen;
    }

    public void setKeepBrowserOpen(Boolean keepBrowserOpen) {
        this.keepBrowserOpen = keepBrowserOpen;
    }

    public WebDriver getWebDriver() {
        if (webDriverManager == null) {
            webDriverManager = DriverFactory.createDriver();
        }
        return webDriverManager.getDriver();
    }

    public WebDriverWait getWait(){
        return webDriverManager.getWait();
    }

    public DriverManager getWebDriverManager() {
        return webDriverManager;
    }

    public DriverManager getMobileDriverManager() {
        return mobileDriverManager;
    }

    public void quit() {
        if (webDriverManager != null) {
            webDriverManager.quitDriver();
        }
    }
}


