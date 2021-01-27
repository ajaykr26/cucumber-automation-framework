package library.engine.web.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java8.En;
import library.reporting.Reporter;
import library.selenium.exec.BasePO;

import static library.selenium.core.FactoryMethod.*;

public class Steps_Clicks extends BasePO implements En {

    @Then("^the user clicks on the element \"([^\"]*)\" at the page \"([^\"]*)\"$")
    public void userclickOnElement(String objectName, String pageName) throws Throwable {
        getElement(objectName, pageName).click();
        Reporter.addStepLog("clicked to the button");
    }

    @Then("^the user clicks at matching cell where the value (.+) is present in the column (.+) in the table having (.+) \"(.*?)\"$")
    public void validateTextFoundInColum(String textToFind, String columnName, String type, String accessName) throws Exception {
        getMiscMethods().validateLocator(type);
        getTableMethods().getMatchingCellElement(textToFind, columnName, type, accessName).click();
        Reporter.addStepLog(String.format("user clicked at the cell contains \"%s\" in the table having \"%s\" \"%s\"", textToFind, columnName, accessName));

    }
}
