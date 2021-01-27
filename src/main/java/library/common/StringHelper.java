package library.common;

import java.util.Arrays;

public class StringHelper {

    private StringHelper(){}

    public static String removeSpecialChars(String stringWithSpecialChars){
        stringWithSpecialChars=stringWithSpecialChars.replaceAll("\\s+","");
        return stringWithSpecialChars.replaceAll("[^a-zA-Z0-9_-]+","");
    }
    public static String removeAllWhiteSpace(String stringWithWhiteSpace){
        return stringWithWhiteSpace.replaceAll("\\s+","");
    }
    public static String getClassShortName(String classLocation){
        return Arrays.asList(classLocation.split("\\.")).get(0);
    }


}
