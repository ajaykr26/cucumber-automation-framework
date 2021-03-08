package library.engine.core;

import io.cucumber.core.api.Scenario;
import io.cucumber.java8.En;

public class EngHooks implements En {
    public EngHooks() {
        Before(15, (Scenario scenario) -> {
        });
        After(15, (Scenario scenario) -> {
        });
    }
}
