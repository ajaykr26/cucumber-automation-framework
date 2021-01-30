package library.selenium.exec;

import library.common.Property;
import library.selenium.core.SeleConstants;

public class ExecConstants extends SeleConstants {
    public static final String TECHSTACK_PATH = BASE_PATH + "config/techstacks/"+ Property.getVariable("techstack")+".json";
    public static final String ALLURE_RESULT_PATH = USER_DIR + "/target/allure-results/";

}
