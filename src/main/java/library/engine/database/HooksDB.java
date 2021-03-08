package library.engine.database;

import io.cucumber.core.api.Scenario;
import io.cucumber.java8.En;

public class HooksDB implements En {
    public HooksDB() {
        Before(35, (Scenario scenario) -> {
        });
        After(40, (Scenario scenario) -> {
        });
    }
}