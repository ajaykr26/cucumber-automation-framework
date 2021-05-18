package library.common;

public class Constants {

    public static final String USER_DIR = System.getProperty("user.dir");
    public static final String BASE_PATH = USER_DIR + "/src/test/resources/";
    public static final String DRIVER_PATH = USER_DIR + "/lib/drivers/";
    public static final String SCREENSHOT_PATH = USER_DIR + "/target/allure-results/screenshots/";
    public static final String ALLURE_RESULT_PATH = USER_DIR + "/target/allure-results";
    public static final String ACTUAL_IMAGE_PATH = USER_DIR + "/target/actual-image/";
    public static final String EXPECTED_IMAGE_PATH = USER_DIR + "/expected-image/";
    public static final String CONFIG_PATH = BASE_PATH + "config/";
    public static final String RUNTIME_PATH = BASE_PATH + "config/selenium/properties/runtime.properties";
    public static final String MOBILE_RUNTIME_PROP = BASE_PATH + "config/appium/properties/runtime.properties";
    public static final String FEATURE_PATH = BASE_PATH + "features/";;
    public static final String TESTDATA_EXCEL_PATH = BASE_PATH + "testdata/TestData.xlsx";;
    public static final String TESTDATA_PATH = BASE_PATH + "testdata/";;
    public static final String TECHSTACK_PATH = BASE_PATH + "config/selenium/techstacks/" + Property.getVariable("cukes.techstack")+".json";
    public static final String APPIUM_TECHSTACKS = BASE_PATH + "config/appium/techstacks/" + Property.getVariable("cukes.techstack")+".json";
    public static final String ENVIRONMENT_PATH = BASE_PATH + "config/environment/"+ Property.getVariable("cukes.environment")+".properties";


}
