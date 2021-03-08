package library.engine.core;

import io.cucumber.java8.En;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static library.engine.core.EngParser.parseValue;

public class EngBaseStep implements En {
    protected Logger logger = LogManager.getLogger(this.getClass().getName());

    protected static final String REGEX_FULLKEY = "(#\\(\\S*?\\))";

    public String replaceParameterVals(String stringToReplace) {
        StringBuffer stringBuffer = new StringBuffer();
        Pattern pattern = Pattern.compile(REGEX_FULLKEY);
        Matcher matcher = pattern.matcher(stringToReplace);

        while (matcher.find()) {
            String fullKey = matcher.group(1);
            matcher.appendReplacement(stringBuffer, Matcher.quoteReplacement(getDictionaryValOrRealVal(fullKey)));
        }
        matcher.appendTail(stringBuffer);
        return stringBuffer.toString();
    }

    protected String getDictionaryValOrRealVal(String value) {
        try {
            return parseValue(value);
        } catch (IllegalArgumentException exception) {
            logger.error(String.format("%s string will not include value at key.", exception.getMessage()));
        }
        return null;
    }

}
