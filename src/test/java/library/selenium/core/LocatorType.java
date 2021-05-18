package library.selenium.core;

import java.util.Arrays;
import java.util.Optional;

public enum LocatorType {
    XPATH("xpath"),
    NAME("name"),
    ID("id"),
    TEXT("text"),
    CSS("cssSelector"),
    CLASS_NAME("className"),
    LINK_TEXT("linkText"),
    PARTIAL_LINK_TEXT("partialLinkText"),
    TAGE_NAME("tagName");

    private final String locatorName;

    LocatorType(String locatorName) {
        this.locatorName = locatorName;
    }

    public String getLocatorName() {
        return locatorName;
    }

    public static LocatorType get(String locatorName) {
        Optional<LocatorType> first = Arrays.stream(LocatorType.values())
                .filter(browser -> browser.getLocatorName()
                        .equalsIgnoreCase(locatorName))
                .findFirst();

        return first.orElse(XPATH);
    }

}
