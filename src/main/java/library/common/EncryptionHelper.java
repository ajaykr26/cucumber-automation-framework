package library.common;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class EncryptionHelper {

    private static Logger logger = LogManager.getLogger(EncryptionHelper.class);

    private EncryptionHelper() {
    }

    public static void main(String[] args) throws Exception {

        String propFileExtention = ".properties";
        String envProp = "cukes.env";
        String propFilePath = Constants.CONFIG_PATH + "SecureText-" + Property.getVariable(envProp) + propFileExtention;
        String encryptedText = parseSecureText(propFilePath, args[0]);
        System.out.println("Encrypted String:" + encryptedText);
    }

    private static String parseSecureText(String propFilePath, String encryptedStringKey) {
        String encryptedString = Property.getProperty(propFilePath, encryptedStringKey);
        String decryptedString = null;
        if (encryptedString != null) {
            decryptedString = decrypt(encryptedString);
            return decryptedString;
        } else {
            logger.error("entry for key '{}' was not found in the SecureText property file", encryptedStringKey, propFilePath);
        }
        return null;
    }

    private static String encrypt(String plainString) {
        String encryptedString = null;
        if (plainString != null) {
            byte[] byteArray = Base64.encodeBase64(plainString.getBytes());
            encryptedString = new String(byteArray);
        } else {
            logger.error("please enter plain text to be encrypted");
        }
        return encryptedString;
    }

    private static String decrypt(String encryptedString) {
        String decryptedString = null;
        if (encryptedString != null) {
            byte[] decodedByteArray = Base64.decodeBase64(encryptedString.getBytes());
            decryptedString = new String(decodedByteArray);
            return decryptedString;
        } else {
            logger.error("encrypted string not found in the SecureText property file");
        }
        return null;
    }

}
