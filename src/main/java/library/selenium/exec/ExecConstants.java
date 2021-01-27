package library.selenium.exec;

import library.common.Property;
import library.selenium.core.SeleConstants;

public class ExecConstants extends SeleConstants {
    public static final String TECHSTACK_PATH = BASE_PATH + "config/techstacks/"+ Property.getVariable("cukes.techstack")+".json";

}
