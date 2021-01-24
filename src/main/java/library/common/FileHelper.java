package library.common;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;

public class FileHelper {
    private FileHelper(){

    }

    private static Logger logger = LogManager.getLogger(FileHelper.class);


    public static void loadProperties(String propFilePath){
        PropertiesConfiguration props = Property.getProperties(propFilePath);
        if (props!= null){
            Iterator<String> iterator = props.getKeys();
            while (iterator.hasNext()){
                String key = iterator.next();
                if (key!=null){
                    TestContext.getInstance().propDataPut(key, props.getProperty(key));
                }
            }
        }
    }
}
