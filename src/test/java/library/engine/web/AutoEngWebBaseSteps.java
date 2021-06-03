package library.engine.web;

import library.engine.core.AutoEngCoreBaseStep;
import library.engine.web.utils.*;
import library.engine.web.utils.ClickMethods;
import library.selenium.exec.BasePO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AutoEngWebBaseSteps extends AutoEngCoreBaseStep {

    private BasePO baseWebPO;

    public AutoEngWebBaseSteps() {
    }

    public BasePO getBasePO() {
        logger.debug("obtaining an instance of base page object");
        if (baseWebPO == null)
            baseWebPO = new BasePO();
        return baseWebPO;
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

    public static JSMethods getJSMethods() {
        return new JSMethods();
    }
}
