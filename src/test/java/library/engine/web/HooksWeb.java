package library.engine.web;

import io.cucumber.core.api.Scenario;
import io.cucumber.java8.En;

public class HooksWeb implements En {
    public HooksWeb() {
        Before(35, (Scenario scenario) -> {
        });
        After(40, (Scenario scenario) -> {
        });
    }
}
