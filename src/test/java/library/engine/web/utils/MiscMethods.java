package library.engine.web.utils;

import library.selenium.exec.BasePO;

import java.util.Arrays;

public class MiscMethods extends BasePO {
    public boolean validLocatorType(String locatorType) {
        return Arrays.asList("id", "class", "css", "name", "xpath").contains(locatorType);
    }

    public void validateLocator(String locatorType) throws Exception {
        if (!validLocatorType(locatorType))
            throw new Exception("Invalid locator type - " + locatorType);
    }

    public boolean validAttribute(String attribute) {
        return Arrays.asList("text", "value", "index").contains(attribute);
    }

    public void validateOptionBy(String attribute) throws Exception {
        if (!validAttribute(attribute))
            throw new Exception("Invalid attribute - " + attribute);
    }
}
