package library.common;

import java.io.File;

public class Constants {

    public static final String USER_DIR = System.getProperty("user.dir");
    public static final String BASE_PATH = USER_DIR + "/src/test/resources/";
    public static final String TESTDATA_PATH = BASE_PATH + "testdata/";
    public static final String PROPERTIES_PATH = BASE_PATH + "config/properties/";
    public static final String TECHSTACK_PATH = BASE_PATH + "config/techstacks/";

    public static final String DRIVER_PATH = USER_DIR + "/lib/drivers/";
    public static final String SCREENSHOT_PATH = USER_DIR + "/target/allure-results/screenshots/";
    public static final String ALLURE_RESULT_PATH = USER_DIR + "/target/allure-results";
    public static final String ACTUAL_IMAGE_PATH = USER_DIR + "/target/actual-image/";
    public static final String EXPECTED_IMAGE_PATH = USER_DIR + "/expected-image/";
    public static final String FEATURE_PATH = BASE_PATH + "features/";
    ;
    public static final String CLASSPATH = USER_DIR + "/target/test-classes/";
    public static final String SELENIUM_PATH = BASE_PATH + "config/selenium/";
    public static final String API_OBJECT_FOLDER = "apiobjects";
    public static final String STORE_UTILS_PATH = "/services/store/";
    public static final String VALIDATE_UTILS_PATH = "/services/validate/";
    public static final String SET_UTILS_PATH = "/services/set/";
    public static final String REMOVE_UTILS_PATH = "/services/remove/";
    public static final String CALL_UTILS_PATH = "/services/call/";
    public static final String PAGEOBJECTJARPATH = USER_DIR + "/target/test-classes/pageobjects/";
    public static final String PAGEOBJECTJAVAPATH = USER_DIR + "/src/test/java/pageobjects/";
    public static final String PAGEOBJECTEXTERNALJARPATH = USER_DIR + "/target/lib/";

    public static final String TESTDATA_EXCEL = TESTDATA_PATH + "TestData.xlsx";
    public static final String TECHSTACKS = TECHSTACK_PATH + Property.getVariable("cukes.techstack") + ".json";
    public static final String ENVIRONMENTS = BASE_PATH + "config/environment/" + Property.getVariable("cukes.environment") + ".properties";
    public static final String RUNTIME_PROP = PROPERTIES_PATH + "runtime.properties";
    public static final String TESTCASE_EXCEL = BASE_PATH + "scripts/TestCase.xlsx";
    public static final String API_OBJECT = CLASSPATH + API_OBJECT_FOLDER + File.separator;

//    public static final String SELENIUM_RUNTIME_PROP = SELENIUM_PATH + "runtime.properties";
//    public static final String APPIUM_RUNTIME_PROP = BASE_PATH + "config/mobile/runtime.properties";


}
