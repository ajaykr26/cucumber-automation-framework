package library.selenium.exec.driver.enums;

import java.util.Arrays;
import java.util.Optional;

public enum BrowserType {
    CHROME("chrome"),
    FIREFOX("firefox"),
    IE("internet explorer"),
    MSEDGE("microsoft edge"),
    HTMLUNIT("htmlunit"),
    PHANTOMJS("phantomjs");

    private String browserName;

    BrowserType(String browserName) {
        this.browserName = browserName;
    }

    public String getBrowserName() {
        return browserName;
    }

    public static BrowserType get(String browserName) {
        Optional<BrowserType> first = Arrays.stream(BrowserType.values())
                .filter(browser -> browser.getBrowserName()
                        .equalsIgnoreCase(browserName))
                .findFirst();

        return first.orElse(CHROME);
    }

}
