package library.common;


public class Constants {

    public static final String BASE_PATH = System.getProperty("user.dir") + "/src/main/resources/";
    public static final String DRIVER_PATH = BASE_PATH + "drivers/";
    public static final String SCREENSHOT_PATH = BASE_PATH + "screenshots/";
    public static final String CONFIG_PATH = BASE_PATH + "config/";
    public static final String RUNTIME_PATH = BASE_PATH + "config/runtime.properties";
    public static final String FEATURE_PATH = BASE_PATH + "features/";;
    public static final String TECHSTACK_PATH = BASE_PATH + "techstacks/"+ Property.getVariable("TechStack")+".json";

}
