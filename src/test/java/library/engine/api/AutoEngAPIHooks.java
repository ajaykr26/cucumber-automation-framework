package library.engine.api;

import io.cucumber.core.api.Scenario;
import io.cucumber.java8.En;


public class AutoEngAPIHooks implements En {
    public AutoEngAPIHooks() {
        Before(35, (Scenario scenario) -> {
        });
        After(35, (Scenario scenario) -> {
        });
    }
}
