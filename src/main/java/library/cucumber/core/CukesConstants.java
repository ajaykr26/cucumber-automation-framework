package library.cucumber.core;

import library.common.Property;

public class CukesConstants {
    public static final String USER_DIR = System.getProperty("user.dir");
    public static final String BASE_PATH = USER_DIR + "/src/main/resources/";
    public static final String DRIVER_PATH = USER_DIR + "/lib/drivers/";
    public static final String SCREENSHOT_PATH = BASE_PATH + "target/allure-results/screenshots/";
    public static final String ALLURE_RESULT_PATH = USER_DIR + "/target/allure-results/";
    public static final String CONFIG_PATH = BASE_PATH + "config/";
    public static final String RUNTIME_PATH = BASE_PATH + "config/properties/runtime.properties";
    public static final String FEATURE_PATH = BASE_PATH + "features/";;
    public static final String TECHSTACK_PATH = BASE_PATH + "config/techstacks/"+ Property.getVariable("techstack")+".json";
    public static final String ENVIRONMENT_PATH = BASE_PATH + "config/environment/"+ Property.getVariable("environment")+".properties";

}
