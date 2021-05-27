package library.appium;


import library.common.Constants;
import library.common.Property;
import library.common.TestContext;
import library.selenium.exec.driver.factory.DriverContext;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MobileCapabilities {

    private final DesiredCapabilities desiredCapabilities;

    public MobileCapabilities() {

        desiredCapabilities = new DesiredCapabilities();

        Map<String, String> map = DriverContext.getInstance().getTechStack();
        for (Map.Entry<String, String> pair : map.entrySet()) {
            if (!pair.getKey().equalsIgnoreCase("serverName"))
                desiredCapabilities.setCapability(pair.getKey(), pair.getValue());
        }
    }

    public DesiredCapabilities getCap() {
        return desiredCapabilities;
    }

}