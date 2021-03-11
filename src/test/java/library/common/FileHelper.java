package library.common;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.*;
import java.util.Iterator;

public class FileHelper {
    private FileHelper() {

    }

    private static Logger logger = LogManager.getLogger(FileHelper.class);

    public static void copyDir(String src, String dest, boolean overwrite) {
        try {
            Files.walk(Paths.get(src)).forEach(a -> {
                Path b = Paths.get(dest, a.toString().substring(src.length()));
                try {
                    if (!a.toString().equals(src))
                        Files.copy(a, b, overwrite ? new CopyOption[]{StandardCopyOption.REPLACE_EXISTING} : new CopyOption[]{});
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadProperties(String propFilePath) {
        PropertiesConfiguration props = Property.getProperties(propFilePath);
        if (props != null) {
            Iterator<String> iterator = props.getKeys();
            while (iterator.hasNext()) {
                String key = iterator.next();
                if (key != null) {
                    TestContext.getInstance().propDataPut(key, props.getProperty(key));
                }
            }
        }
    }
}
