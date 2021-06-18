package library.engine.api;

import io.cucumber.core.api.Scenario;
import io.cucumber.java8.En;
import library.common.TestContext;
import library.engine.core.objectmatcher.fetch.FetchPageObjects;

public class AutoEngAPIHooks implements En {
    public AutoEngAPIHooks() {
        Before(35, (Scenario scenario) -> {
            TestContext.getInstance().setOfPO().addAll(FetchPageObjects.populateListOfPO());
        });
        After(40, (Scenario scenario) -> {
        });
    }
}
