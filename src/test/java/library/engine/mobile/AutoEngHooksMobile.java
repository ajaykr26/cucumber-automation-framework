package library.engine.mobile;

import io.cucumber.core.api.Scenario;
import io.cucumber.java8.En;
import library.selenium.exec.driver.factory.DriverContext;

public class AutoEngHooksMobile implements En {
    public AutoEngHooksMobile() {
        After(40, (Scenario scenario) -> {
            if (DriverContext.getInstance().getMobileTechStack() != null) {
                DriverContext.getInstance().quitMobile();
            }
        });
    }
}
