package library.engine.core;

import io.cucumber.java8.En;
import library.common.Constants;
import library.common.Property;
import library.common.TestContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EngParser implements En {
    protected Logger logger = LogManager.getLogger(this.getClass().getName());

    public static String parseValue(String string) {
        String parsed_value = null;
        String parsedkeyJSON = parse_keyJSON(string);
        String parsedkeyProps = parse_keyProps(string);
        if (parsedkeyJSON != null) {
            parsed_value = TestContext.getInstance().testdataGet(parsedkeyJSON).toString();
        } else if (parsedkeyProps != null) {
            parsed_value = Property.getProperty(Constants.ENVIRONMENT_PATH, parse_keyProps(string));
        } else {
            parsed_value = string;
        }
        return parsed_value;
    }

    public static String parse_keyJSON(String string) {
        String parsed_data = null;

        Pattern patternJSON = Pattern.compile("#\\((.*)\\)");
        Matcher matcherJSON = patternJSON.matcher(string);

        if (matcherJSON.matches()) {
            return matcherJSON.group(1);
        } else {
            return null;
        }
    }

    public static String parse_keyProps(String string) {
        String parsed_data = null;
        Pattern patternProp = Pattern.compile("@\\((.*)\\)");
        Matcher matcherProp = patternProp.matcher(string);

        if (matcherProp.matches()) {
            return matcherProp.group(1);
        } else {
            return null;
        }

    }

}
