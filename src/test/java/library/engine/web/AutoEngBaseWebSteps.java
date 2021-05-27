package library.engine.web;

import library.engine.core.AutoEngBaseCoreStep;
import library.engine.web.utils.*;
import library.selenium.utils.ClickMethods;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AutoEngBaseWebSteps extends AutoEngBaseCoreStep {

    public AutoEngBaseWebSteps() {
    }

    protected Logger logger = LogManager.getLogger(this.getClass().getName());

    public static ClickMethods getClickMethods() {
        return new ClickMethods();
    }

    public static AssertionMethods getAssertionMethods() {
        return new AssertionMethods();
    }

    public static TableMethods getTableMethods() {
        return new TableMethods();
    }

    public static MiscMethods getMiscMethods() {
        return new MiscMethods();
    }

    public static WaitMethods getWaitMethods() {
        return new WaitMethods();
    }

    public static InputMethods getInputMethods() {
        return new InputMethods();
    }

    public static NavigationMethods getNavigationMethods() {
        return new NavigationMethods();
    }
}
