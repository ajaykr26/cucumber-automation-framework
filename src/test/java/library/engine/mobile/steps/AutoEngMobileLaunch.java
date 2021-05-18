package library.engine.mobile.steps;

import io.cucumber.java.en.Given;
import library.engine.mobile.BaseMobileSteps;

public class AutoEngMobileLaunch extends BaseMobileSteps {

//    @Given("^the user launch the mobile application \"([^\"]*)\"$")
//    public void launchMobileApplication(String applicationName) {
//        System.setProperty("fw.mobileAppName", applicationName);
//        launchMobile();
//        launchMobileApplication(applicationName, getMobileDriver());
//    }


    @Given("^the user closes the mobile application \"([^\"]*)\"$")
    public void closeMobileApplication(String applicationName) {
        closeMobileApplication(applicationName, getMobileDriver());
    }

    @Given("^the user switches to the mobile context \"([^\"]*)\"$")
    public void switchesMobileContext(String context) {
        switchContext(context);
    }

}
