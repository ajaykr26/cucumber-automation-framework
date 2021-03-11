package library.selenium.core;

import library.selenium.utils.*;

public class FactoryMethod {
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
    public static CommonMethods getCommonMethods() {
        return new CommonMethods();
    } public static JSMethods getJSMethods() {
        return new JSMethods();
    }
}
