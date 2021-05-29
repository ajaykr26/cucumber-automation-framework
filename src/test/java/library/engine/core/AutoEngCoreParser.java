package library.engine.core;

import io.cucumber.java8.En;
import library.common.Constants;
import library.common.Property;
import library.common.TestContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AutoEngCoreParser implements En {
    protected static final String ERROR = "ERROR";
    protected static final String REGEX_KEYNAME = "#\\((.*)\\)";
    protected static final String ENV_PROP = "cukes.env";
    protected static final String PROPERTIES_EXT = ".properties";

    protected Logger logger = LogManager.getLogger(this.getClass().getName());

    public static String parseValue(String string) {
        String parsedValue = null;
        String parsedKeyJSON = parseKeyJSON(string);
        String parsedKeyProps = parseKeyProps(string);
        if (parsedKeyJSON != null) {
            parsedValue = TestContext.getInstance().testdataGet(parsedKeyJSON).toString();
        } else if (parsedKeyProps != null) {
            parsedValue = Property.getProperty(Constants.ENVIRONMENTS, parseKeyProps(string));
        } else {
            parsedValue = string;
        }
        return parsedValue;
    }

    public static String parseKeyJSON(String string) {
        String dataParsed = null;

        Pattern patternJSON = Pattern.compile("#\\((.*)\\)");
        Matcher matcherJSON = patternJSON.matcher(string);

        if (matcherJSON.matches()) {
            return matcherJSON.group(1);
        } else {
            return null;
        }
    }

    public static String parseKeyProps(String string) {
        String dataParsed = null;
        Pattern patternProp = Pattern.compile("@\\((.*)\\)");
        Matcher matcherProp = patternProp.matcher(string);

        if (matcherProp.matches()) {
            return matcherProp.group(1);
        } else {
            return null;
        }

    }

//    public static String getValueOrVariable(String value){
//        String valToParse;
//        Pattern pattern = Pattern.compile(REGEX_KEYNAME);
//        Matcher matcher = pattern.matcher(value);
//
//        if (matcher.matches()){
//            String valToRetrieve = matcher.group(1);
//            if (TestContext.getInstance().testdataGet(valToRetrieve) == null){
//                throw new IllegalArgumentException(String.format("entry not found in the data dictionary for key: \"%s\".", valToRetrieve));
//            }else {
//                re
//            }
//        }
//    }

    public static String getValueOrVariable(String value) {
        String valToParse;
        Pattern pattern = Pattern.compile(REGEX_KEYNAME);
        Matcher matcher = pattern.matcher(value);

        if (matcher.matches()) {
            valToParse = matcher.group(1);
        } else {
            valToParse = value;
        }
        return valToParse;
    }

    public static Object getValueToObject(String value) {
        Object valToParse;
        Pattern pattern = Pattern.compile(REGEX_KEYNAME);
        Matcher matcher = pattern.matcher(value);

        if (matcher.matches()) {
            valToParse = matcher.group(1);
        } else {
            valToParse = value;
        }
        return valToParse;
    }
}
