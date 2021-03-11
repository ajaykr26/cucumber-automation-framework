package library.selenium.utils;

import library.selenium.exec.BasePO;

import java.util.Arrays;

public class MiscMethods extends BasePO {
    public boolean valid_locator_type(String type) {
        return Arrays.asList("id", "class", "css", "name", "xpath").contains(type);
    }

    public void validateLocator(String type) throws Exception {
        if (!valid_locator_type(type))
            throw new Exception("Invalid locator type - " + type);
    }

    public boolean valid_option_by(String option_by) {
        return Arrays.asList("text", "value", "index").contains(option_by);
    }

    public void validateOptionBy(String optionBy) throws Exception {
        if (!valid_option_by(optionBy))
            throw new Exception("Invalid option by - " + optionBy);
    }
}
